package com.otus.exceptions.resolvers

import com.otus.exceptions.handlers.{LoggingExceptionHandler, Retryable2ExceptionHandler, RetryableExceptionHandler}
import com.otus.exceptions.resolvers.ExceptionHandlersResolver.{AnyCommand, AnyException}
import com.otus.queue.Queue

case class TwiceRetryableResolver(queue: Queue) extends ExceptionHandlersResolver {
  protected val handlers = Map(
    (AnyException, AnyCommand) -> RetryableExceptionHandler(queue),
    (AnyException, "RetryCommand") -> Retryable2ExceptionHandler(queue),
    (AnyException, "Retry2Command") -> LoggingExceptionHandler(queue)
  )
}
