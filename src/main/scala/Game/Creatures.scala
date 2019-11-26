package Game

import Messages.Creature

object Creatures {
  def Solar(id: Int, team: Int, position: Point): Creature = {
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
      new FlyingTowardEnemy(45),
      new FlyingAway(45, 10, 1)
    ), Array(
      new Regeneration(15)
    ))
  }

  def OrcWorgRider(id: Int, team: Int, position: Point): Creature = {
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
      new WalkingTowardEnemy(6)
    ), Array())
  }
}
