package Messages

import Game.{Action, CreatureData, Point}
import Communication.{ExcludeFromJson, Json}

import scala.annotation.meta.{field, getter, param, setter}

class Creature(override val id: Int, val team: Int, val data: CreatureData, @(Json.Exclude@field) val actions: Array[Action] = Array()) extends Message(id) {

}
