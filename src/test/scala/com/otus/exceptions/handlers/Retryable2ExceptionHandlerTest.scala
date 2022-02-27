package com.otus.exceptions.handlers

import com.otus.commands.{Command, Retry2Command}
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class Retryable2ExceptionHandlerTest extends AnyWordSpecLike with Matchers with MockFactory {

  "retryable2 exception handler" should {
    "place incoming command to queue" in {
      val queue: Queue = mock[Queue]
      val exception: Exception = new Exception("test exception")
      val command: Command = () => ()

      (queue.push _).expects(where {
        (c: Command) => c match {
          case Retry2Command(retryable) => retryable.getCommand == command
        }
      })

      val retryableExceptionHandler = Retryable2ExceptionHandler(queue)
      retryableExceptionHandler.handle(exception, command)
    }
  }

}
