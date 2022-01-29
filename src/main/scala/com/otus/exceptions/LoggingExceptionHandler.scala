package com.otus.exceptions

import com.otus.behavior.ExceptionLogable
import com.otus.commands.{Command, LogExceptionCommand}
import com.otus.queue.Queue

case class LoggingExceptionHandler(queue: Queue) extends ExceptionsHandler {
  override def handle(exception: Exception, command: Command): Unit = {
    val logCommand = LogExceptionCommand(new ExceptionLogable {
      override def getLogger = (message: String) => println(message)

      override def getCommand = command

      override def getException = exception
    })
    queue.push(logCommand)
  }
}
