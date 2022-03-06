package com.otus.commands

import com.otus.Moveable
import com.otus.model.Coordinates

case class MoveCommand(moveable: Moveable) extends Command {

  override def execute(): Unit = {
    val currentPosition = moveable.getPosition
    val velocity = moveable.getVelocity
    moveable.setPosition(Coordinates.concat(currentPosition, velocity))
  }

}
