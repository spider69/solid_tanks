package com.otus.adapters

import com.otus.UObject
import com.otus.behavior.FuelBurnable

class FuelBurnableAdapter(obj: UObject) extends FuelBurnable {
  override def getCurrentFuel: Int =
    obj.getProperty("CurrentFuel").asInstanceOf[Int]

  override def setCurrentFuel(fuel: Int): Unit =
    obj.setProperty("CurrentFuel", fuel)

  override def getFuelConsumption: Int =
    obj.getProperty("FuelConsumption").asInstanceOf[Int]
}
