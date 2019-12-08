import java.io.{BufferedReader, DataOutputStream, InputStreamReader}
import java.net.{ServerSocket, Socket}

import Communication.{Deserializer, Json, Serializer}
import Game.{Action, Attack, CreatureData, Creatures, Fights, FlyingAway, FlyingTowardEnemy, Point, WalkingTowardEnemy, Weapons}
import Messages.{Creature, Message, PerformedAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


object Main extends App {

  val rand = new Random()


  def createCreature(sparkContext: SparkContext, n: Int): RDD[(Int, Creature)] = {
    sparkContext.makeRDD[Int](for (i <- 0 until n) yield i)
      .map(i => {
        if(i != 0)
        (i, Creatures.OrcWorgRider(i, 0, new Point(rand.nextGaussian()*20, rand.nextGaussian()*20, 0)))
        else
          (0, Creatures.Solar(0, 2, new Point(0, 0, 0)))
      })
  }

  //  def createGraph(sparkContext: SparkContext): Graph[Messages.Creature, Double] = {
  //    var id: VertexId = 1
  //    val vertices = createCreature(sparkContext, 20).map(c => ({id+=1; id}, c))
  //    val edges = vertices.cartesian(vertices).filter{ case ((id1: VertexId, c1: Messages.Creature), (id2: VertexId, c2: Messages.Creature)) =>
  //      true
  //    }.map{case ((id1: VertexId, c1: Messages.Creature), (id2: VertexId, c2: Messages.Creature)) =>
  //      new Edge(id1, id2, Game.Point.distance(c1.position, c2.position))
  //    }
  //    Graph.apply(vertices, edges)
  //  }

  println("Quel combat faire ?")
  println("1) Combat 1")
  println("2) Combat 2")
  var fightNo = 0
  do {
    fightNo = scala.io.StdIn.readInt()
  } while(fightNo != 1 && fightNo != 2)

  val conf: SparkConf = new SparkConf()
    .setAppName("Cool")
    .setMaster("local[*]")

  val sparkContext: SparkContext = new SparkContext(conf)
  sparkContext.setLogLevel("ERROR")

  val server = new ServerSocket(1234)

  while(true) {
    acceptConnection()
  }


  private def acceptConnection(): Unit = {
    val socket = server.accept()
    val creatures: RDD[(Int, Creature)] =
      sparkContext.makeRDD((if(fightNo == 1) Fights.fight1() else Fights.fight2() )
      .map(c => (c.id, c)))

    new Thread(() => fight(creatures, socket)).start()
  }


  private def fight(creaturesParam: RDD[(Int, Creature)], socket: Socket): Unit = {
    var creatures: RDD[(Int, Message)] = creaturesParam.map{ case (id, c) => (id, c.asInstanceOf[Message])}
    var break = false
    val output = new Serializer(socket.getOutputStream)
    val input = new Deserializer(socket.getInputStream)
    while (!break) {
      creatures = creatures.cache.localCheckpoint
      val json = Json.serialize(creatures.map{ case (id, c) => c})
      output.write(json)
      //output.write('\n')
      println(json)
      val message = input.read() // Wait for Unity to request continuation by sending a message

      if(message == "OK!") {
        val aliveTeams = creatures.map { case (id, c: Creature) => (c.team, 1) }.reduceByKey((a, b) => 1).count()
        if (aliveTeams > 1) {

          creatures = tick(creatures)

        } else {
          break = true
        }
      }
    }


    val creaturesCopy = creatures.collect()

    creaturesCopy.foreach { case (id, c: Creature) => printf("Créature %d de la team %d avec %d pv en (%f, %f, %f)\n", c.id, c.team, c.data.hp, c.data.position.x, c.data.position.y, c.data.position.z) }
    println("-------")

    if (creatures.count() > 0) {
      output.write(String.format("win-%d", (creatures.take(1)(0)._2 match { case c: Creature => c.team }).asInstanceOf[Object]))
      printf("La team %d a gagné\n", creatures.take(1)(0)._2 match { case c: Creature => c.team })
    } else {
      output.write("win-null")
      println("Personne n'a gagné")
    }

    output.close()
    input.close()
    socket.close()
  }


  private def tick(creatures: RDD[(Int, Message)]): RDD[(Int, Message)] = {
    val creaturesCopy = creatures.collect().map { case (id: Int, c2: Creature) => c2 }


    // Choose action for every creature
    val creaturesAndActions = creatures.flatMap { case (id: Int, c: Creature) =>
      var res = ArrayBuffer[Array[(Creature, (Double, Creature, Creature => Creature))]]()

      // list all possible action
      for (action <- c.activeActions) {
        val newMessages = action.prepare(c, creaturesCopy)
        if (newMessages.length > 0)
          res += newMessages
      }

      // Choose the one with the highest priority (lowest numerical value)
      val chosenActions = res.filter(arr => arr.nonEmpty)
        .sortBy(arr => {
        arr.minBy { case (cr, (p, o, a)) => p }._2._1
      }).take(1).flatMap(arr => {
        arr.map { case (cr, (p, o, a)) => (o.id, new PerformedAction(o.id, o, Array(a)).asInstanceOf[Message]) }
      })

      // Add the passive action that always get used
      val passiveActions = c.passiveActions.flatMap((ac: Action) => {
        ac.prepare(c, creaturesCopy)
          .map{ case (cr, (p, o, a)) => (o.id, new PerformedAction(o.id, o, Array(a)).asInstanceOf[Message]) }
      })

      Array((id, c)) ++ chosenActions ++ passiveActions

    case _ => Array[(Int, Message)]()
    }


    // Apply action effects
    val returnCreatures = creaturesAndActions.reduceByKey((msg1, msg2) => {
      val res = (msg1, msg2) match {
        case (c: Creature, ac: PerformedAction) =>
          var cr = c
          for (apply <- ac.actionApply)
            cr = apply(cr)
          cr
        case (ac: PerformedAction, c: Creature) =>
          var cr = c
          for (apply <- ac.actionApply)
            cr = apply(cr)
          cr
        case (ac1: PerformedAction, ac2: PerformedAction) =>
          new PerformedAction(ac1.id, ac1.target, ac1.actionApply ++ ac2.actionApply)
      }
      res
    })

    // Filter out dead creatures
    returnCreatures.filter {
      case (id: Int, c: Creature) => c.data.hp > 0
      case _ => false
    }.map{ // Ensure hp <= maxHP
      case (id: Int, c: Creature) => (id, c.withData(c.data.change(hp = Math.min(c.data.hp, c.data.maxHP))))
      case t => t
    }
  }

}
