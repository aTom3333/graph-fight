package Game

class ConstantGiver(val value: Int) extends ValueGiver {
  override def giveValue(): Int = { value }
}
