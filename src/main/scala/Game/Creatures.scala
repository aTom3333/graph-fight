package Game

import Messages.Creature

object Creatures {
  def Solar(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Solar", team, new CreatureData(
      hp = 363,
      maxHP = 363,
      position = position,
      strength = 28,
      dexterity = 20,
      defense = 44
    ), Array(
      Weapons.GreatSword(),
      Weapons.CompositeLongbow(),
      new FlyingTowardEnemy(45, 1.8),
      new OptionalAction(new FlyingAway(45, 5), 10)
    ), Array(
      new Regeneration(15),
      new ChooseAction(Array(
        new FlyingTowardEnemy(45, 1.8),
        new OptionalAction(new FlyingAway(45, 5), 10)
      ))
    ),
      isBoss)
  }

  def Pito(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Pito", team, new CreatureData(
      hp = 34,
      maxHP = 34,
      position = position,
      strength = 5,
      dexterity = 17,
      defense = 14
    ), Array(), // No actions because Pito is sleeping
      Array(),
      isBoss)
  }

  def OrcWorgRider(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Orc Worg Rider", team, new CreatureData(
      hp = 13,
      maxHP = 13,
      position = position,
      strength = 14,
      dexterity = 15,
      defense = 18
    ), Array(
      Weapons.BattleAxe(),
      Weapons.ShortBow(),
      new WalkingTowardEnemy(6, 1.4)
    ), Array(
      new WalkingTowardEnemy(6, 1.4)
    ),
      isBoss)
  }

  def OrcBarbarian(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Orc Barbarian", team, new CreatureData(
      hp = 142,
      maxHP = 142,
      position = position,
      strength = 22,
      dexterity = 18,
      defense = 17
    ), Array(
      new Attack(Array(
        (19, Dice.dice(8, 1, 10)),
        (14, Dice.dice(8, 1, 10)),
        (9, Dice.dice(8, 1, 10)),
      ), 1.6, 19, 3, false),
      new Attack(Array( // Composite LongBow
        (16, Dice.dice(8, 1, 6)),
        (11, Dice.dice(8, 1, 6)),
        (6, Dice.dice(8, 1, 6)),
      ), 33, 20, 3, true, true, 0.5),
      new WalkingTowardEnemy(12, 1.4)
    ), Array(
      new WalkingTowardEnemy(12, 1.4)
    ),
      isBoss)
  }

  def Warlord(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Warlord", team, new CreatureData(
      hp = 141,
      maxHP = 141,
      position = position,
      strength = 18,
      dexterity = 15,
      defense = 27
    ), Array(
      new Attack(Array(
        (20, Dice.dice(8, 1, 10)),
        (15, Dice.dice(8, 1, 10)),
        (10, Dice.dice(8, 1, 10))
      ), 2, 19, 2, true, false),
      new Attack(Array( // throwing axe
        (19, Dice.dice(6, 1, 5))
      ), 17, 20, 2, true, true, 0.5),
      new WalkingTowardEnemy(9, 1.8)
    ), Array(
      new WalkingTowardEnemy(9, 1.8)
    ),
      isBoss)
  }
}
