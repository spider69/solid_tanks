package com.otus.commands

import com.otus.behavior.Retryable

case class RetryCommand(retryable: Retryable) extends Command {
  override def execute(): Unit = {
    val command = retryable.getCommand
    command.execute()
  }
}
