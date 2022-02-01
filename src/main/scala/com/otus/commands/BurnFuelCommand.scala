package com.otus.commands

import com.otus.behavior.FuelBurnable

case class BurnFuelCommand(fuelBurnable: FuelBurnable) extends Command {
  override def execute() = {
    val currentFuel = fuelBurnable.getCurrentFuel
    val newFuelValue = currentFuel - fuelBurnable.getFuelConsumption
    fuelBurnable.setCurrentFuel(newFuelValue)
  }
}
