package Game

import Messages.Creature

class WalkingAway(val maxDistance: Double, val threshold: Double, factor: Double = 200) extends Action(factor) {
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
      Array((self, (distance * factor / count * self.data.hp / self.data.maxHP, self, (cr: Creature) => {
        val distance = Point.distance(cr.data.position, averagePos)
        val dx = averagePos.x - cr.data.position.x
        val dy = averagePos.y - cr.data.position.y
        val destination =
          new Point(cr.data.position.x - dx / distance * movingDistance, cr.data.position.y - dy / distance * movingDistance, cr.data.position.z)
        cr.withData(cr.data.change(position = destination))
      })))
    } else
      Array[(Creature, (Double, Creature, Creature => Creature))]()

    goingAwayAction
  }
}