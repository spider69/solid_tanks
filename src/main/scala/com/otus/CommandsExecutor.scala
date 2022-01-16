package com.otus

import com.otus.commands.Command
import com.otus.exceptions.ExceptionsHandlersResolver

trait CommandsExecutor {
  def executeCommand(command: Command): Unit
}

class CommandsExecutorImpl(exceptionsHandler: ExceptionsHandlersResolver) extends CommandsExecutor {
  override def executeCommand(command: Command): Unit =
    try {
      command.execute()
    } catch {
      case e: Exception =>
        val handler = exceptionsHandler.resolve(e, command)
        handler.handle(e, command)
    }
}
