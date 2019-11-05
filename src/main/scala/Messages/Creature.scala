package Messages

import Game.{Action, CreatureData, Point}

class Creature(id: Int, val team: Int, val data: CreatureData, val actions: Array[Action] = Array()) extends Message(id) {

}
