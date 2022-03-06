package com.otus.commands

import com.otus.behavior.VelocityChangeable
import com.otus.model.Coordinates

case class ChangeVelocityCommand(velocityChangeable: VelocityChangeable) extends Command {
  override def execute(): Unit = {
    val currentVelocity = velocityChangeable.getVelocity
    val velocityDiff = velocityChangeable.getVelocityDifference
    val newVelocity = Coordinates.subtract(currentVelocity, velocityDiff)
    velocityChangeable.setVelocity(newVelocity)
  }
}