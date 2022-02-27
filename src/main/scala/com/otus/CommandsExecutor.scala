package com.otus

import com.otus.exceptions.resolvers.ExceptionHandlersResolver
import com.otus.queue.Queue

trait CommandsExecutor {
  def executeCommand(): Unit
}

class CommandsExecutorImpl(
  queue: Queue,
  exceptionHandlersResolver: ExceptionHandlersResolver
) extends CommandsExecutor {

  override def executeCommand(): Unit = {
    val command = queue.pop
    try {
      command.execute()
    } catch {
      case e: Exception =>
        val handler = exceptionHandlersResolver.resolve(e, command)
        handler.handle(e, command)
    }
  }

}
