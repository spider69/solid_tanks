package com.otus.adapters

import com.otus.commands.Command
import com.otus.{Retryable, UObject}
import com.otus.queue.Queue

class RetryableAdapter(obj: UObject) extends Retryable {
  override def getQueue: Queue =
    obj.getProperty("CurrentQueue").asInstanceOf[Queue]

  override def getCommand: Command =
    obj.getProperty("CommandToRetry").asInstanceOf[Command]
}
