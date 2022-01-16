package com.otus

import com.otus.commands.Command
import com.otus.queue.Queue

trait Retryable {
  def getQueue: Queue

  def getCommand: Command
}
