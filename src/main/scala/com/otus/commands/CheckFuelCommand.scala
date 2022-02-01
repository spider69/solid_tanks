package com.otus.commands

import com.otus.behavior.FuelCheckable
import com.otus.exceptions.NotEnoughFuelException

case class CheckFuelCommand(fuelCheckable: FuelCheckable) extends Command {
  override def execute() = {
    val currentFuel = fuelCheckable.getCurrentFuel
    val fuelConsumption = fuelCheckable.getFuelConsumption
    if(currentFuel - fuelConsumption < 0) {
      throw NotEnoughFuelException()
    }
  }
}
