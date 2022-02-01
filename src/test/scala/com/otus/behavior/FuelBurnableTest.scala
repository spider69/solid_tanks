package com.otus.behavior

import com.otus.UObject
import com.otus.adapters.FuelBurnableAdapter
import com.otus.commands.BurnFuelCommand
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class FuelBurnableTest extends AnyWordSpecLike with Matchers with MockFactory {

  "fuel burnable object" should {
    "burn fuel correctly" in {
      val obj: UObject = mock[UObject]
      (obj.getProperty _).expects("CurrentFuel").returns(6)
      (obj.getProperty _).expects("FuelConsumption").returns(2)
      (obj.setProperty _).expects("CurrentFuel", 4)

      executeBurnFuelCommand(obj)
    }
  }

  private def executeBurnFuelCommand(obj: UObject): Unit = {
    val fuelBurnableAdapter = new FuelBurnableAdapter(obj)
    val burnFuelCommand = BurnFuelCommand(fuelBurnableAdapter)
    burnFuelCommand.execute()
  }
}
