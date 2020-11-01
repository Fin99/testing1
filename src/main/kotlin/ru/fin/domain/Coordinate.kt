package ru.fin.domain

import kotlin.math.pow

class Coordinate(
    var x: Double,
    var y: Double
) {
    fun getDistance(coordinate: Coordinate): Double =
        ((coordinate.x - x).pow(2) + (coordinate.y - y).pow(2)).pow(0.5)
}
