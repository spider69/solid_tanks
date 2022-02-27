package com.otus

import com.otus.commands.Command
import com.otus.exceptions.handlers.ExceptionsHandler
import com.otus.exceptions.resolvers.ExceptionHandlersResolver
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class CommandsExecutorTest extends AnyWordSpecLike with Matchers with MockFactory {

  "commands executor" should {
    "execute command from queue" in {
      val queue: Queue = mock[Queue]
      val exceptionHandlersResolver = mock[ExceptionHandlersResolver]
      val command = mock[Command]

      (() => queue.pop).expects().returns(command)
      (() => command.execute()).expects()

      val executor = new CommandsExecutorImpl(queue, exceptionHandlersResolver)
      executor.executeCommand()
    }

    "resolve exception while execute" in {
      val queue: Queue = mock[Queue]
      val exceptionHandlersResolver = mock[ExceptionHandlersResolver]
      val exceptionHandler = mock[ExceptionsHandler]

      val exception = new IllegalArgumentException("Test exception")
      val command = FailedCommand(exception)

      (() => queue.pop).expects().returns(command)
      (exceptionHandlersResolver.resolve _).expects(exception, command).returns(exceptionHandler)
      (exceptionHandler.handle _).expects(exception, command)

      val executor = new CommandsExecutorImpl(queue, exceptionHandlersResolver)
      executor.executeCommand()
    }
  }

  case class FailedCommand(exception: Exception) extends Command {
    override def execute() = throw exception
  }
}
