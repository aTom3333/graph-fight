import Communication.{Json}
import Game.{Attack, CreatureData, Point, WalkingTowardEnemy, Weapons}
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
        (i, new Creature(i, rand.nextInt(1), new CreatureData(new Point(rand.nextGaussian()*20, rand.nextGaussian()*20, 0), 10, 3), Array(Weapons.BasicSword(), new WalkingTowardEnemy(2))))
        else
          (0, new Creature(0, 2, new CreatureData(new Point(0, 0, 0), 200, 40), Array(Weapons.GreatSword(), new WalkingTowardEnemy(3), Weapons.CompositeLongbow())))
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

  val conf: SparkConf = new SparkConf()
    .setAppName("Cool")
    .setMaster("local[*]")

  val sparkContext: SparkContext = new SparkContext(conf)
  sparkContext.setLogLevel("ERROR")

  var creatures: RDD[(Int, Creature)] = createCreature(sparkContext, 150)


  fight(creatures)


  private def fight(creaturesParam: RDD[(Int, Creature)]): Unit = {
    var creatures: RDD[(Int, Message)] = creaturesParam.map{ case (id, c) => (id, c.asInstanceOf[Message])}
    var break = false
    while (!break) {
      creatures = creatures.cache.localCheckpoint
      val json = Json.serialize(creatures.map{ case (id, c) => c})
      println(json)

      val aliveTeams = creatures.map { case (id, c: Creature) => (c.team, 1) }.reduceByKey((a, b) => 1).count()
      if (aliveTeams > 1) {


        creatures.foreach { case (id, c: Creature) => printf("Créature %d de la team %d avec %d pv en (%f, %f, %f)\n", c.id, c.team, c.data.hp, c.data.position.x, c.data.position.y, c.data.position.z) }
        println("-------")

        creatures = tick(creatures)

      } else {
        break = true
      }
    }


    val creaturesCopy = creatures.collect()

    creaturesCopy.foreach { case (id, c: Creature) => printf("Créature %d de la team %d avec %d pv en (%f, %f, %f)\n", c.id, c.team, c.data.hp, c.data.position.x, c.data.position.y, c.data.position.z) }
    println("-------")

    if (creatures.count() > 0) {
      printf("La team %d a gagné\n", creatures.take(1)(0)._2 match { case c: Creature => c.team })
    } else {
      println("Personne n'a gagné")
    }
  }


  private def tick(creatures: RDD[(Int, Message)]): RDD[(Int, Message)] = {
    val creaturesCopy = creatures.collect()

    // Choose action for every creature
    val creaturesAndActions = creatures.flatMap { case (id: Int, c: Creature) =>
      var res = ArrayBuffer[Array[(Creature, (Double, Creature, Creature => Creature))]]()

      // list all possible action
      for (action <- c.actions) {
        val newMessages = action.prepare(c, creaturesCopy.map { case (id: Int, c2: Creature) => c2 })
        if (newMessages.length > 0)
          res += newMessages
      }

      // Choose the one with the highest priority (lowest numerical value)
      val chosenActions = res.sortBy(arr => {
        arr.minBy { case (cr, (p, o, a)) => p }._2._1
      }).take(1).flatMap(arr => {
        arr.map { case (cr, (p, o, a)) => (o.id, new PerformedAction(o.id, o, Array(a)).asInstanceOf[Message]) }
      })

      Array((id, c)) ++ chosenActions

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
    }
  }

}
