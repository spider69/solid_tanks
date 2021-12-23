package com.otus

trait UObject {
  def getProperty(name: String): Any

  def setProperty(name: String, property: Any): Unit
}
