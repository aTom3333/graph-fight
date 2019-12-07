package Game
import Messages.Creature

class ChooseAction(val actions: Array[Action]) extends Action(1) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    actions.map(a => a.prepare(self, others))
      .filter(arr => arr.nonEmpty)
      .reduceOption((a, b) => if(a(0)._2._1 < b(0)._2._1) a else b)
      .getOrElse(Array())
  }
}
