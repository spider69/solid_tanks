package com.otus.exceptions

import com.otus.commands.Command
import com.otus.logger.Logger

class LoggingExceptionHandler(logger: Logger) extends ExceptionsHandler[Exception, Command] {
  override def handle(exception: Exception, command: Command): Unit = {
    logger.log(s"$exception from command $command")
  }
}
