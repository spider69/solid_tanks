package com.otus

import com.otus.model.Coordinates

trait Moveable {
  def setPosition(coords: Coordinates): Unit

  def getPosition: Coordinates

  def getVelocity: Coordinates
}
