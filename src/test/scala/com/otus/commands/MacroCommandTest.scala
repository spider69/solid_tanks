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

      val macroCommand = MacroCommand(List(command1, command2, command3))
      macroCommand.execute()
    }
  }

}
