package Game
import Messages.Creature

class Regeneration(val amount: Int, override val factor: Double = 1) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    Array(
      (self, (self.data.hp.toDouble / self.data.maxHP * factor, self, (c: Creature) => {
        c.withData(c.data.change(hp = c.data.hp + amount))
      }))
    )
  }
}
