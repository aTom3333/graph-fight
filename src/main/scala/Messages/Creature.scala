package Messages

import Game.{Action, CreatureData, Point}
import Communication.{ExcludeFromJson, Json}

import scala.annotation.meta.{field, getter, param, setter}

class Creature(override val id: Int, val name: String,
               val team: Int,
               val data: CreatureData,
               @(Json.Exclude@field) val activeActions: Array[Action],
               @(Json.Exclude@field) val passiveActions: Array[Action]) extends Message(id) {
  def withData(d: CreatureData): Creature = {
    new Creature(id, name, team, d, activeActions, passiveActions)
  }
}
