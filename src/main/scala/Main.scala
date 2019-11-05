import Game.{Attack, CreatureData, Point}
import Messages.{Creature, Message, PerformedAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random


object Main extends App {

  val rand = new Random()

  def createCreature(sparkContext: SparkContext, n: Int): RDD[(Int, Message)] = {
    sparkContext.makeRDD[Int](for (i <- 0 until n) yield i)
      .map(i => {
        (i, new Creature(i, rand.nextInt(2), new CreatureData(new Point(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian()), 10), Array(new Attack)))
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

  var creatures: RDD[(Int, Message)] = createCreature(sparkContext, 5000)

  var break = false

  while(!break) {
    creatures = creatures.cache.localCheckpoint

    val aliveTeams = creatures.map{ case (id, c:Creature) => (c.team,1) }.reduceByKey((a,b) => 1).count()
    if(aliveTeams > 1) {

      val creaturesCopy = creatures.collect()

      creaturesCopy.foreach { case (id, c: Creature) => printf("Créature %d de la team %d avec %d pv en (%f, %f, %f)\n", c.id, c.team, c.data.hp, c.data.position.x, c.data.position.y, c.data.position.z) }
      println("-------")

      val creaturesAndActions = creatures.flatMap { case (id: Int, c: Creature) =>
        var res = ArrayBuffer[Array[(Creature, (Double, Creature, Int))]]()

        for (action <- c.actions) {
          val newMessages = action.applyOn(c, creaturesCopy.map { case (id: Int, c2: Creature) => c2 })
          if (newMessages.length > 0)
            res += newMessages
        }

        val chosenActions = res.sortBy(arr => {
          arr.minBy { case (cr, (p, o, a)) => p }._2._1
        }).take(1).flatMap(arr => {
          arr.map { case (cr, (p, o, a)) => (o.id, new PerformedAction(o.id, o, a).asInstanceOf[Message]) }
        })

        Array((id, c)) ++ chosenActions

      case _ => Array[(Int, Message)]()
      }

      creatures = creaturesAndActions.reduceByKey((msg1, msg2) => {
        (msg1, msg2) match {
          case (c: Creature, ac: PerformedAction) =>
            new Creature(c.id, c.team, new CreatureData(c.data.position, c.data.hp - ac.damage), c.actions)
          case (ac: PerformedAction, c: Creature) =>
            new Creature(c.id, c.team, new CreatureData(c.data.position, c.data.hp - ac.damage), c.actions)
          case (ac1: PerformedAction, ac2: PerformedAction) =>
            new PerformedAction(ac1.id, ac1.target, ac1.damage + ac2.damage)
        }
      })

      creatures = creatures.filter {
        case (id: Int, c: Creature) => c.data.hp > 0
        case _ => false
      }

    } else {
      break = true
    }
  }

  val creaturesCopy = creatures.collect()

  creaturesCopy.foreach { case (id, c: Creature) => printf("Créature %d de la team %d avec %d pv en (%f, %f, %f)\n", c.id, c.team, c.data.hp, c.data.position.x, c.data.position.y, c.data.position.z) }
  println("-------")

  if(creatures.count() > 0) {
    printf("La team %d a gagné\n", creatures.take(1)(0)._2 match { case c:Creature => c.team } )
  } else {
    println("Personne n'a gagné")
  }

}
