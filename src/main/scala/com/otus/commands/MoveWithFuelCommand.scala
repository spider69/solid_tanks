package com.otus.commands

import com.otus.behavior.MoveableWithFuel

case class MoveWithFuelCommand(moveableWithFuel: MoveableWithFuel)
  extends MacroCommand(List(
    CheckFuelCommand(moveableWithFuel),
    MoveCommand(moveableWithFuel),
    BurnFuelCommand(moveableWithFuel)
  ))
