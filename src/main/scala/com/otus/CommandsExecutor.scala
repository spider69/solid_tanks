package com.otus

import com.otus.exceptions.ExceptionsHandlersResolver
import com.otus.queue.Queue

trait CommandsExecutor {
  def executeCommand(): Unit
}

class CommandsExecutorImpl(
  queue: Queue,
  exceptionsHandlersResolver: ExceptionsHandlersResolver
) extends CommandsExecutor {

  override def executeCommand(): Unit = {
    val command = queue.pop
    try {
      command.execute()
    } catch {
      case e: Exception =>
        val handler = exceptionsHandlersResolver.resolve(e.getClass.getSimpleName, command.getClass.getSimpleName)
        handler.handle(e, command)
    }
  }

}
