package com.otus

import com.otus.behavior.Retryable
import com.otus.commands.{Command, LogExceptionCommand, Retry2Command, RetryCommand}
import com.otus.exceptions.ExceptionsHandlersResolverImpl.{AnyCommand, AnyException}
import com.otus.exceptions.{ExceptionsHandlersResolverImpl, LoggingExceptionHandler, Retryable2ExceptionHandler, RetryableExceptionHandler}
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class CommandsExecutorTest extends AnyWordSpecLike with Matchers with MockFactory {

  "commands executor" should {
    "retry command and write to log" in {
      val queue = mock[Queue]

      val exceptionsHandlersResolver = new ExceptionsHandlersResolverImpl
      exceptionsHandlersResolver.register(AnyException, AnyCommand, RetryableExceptionHandler(queue))
      exceptionsHandlersResolver.register(AnyException, "RetryCommand", LoggingExceptionHandler(queue))

      val commandsExecutor = new CommandsExecutorImpl(queue, exceptionsHandlersResolver)

      val commandWithException = new Command {
        override def execute() = throw new Exception("test exception")
      }
      val retryCommand = RetryCommand(new Retryable {
        override def getCommand = commandWithException
      })

      (() => queue.pop).expects().returns(commandWithException)
      (queue.push _).expects(where {
        (r: Command) => r match {
          case c: RetryCommand => c.retryable.getCommand == commandWithException
          case _ => false
        }
      })

      (() => queue.pop).expects().returns(retryCommand)
      (queue.push _).expects(where {
        (r: Command) => r match {
          case c: LogExceptionCommand => c.exceptionLogable.getCommand == retryCommand
          case _ => false
        }
      })

      commandsExecutor.executeCommand()
      commandsExecutor.executeCommand()
    }

    "retry command 2 times and write to log" in {
      val queue = mock[Queue]

      val exceptionsHandlersResolver = new ExceptionsHandlersResolverImpl
      exceptionsHandlersResolver.register(AnyException, AnyCommand, RetryableExceptionHandler(queue))
      exceptionsHandlersResolver.register(AnyException, "RetryCommand", Retryable2ExceptionHandler(queue))
      exceptionsHandlersResolver.register(AnyException, "Retry2Command", LoggingExceptionHandler(queue))

      val commandsExecutor = new CommandsExecutorImpl(queue, exceptionsHandlersResolver)

      val commandWithException = new Command {
        override def execute() = throw new Exception("test exception")
      }
      val retryCommand = RetryCommand(new Retryable {
        override def getCommand = commandWithException
      })
      val retry2Command = Retry2Command(new Retryable {
        override def getCommand = commandWithException
      })

      (() => queue.pop).expects().returns(commandWithException)
      (queue.push _).expects(where {
        (r: Command) => r match {
          case c: RetryCommand => c.retryable.getCommand == commandWithException
          case _ => false
        }
      })

      (() => queue.pop).expects().returns(retryCommand)
      (queue.push _).expects(where {
        (r: Command) => r match {
          case c: Retry2Command => c.retryable.getCommand == commandWithException
          case _ => false
        }
      })

      (() => queue.pop).expects().returns(retry2Command)
      (queue.push _).expects(where {
        (r: Command) => r match {
          case c: LogExceptionCommand => c.exceptionLogable.getCommand == retry2Command
          case _ => false
        }
      })

      commandsExecutor.executeCommand()
      commandsExecutor.executeCommand()
      commandsExecutor.executeCommand()
    }
  }

}
