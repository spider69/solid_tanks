package com.otus.exceptions.handlers

import com.otus.commands.Command

trait ExceptionsHandler {
  def handle(exception: Exception, command: Command): Unit
}
