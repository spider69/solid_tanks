package com.otus.exceptions.resolvers

import com.otus.behavior.Retryable
import com.otus.commands.{Command, RetryCommand}
import com.otus.exceptions.handlers.{LoggingExceptionHandler, RetryableExceptionHandler}
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class OnceRetryableResolverTest extends AnyWordSpecLike with Matchers with MockFactory {

  "once retryable resolver" should {
    "return retryable handler on command" in {
      val exception = new IllegalStateException("Test exception")
      val command = TestCommand()
      val queue: Queue = mock[Queue]

      val expectedHandler = RetryableExceptionHandler(queue)
      val onceRetryableResolver = OnceRetryableResolver(queue)
      val handler = onceRetryableResolver.resolve(exception, command)
      assert(handler == expectedHandler)
    }

    "return log handler on retry command" in {
      val exception = new IllegalStateException("Test exception")
      val command = RetryCommand(new Retryable {
        override def getCommand = TestCommand()
      })
      val queue: Queue = mock[Queue]

      val expectedHandler = LoggingExceptionHandler(queue)
      val onceRetryableResolver = OnceRetryableResolver(queue)
      val handler = onceRetryableResolver.resolve(exception, command)
      assert(handler == expectedHandler)
    }
  }

  case class TestCommand() extends Command {
    override def execute() = ()
  }

}
