package com.otus.exceptions

import com.otus.commands.Command

import scala.collection.mutable

trait ExceptionsHandlersResolver {
  def register(exception: Exception, command: Command, resolver: ExceptionsHandler): Unit
  def resolve(exception: Exception, command: Command): ExceptionsHandler
}

class ExceptionsHandlersResolverImpl extends ExceptionsHandlersResolver {
  private val handlers: mutable.Map[(Exception, Command), ExceptionsHandler] = new mutable.HashMap[(Exception, Command), ExceptionsHandler]()

  override def register(exception: Exception, command: Command, resolver: ExceptionsHandler): Unit =
    handlers.put((exception, command), resolver)

  override def resolve(exception: Exception, command: Command): ExceptionsHandler =
    handlers.getOrElse((exception, command), throw new IllegalArgumentException(s"There is no exception handler for ($exception, $command)"))
}
