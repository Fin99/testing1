package ru.fin.domain

import java.lang.Exception

class Car(
    var coordinate: Coordinate,
    var fuel: Double,
) {
    var landingPlace: Person? = null

    fun fly(coordinate: Coordinate) {
        val distance = this.coordinate.getDistance(coordinate)
        if (distance > fuel * 5.0) throw Exception("Нужно больше соляры")

        landingPlace?.let {
            this.coordinate = coordinate
            it.coordinate = coordinate
            fuel = distance / 5.0
        } ?: throw Exception("Как машина поедет без водителя?")
    }
}
