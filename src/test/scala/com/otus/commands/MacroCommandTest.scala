package com.otus.commands

import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MacroCommandTest extends AnyWordSpecLike with Matchers with MockFactory {

  "macro command" should {
    "execute commands sequentially" in {
      val command1 = mock[Command]
      val command2 = mock[Command]
      val command3 = mock[Command]

      (() => command1.execute()).expects()
      (() => command2.execute()).expects()
      (() => command3.execute()).expects()

      val macroCommand = new MacroCommand(List(command1, command2, command3))
      macroCommand.execute()
    }

    "break execution" when {
      "any command throws exception" in {
        val command1 = mock[Command]
        val command2 = mock[Command]
        val command3 = mock[Command]

        (() => command1.execute()).expects()
        (() => command2.execute()).expects().throws(new IllegalArgumentException("Test exception"))
        (() => command3.execute()).expects().never()

        val macroCommand = new MacroCommand(List(command1, command2, command3))

        assertThrows[IllegalArgumentException] {
          macroCommand.execute()
        }
      }
    }
  }

}
