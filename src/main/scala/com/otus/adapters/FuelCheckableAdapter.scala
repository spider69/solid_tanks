package com.otus.adapters

import com.otus.UObject
import com.otus.behavior.FuelCheckable

class FuelCheckableAdapter(obj: UObject) extends FuelCheckable {
  override def getCurrentFuel: Int =
    obj.getProperty("CurrentFuel").asInstanceOf[Int]

  override def getFuelConsumption: Int =
    obj.getProperty("FuelConsumption").asInstanceOf[Int]
}
