package com.otus.model

object Coordinates {
  def concat(left: Coordinates, right: Coordinates): Coordinates =
    new Coordinates(left.x + right.x, left.y + right.y)
}

case class Coordinates(
  x: Double,
  y: Double
)
