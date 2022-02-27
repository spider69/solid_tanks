package com.otus.exceptions.resolvers
import com.otus.exceptions.handlers.{LoggingExceptionHandler, RetryableExceptionHandler}
import com.otus.exceptions.resolvers.ExceptionHandlersResolver._
import com.otus.queue.Queue

case class OnceRetryableResolver(queue: Queue) extends ExceptionHandlersResolver {
  protected val handlers = Map(
    (AnyException, AnyCommand) -> RetryableExceptionHandler(queue),
    (AnyException, "RetryCommand") -> LoggingExceptionHandler(queue)
  )
}