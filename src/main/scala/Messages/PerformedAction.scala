package Messages

class PerformedAction(id: Int, val target: Creature, val actionApply: Array[Creature => Creature]) extends Message(id) {

}
