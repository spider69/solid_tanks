package com.otus.behavior

import com.otus.UObject
import com.otus.adapters.RetryableAdapter
import com.otus.commands.{Command, Retry2Command, RetryCommand}
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class RetryableTest extends AnyWordSpecLike with Matchers with MockFactory {

  "retryable object" should {
    "retry command" in {
      val obj: UObject = mock[UObject]
      val command: Command = () => ()

      (obj.getProperty _).expects("CommandToRetry").returns(command)

      val retryableAdapter = new RetryableAdapter(obj)
      val retryCommand = RetryCommand(retryableAdapter)
      retryCommand.execute()
    }

    "retry2 command" in {
      val obj: UObject = mock[UObject]
      val command: Command = () => ()

      (obj.getProperty _).expects("CommandToRetry").returns(command)

      val retryableAdapter = new RetryableAdapter(obj)
      val retryCommand = Retry2Command(retryableAdapter)
      retryCommand.execute()
    }
  }

}
