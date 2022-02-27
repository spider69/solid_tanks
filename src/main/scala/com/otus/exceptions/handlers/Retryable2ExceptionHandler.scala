package com.otus.exceptions.handlers

import com.otus.behavior.Retryable
import com.otus.commands.{Command, Retry2Command, RetryCommand}
import com.otus.queue.Queue

case class Retryable2ExceptionHandler(queue: Queue) extends ExceptionsHandler {
  override def handle(exception: Exception, command: Command): Unit = {
    val retryCommand = Retry2Command(new Retryable {
      override def getCommand = command match {
        case RetryCommand(retryable) => retryable.getCommand
        case _ => command
      }
    })
    queue.push(retryCommand)
  }
}
