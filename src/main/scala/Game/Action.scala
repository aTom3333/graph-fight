package Game

import Messages.Creature

abstract class Action(val repeat: Int) extends Serializable {
  def applyOn(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Int))]
}
