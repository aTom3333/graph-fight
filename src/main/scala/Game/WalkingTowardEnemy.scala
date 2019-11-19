package Game
import Messages.Creature

class WalkingTowardEnemy(val maxDistance: Double, factor: Double = 10) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    val target = others.filter(c => c.team != self.team)
    .minBy(c => Point.distance(self.data.position, c.data.position))

    val distance = Point.distance(self.data.position, target.data.position)
    val dx = target.data.position.x - self.data.position.x
    val dy = target.data.position.y - self.data.position.y
    val destination = if(distance > maxDistance) {
      val factor = Math.min(maxDistance / distance, 0.8)
      new Point(self.data.position.x + dx * factor, self.data.position.y + dy * factor, self.data.position.z)
    } else
      new Point(self.data.position.x + dx * 0.8, self.data.position.y + dy * 0.8, self.data.position.z)
    Array((self, (distance*factor, self, (cr: Creature) => {
      new Creature(cr.id, cr.name, cr.team, cr.data.change(position = destination), cr.actions)
    })))
  }
}
