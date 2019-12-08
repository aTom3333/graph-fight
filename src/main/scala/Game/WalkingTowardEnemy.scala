package Game
import Messages.Creature

class WalkingTowardEnemy(val maxDistance: Double, val minDistance: Double, factor: Double = 10) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    val target = others.filter(c => c.team != self.team)
    .minBy(c => Point.distance(self.data.position, c.data.position))

    val distance = Point.distance(self.data.position, target.data.position)
    if(distance > minDistance) {

      Array((self, (distance * factor, self, (cr: Creature) => {
        val dx = target.data.position.x - cr.data.position.x
        val dy = target.data.position.y - cr.data.position.y
        val destination = if (distance > maxDistance) {
          val factor = Math.min(maxDistance / distance, 0.8)
          new Point(cr.data.position.x + dx * factor, cr.data.position.y + dy * factor, cr.data.position.z)
        } else
          new Point(cr.data.position.x + dx * 0.8, cr.data.position.y + dy * 0.8, cr.data.position.z)
        new Creature(cr.id, cr.name, cr.team, cr.data.change(position = destination), cr.activeActions, cr.passiveActions)
      })))
    } else {
      Array()
    }
  }
}
