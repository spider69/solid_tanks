package com.otus.commands

class MacroCommand(commands: List[Command]) extends Command {
  override def execute(): Unit =
    commands.foreach(_.execute())
}
