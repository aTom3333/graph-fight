package Game

import Messages.Creature

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Fights {
  val rand = new Random()

  def fight1(): Array[Creature] = {
    var id = 0
    val arr = ArrayBuffer[Creature]()
    arr += Creatures.Pito(id, 1, new Point(-50, 0, 0))
    id += 1
    arr += Creatures.Solar(id, 1, new Point(-45, 0, 2))
    id += 1

    arr += Creatures.Warlord(id, 2, new Point(50, 0, 0))
    id += 1
    for(i <- 0 to 4) {
      arr += Creatures.OrcBarbarian(id, 2, new Point(45 + rand.nextGaussian(), rand.nextGaussian()*4, 0))
      id += 1
    }
    for(i <- 0 to 9) {
      arr += Creatures.OrcWorgRider(id, 2, new Point(40 + rand.nextGaussian()*2, rand.nextGaussian()*7, 0))
      id += 1
    }

    arr.toArray
  }

  def fight2(): Array[Creature] = {
    Array() // TODO Implement
  }
}
