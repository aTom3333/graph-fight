package Game
import Messages.Creature

class Heal(val amount: Int, val number: Int, factor: Double) extends Action(factor) {
  override def prepare(self: Creature, others: Array[Creature]): Array[(Creature, (Double, Creature, Creature => Creature))] = {
    val weakest = others.filter(c => c.team == self.team)
      .sortBy(c => c.data.hp / c.data.maxHP * (if(c.isBoss) 0.5 else 1))
        .take(number)

    weakest.map(crea => {
      (self, (factor * crea.data.hp / crea.data.maxHP * (if (crea.isBoss) 0.5 else 1), crea, (c: Creature) => {
        c.withData(c.data.change(hp = c.data.hp + amount))
      }))
    })
  }
}
