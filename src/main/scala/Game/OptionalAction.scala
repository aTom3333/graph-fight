package Game
import Messages.Creature

class OptionalAction(val action: Action, val threshold: Double) extends Action(1) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    val arr = action.prepare(self, others)
    if(arr.length > 0 && arr(0)._2._1 < threshold) {
      arr
    } else {
      Array()
    }
  }
}
