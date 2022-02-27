package com.otus.exceptions.resolvers

import com.otus.commands.Command
import com.otus.exceptions.handlers.ExceptionsHandler

object ExceptionHandlersResolver {
  val AnyException = "*"
  val AnyCommand = "*"
}

import ExceptionHandlersResolver._

trait ExceptionHandlersResolver {
  protected val handlers: Map[(String, String), ExceptionsHandler]

  def resolve[A <: Exception, B <: Command](exception: A, command: B): ExceptionsHandler = {
    val exceptionClassName = exception.getClass.getSimpleName
    val commandClassName = command.getClass.getSimpleName
    handlers
      .get((exceptionClassName, commandClassName))
      .orElse(findHandlerForAnyException(commandClassName))
      .orElse(getDefaultHandler)
      .getOrElse(throw new IllegalArgumentException(s"Exception handler is not defined for ($exceptionClassName, $commandClassName)"))
  }

  private def findHandlerForAnyException(commandClassName: String): Option[ExceptionsHandler] =
    handlers.find {
      case ((exception, command), _) => exception == AnyException && command == commandClassName
    }.map(_._2)

  private def getDefaultHandler: Option[ExceptionsHandler] =
    handlers.get((AnyException, AnyCommand))
}
