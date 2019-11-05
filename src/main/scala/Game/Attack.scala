package Game

import Messages.Creature
class Attack() extends Action(1) {
  override def applyOn(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Int))] = {
    others
      .filter{c => c.team != self.team}
      .sortBy(c => Point.distance(self.data.position, c.data.position))
      .take(1)
      .map(c => {
        (self, (Point.distance(self.data.position, c.data.position), c, 1))
      })
  }
}
