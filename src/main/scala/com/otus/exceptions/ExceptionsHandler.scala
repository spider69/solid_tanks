package com.otus.exceptions

import com.otus.commands.Command

trait ExceptionsHandler[E <: Exception, C <: Command] {
  def handle(exception: E, command: C): Unit
}
