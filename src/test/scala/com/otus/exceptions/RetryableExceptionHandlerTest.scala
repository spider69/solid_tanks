package com.otus.exceptions

import com.otus.commands.Command
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class RetryableExceptionHandlerTest extends AnyWordSpecLike with Matchers with MockFactory {

  "retryable exception handler" should {
    "place incoming command to queue" in {
      val queue: Queue = mock[Queue]
      val exception: Exception = new Exception("test exception")
      val command: Command = () => ()

      (queue.push _).expects(command)

      val retryableExceptionHandler = new RetryableExceptionHandler(queue)
      retryableExceptionHandler.handle(exception, command)
    }
  }

}
