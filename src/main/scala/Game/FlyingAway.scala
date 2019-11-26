package Game

import Messages.Creature

class FlyingAway(val maxDistance: Double, val threshold: Double, factor: Double = 20) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    val closeEnemies = others.filter(c => c.team != self.team)
      .filter(c => Point.distance(self.data.position, c.data.position) < threshold)
      //.sortBy(c => Point.distance(self.data.position, c.data.position))

    val count = closeEnemies.count(c => true)
    val goingAwayAction = if(count > 0) {
      val averagePos = Point.div(closeEnemies.map(c => c.data.position)
        .reduce((p1, p2) => Point.add(p1, p2)), count)

      val distance = Point.distance(self.data.position, averagePos)
      val dx = averagePos.x - self.data.position.x
      val dy = averagePos.y - self.data.position.y
      val dz = averagePos.z - self.data.position.z
      val destination = if (distance > maxDistance) {
        val factor = Math.min(maxDistance / distance, 1)
        new Point(self.data.position.x + dx * factor, self.data.position.y + dy * factor, Math.max(self.data.position.z + dz * factor, 0))
      } else
        new Point(self.data.position.x + dx * 1, self.data.position.y + dy * 1, Math.max(self.data.position.z + dz * 1, 0))

      Array((self, (distance * factor / count * self.data.hp / self.data.maxHP, self, (cr: Creature) => {
        new Creature(cr.id, cr.name, cr.team, cr.data.change(position = destination), cr.activeActions, cr.passiveActions)
      })))
    } else
      Array[(Creature, (Double, Creature, Creature => Creature))]()

    val goingUpAction = Array((self, (factor / count * self.data.hp / self.data.maxHP * 10, self, (cr: Creature) => {
      new Creature(cr.id, cr.name, cr.team, cr.data.change(position = new Point(cr.data.position.x, cr.data.position.y, cr.data.position.z + maxDistance)), cr.activeActions, cr.passiveActions)
    })))

    goingAwayAction ++ goingUpAction
  }
}