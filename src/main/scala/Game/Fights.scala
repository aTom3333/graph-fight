package Game

import Messages.Creature

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Fights {
  val rand = new Random()

  def fight1(): Array[Creature] = {
    var id = 0
    val arr = ArrayBuffer[Creature]()
    arr += Creatures.Pito(id, 1, new Point(-50, 0, 0), isBoss = true)
    id += 1
    arr += Creatures.Solar(id, 1, new Point(-45, 0, 5))
    id += 1

    arr += Creatures.Warlord(id, 2, new Point(50, 0, 0), isBoss = true)
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
    var id = 0
    val arr = ArrayBuffer[Creature]()
    arr += Creatures.Solar(id, 1, new Point(-50, 0, 5), isBoss = true)
    id += 1
    for(i <- 0 to 2) {
      arr += Creatures.Planetar(id, 1, new Point(-48, 1-2*i, 4))
      id += 1
    }
    for(i <- 0 to 2) {
      arr += Creatures.MovanicDeva(id, 1, new Point(-47, 3-6*i, 3))
      id += 1
    }
    for(i <- 0 to 5) {
      arr += Creatures.AstralDeva(id, 1, new Point(-45, 4-2*i, 3))
      id += 1
    }
    for(i <- 0 to 100) {
      arr += Creatures.HoundArchon(id, 1, new Point(-40+rand.nextGaussian()*3, rand.nextGaussian()*10, 0))
      id += 1
    }


    arr += Creatures.RedDragonGreatWyrm(id, 2, new Point(50, 0, 10), isBoss = true)
    id += 1
    for(i <- 0 to 4) {
      arr += Creatures.RedDragon(id, 2, new Point(47, 2*i-3, 5))
      id += 1
    }
    for(i <- 0 to 15) {
      arr += Creatures.AngelSlayer(id, 2, new Point(45+rand.nextGaussian()*2, rand.nextGaussian()*5, 0))
      id += 1
    }
    for(i <- 0 to 300) {
      arr += Creatures.OrcBarbarian(id, 2, new Point(40 + rand.nextGaussian()*3, rand.nextGaussian()*15, 0))
      id += 1
    }
    arr.toArray
  }
}
