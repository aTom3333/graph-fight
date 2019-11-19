package Game

class CreatureData(val position: Point, val hp: Int, val maxHP: Int, val strength: Int, val dexterity: Int, val defense: Int) extends Serializable {
  def change(position: Point = position, hp: Int = hp, maxHP: Int = maxHP, strength: Int = strength, dexterity: Int = dexterity, defense: Int = defense): CreatureData = {
    new CreatureData(position, hp, maxHP, strength, dexterity, defense)
  }
}
