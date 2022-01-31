package com.otus.exceptions

import com.otus.Moveable
import com.otus.commands.{Command, MoveCommand}
import com.otus.exceptions.ExceptionsHandlersResolverImpl.{AnyCommand, AnyException}
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ExceptionsHandlersResolverTest extends AnyWordSpecLike with Matchers with MockFactory {

  val exception = new Exception("test exception")
  val command = new Command {
    override def execute() = ???
  }
  val expectedHandler = mock[ExceptionsHandler]


  "exceptions handler resolver" should {
    "throw exception" when {
      "there is no handler for command and exception" in {
        val exceptionsHandlersResolver = new ExceptionsHandlersResolverImpl

        assertThrows[IllegalArgumentException] {
          exceptionsHandlersResolver.resolve(exception, command)
        }
      }
    }

    "resolve exception for specific command for any exception" in {
      val obj = mock[Moveable]
      val moveCommand = new MoveCommand(obj)

      val exceptionsHandlersResolver = new ExceptionsHandlersResolverImpl
      exceptionsHandlersResolver.register(AnyException, moveCommand.getClass.getSimpleName, expectedHandler)

      val actualHandler = exceptionsHandlersResolver.resolve(exception, moveCommand)
      actualHandler should be(expectedHandler)
    }

    "resolve any exception handler with default" in {
      val exceptionsHandlersResolver = new ExceptionsHandlersResolverImpl
      exceptionsHandlersResolver.register(AnyException, AnyCommand, expectedHandler)

      val actualHandler = exceptionsHandlersResolver.resolve(exception, command)
      actualHandler should be(expectedHandler)
    }

    "resolve exception by specific handler" in {
      val exceptionsHandlersResolver = new ExceptionsHandlersResolverImpl
      exceptionsHandlersResolver.register(exception.getClass.getSimpleName, command.getClass.getSimpleName, expectedHandler)

      val actualHandler = exceptionsHandlersResolver.resolve(exception, command)
      actualHandler should be(expectedHandler)
    }
  }

}
