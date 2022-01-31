package com.otus.commands

case class MacroCommand(commands: List[Command]) extends Command {
  override def execute() =
    commands.foreach(_.execute())
}
