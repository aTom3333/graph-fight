package Game

class AdditionGiver(val giver: Array[ValueGiver]) extends ValueGiver {
  override def giveValue(): Int = {
    giver.map(g => g.giveValue()).sum
  }
}
