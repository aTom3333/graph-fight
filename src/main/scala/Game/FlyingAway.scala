package Game

import Messages.Creature

class FlyingAway(val maxDistance: Double, val threshold: Double, factor: Double = 200) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    val closeEnemies = others.filter(c => c.team != self.team)
      .filter(c => Point.distance(self.data.position, c.data.position) < threshold)
      //.sortBy(c => Point.distance(self.data.position, c.data.position))

    val count = closeEnemies.count(c => true)
    val movingDistance = maxDistance * (0.05 + 0.95 * Math.sqrt(1 - self.data.hp.toDouble / self.data.maxHP))
    val goingAwayAction = if(count > 0) {
      val averagePos = Point.div(closeEnemies.map(c => c.data.position)
        .reduce((p1, p2) => Point.add(p1, p2)), count)

      val distance = Point.distance(self.data.position, averagePos)
      val dx = averagePos.x - self.data.position.x
      val dy = averagePos.y - self.data.position.y
      val dz = averagePos.z - self.data.position.z
      val destination =
        new Point(self.data.position.x - dx / distance * movingDistance, self.data.position.y - dy / distance * movingDistance, Math.max(self.data.position.z - dz / distance * movingDistance, 0))
      Array((self, (distance * factor / count * self.data.hp / self.data.maxHP, self, (cr: Creature) => {
        new Creature(cr.id, cr.name, cr.team, cr.data.change(position = destination), cr.activeActions, cr.passiveActions)
      })))
    } else
      Array[(Creature, (Double, Creature, Creature => Creature))]()

    val goingUpAction = Array((self, (factor / count * self.data.hp / self.data.maxHP * 10, self, (cr: Creature) => {
      new Creature(cr.id, cr.name, cr.team, cr.data.change(position = new Point(cr.data.position.x, cr.data.position.y, cr.data.position.z + movingDistance)), cr.activeActions, cr.passiveActions)
    })))

    goingAwayAction ++ goingUpAction
  }
}