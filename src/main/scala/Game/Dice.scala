package Game

object Dice {
  def dice(faces: Int = 6, amount: Int = 1, add: Int = 0): ValueGiver = {
    new AdditionGiver((0 until amount).map(i => new RandomGiver(faces)).toArray ++ Array(new ConstantGiver(add)))
  }
}
