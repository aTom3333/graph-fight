package Game
import Messages.Creature

class NoOverlap extends Action(1) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    val minDistance = others.filterNot(c => c.id == self.id)
      .map(c => Point.distance(c.data.position, self.data.position))
      .min

    val value = 0.5
    if(minDistance < value) {
      Array(
        (self, (1, self, (c: Creature) => {
          val r = value-minDistance
          val a = Math.random()*Math.PI*2
          val x = r * Math.cos(a)
          val y = r * Math.sin(a)
          c.withData(c.data.change(position = new Point(c.data.position.x + x, c.data.position.y + y, c.data.position.z)))
        }))
      )
    } else {
      Array()
    }
  }
}
