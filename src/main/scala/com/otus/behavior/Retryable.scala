package com.otus.behavior

import com.otus.commands.Command

trait Retryable {
  def getCommand: Command
}
