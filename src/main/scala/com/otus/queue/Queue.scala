package com.otus.queue

import com.otus.commands.Command

trait Queue {
  def push(command: Command): Unit
  def pop: Command
}
