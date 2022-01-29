package com.otus.exceptions

import com.otus.commands.Command

trait ExceptionsHandler {
  def handle(exception: Exception, command: Command): Unit
}
