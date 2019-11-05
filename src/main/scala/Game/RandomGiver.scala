package Game

import scala.util.Random

class RandomGiver(val bound: Int) extends ValueGiver {
  override def giveValue(): Int = { RandomGiver.rand.nextInt(bound) + 1 }
}

object RandomGiver {
  val rand = new Random()
}