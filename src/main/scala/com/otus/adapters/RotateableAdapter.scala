package com.otus.adapters

import com.otus.{Rotateable, UObject}

class RotateableAdapter(obj: UObject) extends Rotateable {
  override def getDirection: Int =
    obj.getProperty("GetDirection").asInstanceOf[Int]

  override def setDirection(newDirection: Int): Unit =
    obj.setProperty("SetDirection", newDirection)

  override def getAngularVelocity: Int =
    obj.getProperty("GetAngularVelocity").asInstanceOf[Int]

  override def getMaxDirections: Int =
    obj.getProperty("GetMaxDirections").asInstanceOf[Int]
}
