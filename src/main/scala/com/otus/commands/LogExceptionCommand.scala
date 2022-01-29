package com.otus.commands

import com.otus.behavior.ExceptionLogable

case class LogExceptionCommand(exceptionLogable: ExceptionLogable) extends Command {

  override def execute(): Unit = {
    val logger = exceptionLogable.getLogger
    val command = exceptionLogable.getCommand
    val exception = exceptionLogable.getException
    logger.log(s"$exception was thrown for command $command")
  }

}
