package com.otus.adapters

import com.otus.model.Coordinates
import com.otus.{Moveable, UObject}

class MoveableAdapter(obj: UObject) extends Moveable {
  override def setPosition(coords: Coordinates): Unit =
    obj.setProperty("SetPosition", coords)

  override def getPosition: Coordinates =
    obj.getProperty("GetPosition").asInstanceOf[Coordinates]

  override def getVelocity: Coordinates =
    obj.getProperty("GetVelocity").asInstanceOf[Coordinates]
}
