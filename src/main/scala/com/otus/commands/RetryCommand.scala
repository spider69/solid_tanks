package com.otus.commands

import com.otus.Retryable

class RetryCommand(retryable: Retryable) extends Command {
  override def execute(): Unit = {
    val queue = retryable.getQueue
    val command = retryable.getCommand
    queue.push(command)
  }
}
