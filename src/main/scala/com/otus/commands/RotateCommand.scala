package com.otus.commands

import com.otus.Rotateable

class RotateCommand(rotateable: Rotateable) extends Command {
  override def execute(): Unit = {
    val currentDirection = rotateable.getDirection
    val currentAngularVelocity = rotateable.getAngularVelocity
    rotateable.setDirection((currentDirection + currentAngularVelocity) % rotateable.getMaxDirections)
  }
}
