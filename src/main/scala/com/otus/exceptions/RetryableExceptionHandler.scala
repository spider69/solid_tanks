package com.otus.exceptions

import com.otus.commands.RetryCommand

class RetryableExceptionHandler extends ExceptionsHandler[Exception, RetryCommand] {
  override def handle(exception: Exception, command: RetryCommand): Unit = {
    command.execute()
  }
}
