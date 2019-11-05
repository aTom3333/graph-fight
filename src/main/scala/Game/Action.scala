package Game

import Messages.Creature

abstract class Action(val repeat: Int) extends Serializable {
  def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))]
}
