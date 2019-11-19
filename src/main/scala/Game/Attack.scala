package Game

import Messages.Creature
class Attack(val damageAccuracy: Array[(Int, ValueGiver)], val reach: Double, factor: Double = 1) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    others
      .filter{c => c.team != self.team}
      .filter(c => Point.distance(self.data.position, c.data.position) <= reach)
      .sortBy(c => Point.distance(self.data.position, c.data.position))
      .take(damageAccuracy.length)
      .map(c => {
        (self, (Point.distance(self.data.position, c.data.position) * factor, c, (crea: Creature) => {
          val accuracy = new RandomGiver(20).giveValue()

          if(accuracy == 20 || accuracy+damageAccuracy(0)._1 > c.data.defense) {
            val damage = damageAccuracy(0)._2.giveValue()
            new Creature(c.id, c.name, c.team, c.data.change(hp = c.data.hp-damage), c.actions)
          }
          else {
            crea
          }
        }))
      })
  }
}
