package com.otus.exceptions

import scala.collection.mutable

trait ExceptionsHandlersResolver {
  def register(exceptionClassName: String, commandClassName: String, handler: ExceptionsHandler): Unit
  def resolve(exceptionClassName: String, commandName: String): ExceptionsHandler
}

class ExceptionsHandlersResolverImpl extends ExceptionsHandlersResolver {
  private val handlers: mutable.Map[(String, String), ExceptionsHandler] = new mutable.HashMap[(String, String), ExceptionsHandler]()

  override def register(exceptionClassName: String, commandClassName: String, handler: ExceptionsHandler): Unit =
    handlers.put((exceptionClassName, commandClassName), handler)

  override def resolve(exceptionClassName: String, commandClassName: String): ExceptionsHandler =
    handlers
      .get((exceptionClassName, commandClassName))
      .orElse(findHandlerForAnyException(commandClassName))
      .orElse(getDefaultHandler)
      .getOrElse(throw new IllegalArgumentException(s"Exception handler is not defined for ($exceptionClassName, $commandClassName)"))

  private def findHandlerForAnyException(commandClassName: String): Option[ExceptionsHandler] =
    handlers.find {
      case ((exception, command), _) => exception == "*" && command == commandClassName
    }.map(_._2)

  private def getDefaultHandler: Option[ExceptionsHandler] =
    handlers.get(("*", "*"))
}
