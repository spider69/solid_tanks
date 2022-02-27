package com.otus.behavior

import com.otus.commands.Command
import com.otus.logger.Logger

trait ExceptionLogable {

  def getLogger: Logger

  def getCommand: Command

  def getException: Exception

}
