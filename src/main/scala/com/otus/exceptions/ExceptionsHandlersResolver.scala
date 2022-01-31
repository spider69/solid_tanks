package com.otus.exceptions

import com.otus.commands.Command

import scala.collection.mutable

trait ExceptionsHandlersResolver {
  def register(exceptionClassName: String, commandClassName: String, handler: ExceptionsHandler): Unit
  def resolve[A <: Exception, B <: Command](exception: A, command: B): ExceptionsHandler
}

import ExceptionsHandlersResolverImpl._

class ExceptionsHandlersResolverImpl extends ExceptionsHandlersResolver {
  private val handlers: mutable.Map[(String, String), ExceptionsHandler] = new mutable.HashMap[(String, String), ExceptionsHandler]()

  override def register(exceptionClassName: String, commandClassName: String, handler: ExceptionsHandler): Unit = {
    handlers.put((exceptionClassName, commandClassName), handler)
  }

  override def resolve[A <: Exception, B <: Command](exception: A, command: B): ExceptionsHandler = {
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

object ExceptionsHandlersResolverImpl {
  val AnyException = "*"
  val AnyCommand = "*"
}
