package com.otus.exceptions.resolvers

import com.otus.behavior.Retryable
import com.otus.commands.{Command, Retry2Command, RetryCommand}
import com.otus.exceptions.handlers.{LoggingExceptionHandler, Retryable2ExceptionHandler, RetryableExceptionHandler}
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class TwiceRetryableResolverTest extends AnyWordSpecLike with Matchers with MockFactory {

  "twice retryable resolver" should {
    "return retryable on command" in {
      val exception = new IllegalStateException("Test exception")
      val command = TestCommand()
      val queue: Queue = mock[Queue]

      val expectedHandler = RetryableExceptionHandler(queue)
      val twiceRetryableResolver = TwiceRetryableResolver(queue)
      val handler = twiceRetryableResolver.resolve(exception, command)
      assert(handler == expectedHandler)
    }

    "return retryable2 on retry command handler" in {
      val exception = new IllegalStateException("Test exception")
      val command = RetryCommand(new Retryable {
        override def getCommand = TestCommand()
      })
      val queue: Queue = mock[Queue]

      val expectedHandler = Retryable2ExceptionHandler(queue)
      val twiceRetryableResolver = TwiceRetryableResolver(queue)
      val handler = twiceRetryableResolver.resolve(exception, command)
      assert(handler == expectedHandler)
    }

    "return log command on retry2 command" in {
      val exception = new IllegalStateException("Test exception")
      val command = Retry2Command(new Retryable {
        override def getCommand = TestCommand()
      })
      val queue: Queue = mock[Queue]

      val expectedHandler = LoggingExceptionHandler(queue)
      val twiceRetryableResolver = TwiceRetryableResolver(queue)
      val handler = twiceRetryableResolver.resolve(exception, command)
      assert(handler == expectedHandler)
    }
  }

  case class TestCommand() extends Command {
    override def execute() = ()
  }

}
