package com.otus.exceptions.handlers

import com.otus.commands.{Command, LogExceptionCommand}
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class LoggingExceptionHandlerTest extends AnyWordSpecLike with Matchers with MockFactory {

  "logging exception handler" should {
    "log incoming command and exception" in {
      val queue: Queue = mock[Queue]
      val exception: Exception = new Exception("test exception")
      val command: Command = () => ()

      (queue.push _).expects(where {
        (c: Command) => c match {
          case LogExceptionCommand(loggable) => loggable.getCommand == command && loggable.getException == exception
        }
      })

      val loggingExceptionHandler = LoggingExceptionHandler(queue)
      loggingExceptionHandler.handle(exception, command)
    }
  }

}
