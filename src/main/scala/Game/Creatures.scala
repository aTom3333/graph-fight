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
      )),
      new NoOverlap()
    ),
      isBoss)
  }

  def Planetar(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Planetar", team, new CreatureData(
      hp = 229,
      maxHP = 229,
      position = position,
      strength = 27,
      dexterity = 19,
      defense = 32
    ), Array(
      new Attack(Array( // holy greatsword
        (27, Dice.dice(6, 3, 15)),
        (22, Dice.dice(6, 3, 15)),
        (17, Dice.dice(6, 3, 15)),
      ), 1.8, 19, 2, false),
      new FlyingTowardEnemy(27, 1.6),
      new OptionalAction(new FlyingAway(27, 5), 10)
    ), Array(
      new Regeneration(10),
      new ChooseAction(Array(
        new FlyingTowardEnemy(27, 1.6),
        new OptionalAction(new FlyingAway(27, 5), 10)
      )),
      new NoOverlap()
    ), isBoss)
  }

  def MovanicDeva(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Movanic Deva", team, new CreatureData(
      hp = 126,
      maxHP = 126,
      position = position,
      strength = 19,
      dexterity = 17,
      defense = 24
    ), Array(
      new Attack(Array( // Flaming greatsword
        (17, Dice.dice(6, 2,7)),
        (12, Dice.dice(6, 2,7)),
        (7, Dice.dice(6, 2,7)),
      ), 1.6, 19, 2, false),
      new FlyingTowardEnemy(18, 1.4),
      new OptionalAction(new FlyingAway(18, 5), 10),
      new Heal(200, 65, 0.1)
    ), Array(
      new ChooseAction(Array(
        new FlyingTowardEnemy(18, 1.4),
        new OptionalAction(new FlyingAway(18, 5, 0.1), 10)
      )),
      new NoOverlap()
    ),
      isBoss)
  }

  def AstralDeva(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Astral Deva", team, new CreatureData(
      hp = 172,
      maxHP = 172,
      position = position,
      strength = 26,
      dexterity = 19,
      defense = 29
    ), Array(
      new Attack(Array( // Warhammer
        (26, Dice.dice(8, 1,14)),
        (21, Dice.dice(8, 1,14)),
        (16, Dice.dice(8, 1,14)),
      ), 1.6, 20, 3, false),
      new FlyingTowardEnemy(30, 1.4),
      new OptionalAction(new FlyingAway(30, 5), 10),
      new Heal(200, 60, 0.1)
    ), Array(
      new ChooseAction(Array(
        new FlyingTowardEnemy(30, 1.4),
        new OptionalAction(new FlyingAway(30, 5, 0.1), 10)
      )),
      new NoOverlap()
    ),
      isBoss)
  }

  def HoundArchon(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Hound Archon", team, new CreatureData(
      hp = 39,
      maxHP = 39,
      position = position,
      strength = 15,
      dexterity = 10,
      defense = 19
    ), Array(
      new Attack(Array( // Greatsword
        (9, Dice.dice(6, 2, 3)),
        (4, Dice.dice(6, 2, 3))
      ), 1.6),
      new WalkingTowardEnemy(12, 1.4),
      new OptionalAction(new WalkingAway(12, 5), 10)
    ), Array(
      new ChooseAction(Array(
        new WalkingTowardEnemy(12, 1.4),
        new OptionalAction(new WalkingAway(12, 5), 10)
      )),
      new NoOverlap()
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
      new WalkingTowardEnemy(6, 1.4),
      new OptionalAction(new WalkingAway(6, 5), 10)
    ), Array(
      new ChooseAction(Array(
        new OptionalAction(new WalkingAway(6, 5), 10),
        new WalkingTowardEnemy(6, 1.4)
      )),
      new NoOverlap()
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
      new WalkingTowardEnemy(12, 1.4),
      new OptionalAction(new WalkingAway(12, 5), 10),
    ), Array(
      new ChooseAction(Array(
        new OptionalAction(new WalkingAway(12, 5), 10),
        new WalkingTowardEnemy(12, 1.4)
      )),
      new NoOverlap()
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
      new OptionalAction(new WalkingAway(9, 5), 10),
      new WalkingTowardEnemy(9, 1.8)
    ), Array(
      new ChooseAction(Array(
        new OptionalAction(new WalkingAway(9, 5), 10),
        new WalkingTowardEnemy(9, 1.8)
      )),
      new NoOverlap()
    ),
      isBoss)
  }

  def RedDragonGreatWyrm(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Red Dragon Great Wyrm", team, new CreatureData(
      hp = 449,
      maxHP = 449,
      position = position,
      strength = 43,
      dexterity = 6,
      defense = 39
    ), Array(
      new Attack(Array( // bite, 2 claws, 2 wings, tail
        (37, Dice.dice(8, 4, 24)),
        (37, Dice.dice(6, 4, 16)),
        (37, Dice.dice(6, 4, 16)),
        (35, Dice.dice(8, 2, 8)),
        (35, Dice.dice(8, 2, 8)),
        (35, Dice.dice(6, 4, 24)),
      ), 2.5),
      new Attack(Array( // breath
        (33, Dice.dice(10, 24)),
        (33, Dice.dice(10, 24)),
        (33, Dice.dice(10, 24)),
        (33, Dice.dice(10, 24)),
      ), 20, 20, 1, true, true, 0.25),
      new FlyingTowardEnemy(75, 2),
      new OptionalAction(new FlyingAway(75, 5), 10)
    ), Array(
      new ChooseAction(Array(
        new FlyingTowardEnemy(75, 2),
        new OptionalAction(new FlyingAway(75, 5), 10)
      )),
      new NoOverlap()
    ),
      isBoss)
  }

  def RedDragon(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Red Dragon", team, new CreatureData(
      hp = 172,
      maxHP = 172,
      position = position,
      strength = 29,
      dexterity = 10,
      defense = 26
    ), Array(
      new Attack(Array( // bite, 2 claws, 2 wings, tail
        (22, Dice.dice(8, 2, 13)),
        (22, Dice.dice(6, 2, 9)),
        (22, Dice.dice(6, 2, 9)),
        (20, Dice.dice(8, 1, 4)),
        (20, Dice.dice(8, 1, 4)),
        (20, Dice.dice(6, 2, 13)),
      ), 2.1),
      new Attack(Array( // breath
        (22, Dice.dice(10, 10)),
        (22, Dice.dice(10, 10)),
        (22, Dice.dice(10, 10))
      ), 15, 20, 1, true, true, 0.25),
      new FlyingTowardEnemy(60, 1.9),
      new OptionalAction(new FlyingAway(60, 5), 10)
    ), Array(
      new ChooseAction(Array(
        new FlyingTowardEnemy(60, 1.9),
        new OptionalAction(new FlyingAway(60, 5), 10)
      )),
      new NoOverlap()
    ),
      isBoss)
  }

  def AngelSlayer(id: Int, team: Int, position: Point, isBoss: Boolean = false): Creature = {
    new Creature(id, "Angel Slayer", team, new CreatureData(
      hp = 112,
      maxHP = 112,
      position = position,
      strength = 22,
      dexterity = 17,
      defense = 26
    ), Array(
      new Attack(Array(
        (21, Dice.dice(8, 1, 15)),
        (16, Dice.dice(8, 1, 15)),
        (11, Dice.dice(8, 1, 15)),
      ), 1.6, 19, 3, true),
      new Attack(Array(
        (19, Dice.dice(8, 1, 14)),
        (14, Dice.dice(8, 1, 14)),
        (9, Dice.dice(8, 1, 14)),
      ), 22, 19, 3, false, true, 0.5),
      new WalkingTowardEnemy(15, 1.4),
      new OptionalAction(new WalkingAway(15, 5), 10)
    ), Array(
      new ChooseAction(Array(
        new WalkingTowardEnemy(15, 1.4),
        new OptionalAction(new WalkingAway(15, 5), 10)
      )),
      new NoOverlap()
    ),
      isBoss)
  }
}
