package Game

class CreatureData(val position: Point, val hp: Int, val defense: Int) extends Serializable {
  def change(position: Point = position, hp: Int = hp, defense: Int = defense): CreatureData = {
    new CreatureData(position, hp, defense)
  }
}
