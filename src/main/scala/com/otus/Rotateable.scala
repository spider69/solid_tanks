package com.otus

trait Rotateable {
  def getDirection: Int

  def setDirection(newDirection: Int): Unit

  def getAngularVelocity: Int

  def getMaxDirections: Int
}
