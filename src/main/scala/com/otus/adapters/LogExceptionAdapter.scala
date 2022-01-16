package com.otus.adapters

import com.otus.commands.Command
import com.otus.logger.Logger
import com.otus.{ExceptionLogable, UObject}

class LogExceptionAdapter(obj: UObject) extends ExceptionLogable {
  override def getLogger: Logger =
    obj.getProperty("ExceptionsLogger").asInstanceOf[Logger]

  override def getCommand: Command =
    obj.getProperty("LoggedCommand").asInstanceOf[Command]

  override def getException: Exception =
    obj.getProperty("LoggedException").asInstanceOf[Exception]
}
