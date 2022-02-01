package com.otus.behavior

trait FuelBurnable {
  def getCurrentFuel: Int
  def setCurrentFuel(fuel: Int): Unit
  def getFuelConsumption: Int
}
