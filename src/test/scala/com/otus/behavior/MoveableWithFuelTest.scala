package com.otus.behavior

import com.otus.commands.MoveWithFuelCommand
import com.otus.exceptions.NotEnoughFuelException
import com.otus.model.Coordinates
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MoveableWithFuelTest extends AnyWordSpecLike with Matchers with MockFactory {

  "moveable with fuel object" should {
    "move with fuel" in {
      val moveableWithFuel = mock[MoveableWithFuel]

      (moveableWithFuel.getCurrentFuel _).expects().returns(10)
      (moveableWithFuel.getFuelConsumption _).expects().returns(3)
      (moveableWithFuel.getPosition _).expects().returns(Coordinates(2.0, 4.0))
      (moveableWithFuel.getVelocity _).expects().returns(Coordinates(1.0, 2.0))
      (moveableWithFuel.setPosition _).expects(Coordinates(3.0, 6.0))
      (moveableWithFuel.getCurrentFuel _).expects().returns(10)
      (moveableWithFuel.getFuelConsumption _).expects().returns(3)
      (moveableWithFuel.setCurrentFuel _).expects(7)

      val moveWithFuelCommand = MoveWithFuelCommand(moveableWithFuel)
      moveWithFuelCommand.execute()
    }

    "throw exception" when {
      "fuel is not enough" in {
        val moveableWithFuel = mock[MoveableWithFuel]

        (moveableWithFuel.getCurrentFuel _).expects().returns(2)
        (moveableWithFuel.getFuelConsumption _).expects().returns(3)

        val moveWithFuelCommand = MoveWithFuelCommand(moveableWithFuel)
        assertThrows[NotEnoughFuelException] {
          moveWithFuelCommand.execute()
        }
      }
    }
  }

}
