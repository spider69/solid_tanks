package com.otus.adapters

import com.otus.commands.Command
import com.otus.UObject
import com.otus.behavior.Retryable

class RetryableAdapter(obj: UObject) extends Retryable {
  override def getCommand: Command =
    obj.getProperty("CommandToRetry").asInstanceOf[Command]
}
