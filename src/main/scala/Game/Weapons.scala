package Game

object Weapons {
  def GreatSword(): Attack = {
    new Attack(Array(
      (35, Dice.dice(6, 3, 18)), // +35 / 3d6+18
      (30, Dice.dice(6, 3, 18)), // +30 / 3d6+18
      (25, Dice.dice(6, 3, 18)), // +25 / 3d6+18
      (20, Dice.dice(6, 3, 18)), // +20 / 3d6+18
    ), 2, 19, 2, false)
  }

  def BasicSword(): Attack = {
    new Attack(Array(
      (10, Dice.dice(6, 1, 2))
    ), 1.6, 20, 3)
  }

  def CompositeLongbow(): Attack = {
    new Attack(Array(
      (31, Dice.dice(6, 2, 14)), // +31 / 3d6+18
      (26, Dice.dice(6, 2, 14)), // +26 / 3d6+18
      (21, Dice.dice(6, 2, 14)), // +21 / 3d6+18
      (16, Dice.dice(6, 2, 14)), // +16 / 3d6+18
    ), 33, 20, 2, true, true, 0.5)
  }

  def BattleAxe(): Attack = {
    new Attack(Array(
      (6, Dice.dice(8, 1, 2))
    ), 1.6, 20, 3)
  }

  def ShortBow(): Attack = {
    new Attack(Array(
      (4, Dice.dice())
    ), 20, 20, 3, true, true, 0.1)
  }
}