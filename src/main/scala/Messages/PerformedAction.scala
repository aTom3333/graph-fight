package Messages

class PerformedAction(id: Int, val target: Creature, val damage: Int) extends Message(id) {

}
