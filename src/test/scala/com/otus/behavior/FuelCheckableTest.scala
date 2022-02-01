package com.otus.behavior

import com.otus.UObject
import com.otus.adapters.FuelCheckableAdapter
import com.otus.commands.CheckFuelCommand
import com.otus.exceptions.NotEnoughFuelException
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class FuelCheckableTest extends AnyWordSpecLike with Matchers with MockFactory {

  "fuel checkable object" should {
    "throw exception" when {
      "not enough fuel" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("CurrentFuel").returns(1)
        (obj.getProperty _).expects("FuelConsumption").returns(2)

        assertThrows[NotEnoughFuelException] {
          executeCheckFuelCommand(obj)
        }
      }
    }
    "pass when enough fuel" in {
      val obj: UObject = mock[UObject]
      (obj.getProperty _).expects("CurrentFuel").returns(4)
      (obj.getProperty _).expects("FuelConsumption").returns(2)
      
      executeCheckFuelCommand(obj)
    }
  }

  private def executeCheckFuelCommand(obj: UObject) = {
    val fuelCheckableAdapter = new FuelCheckableAdapter(obj)
    val checkFuelCommand = CheckFuelCommand(fuelCheckableAdapter)
    checkFuelCommand.execute()
  }
}
