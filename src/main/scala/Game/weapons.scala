package Game

class GreatSword extends Attack(Array(
  (35, Dice.dice(6, 3, 18)), // +35 / 3d6+18
  (35, Dice.dice(6, 3, 18)), // +30 / 3d6+18
  (35, Dice.dice(6, 3, 18)), // +25 / 3d6+18
  (35, Dice.dice(6, 3, 18)), // +20 / 3d6+18
), 10.5) {

}

class BasicSword extends Attack(Array(
  (10, Dice.dice(6, 1, 2))
), 10.4)