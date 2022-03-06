package com.otus.behavior

import com.otus.model.Coordinates

trait VelocityChangeable {
  def getVelocity: Coordinates

  def getVelocityDifference: Coordinates

  def setVelocity(newVelocity: Coordinates): Unit
}
