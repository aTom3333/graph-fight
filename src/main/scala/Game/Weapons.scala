package Game

object Weapons {
  def GreatSword(): Attack = {
    new Attack(Array(
      (35, Dice.dice(6, 3, 18)), // +35 / 3d6+18
      (35, Dice.dice(6, 3, 18)), // +30 / 3d6+18
      (35, Dice.dice(6, 3, 18)), // +25 / 3d6+18
      (35, Dice.dice(6, 3, 18)), // +20 / 3d6+18
    ), 1)
  }

  def BasicSword(): Attack = {
    new Attack(Array(
      (10, Dice.dice(6, 1, 2))
    ), 0.8)
  }

  def CompositeLongbow(): Attack = {
    new Attack(Array(
      (31, Dice.dice(6, 2, 14)), // +31 / 3d6+18
      (26, Dice.dice(6, 2, 14)), // +26 / 3d6+18
      (21, Dice.dice(6, 2, 14)), // +21 / 3d6+18
      (16, Dice.dice(6, 2, 14)), // +16 / 3d6+18
    ), 100, 0.5)
  }
}