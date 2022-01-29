package com.otus.exceptions

import com.otus.behavior.Retryable
import com.otus.commands.{Command, RetryCommand}
import com.otus.queue.Queue

case class RetryableExceptionHandler(queue: Queue) extends ExceptionsHandler {
  override def handle(exception: Exception, command: Command): Unit = {
    val retryCommand = RetryCommand(new Retryable {
      override def getCommand = command
    })
    queue.push(retryCommand)
  }
}
