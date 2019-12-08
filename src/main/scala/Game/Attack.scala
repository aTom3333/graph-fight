package Game

import Messages.Creature

class Attack(val damageAccuracy: Array[(Int, ValueGiver)], val reach: Double, val critThresh: Int = 20, val critMult: Double = 2, val singleHand: Boolean = true, val ranged: Boolean = false, factor: Double = 1) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    others
      .filter{c => c.team != self.team}
      .filter(c => Point.distance(self.data.position, c.data.position) <= reach)
      .sortBy(c => Point.distance(self.data.position, c.data.position))
      .take(damageAccuracy.length)
      .zipWithIndex
      .map{ case(c, idx) => {
        (self, (Point.distance(self.data.position, c.data.position) * factor, c, (crea: Creature) => {
          val accuracy = new RandomGiver(20).giveValue()
          val accuracyAdd = damageAccuracy(idx)._1 + modifier(if(ranged) self.data.dexterity else self.data.strength) // TODO ? Base Attack

          val crit = accuracy >= critThresh
          if(crit || accuracy+accuracyAdd >= c.data.defense) {
            val damage = damageAccuracy(idx)._2.giveValue() * (if(crit) critMult else 1) * (if(singleHand) 1 else 1.5)
            crea.withData(crea.data.change(hp = crea.data.hp-damage.toInt))
          }
          else {
            crea
          }
        }))
      }}
  }

  private def modifier(value: Int): Int = {
    Math.max((value - 10) / 2, 0)
  }
}