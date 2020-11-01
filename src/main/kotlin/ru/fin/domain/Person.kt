package ru.fin.domain

import java.lang.Exception

class Person(
    var coordinate: Coordinate,
    var nearRadius: Double,
    var viewRadius: Double
) {

    var car: Car? = null

    fun seatInCar(car: Car) {
        if (coordinate.getDistance(car.coordinate) > nearRadius) {
            throw Exception("Машина слишком далеко")
        }

        this.car?.let { throw Exception("Я уже в машине") }

        car.landingPlace?.let { throw Exception("В машине кто-то есть...") }

        this.car = car
        car.landingPlace = this
    }

    fun go(coordinate: Coordinate) {
        car?.fly(coordinate) ?: let {
            this.coordinate = coordinate
        }
    }

    fun leaveCar() {
        car?.let {
            it.landingPlace = null
            car = null
        } ?: throw Exception("Но я не в машине")
    }

    fun getInformationAboutInfinityOfPlace(place: Place): InformativeAboutInfinity {
        val placeSize = place.coordinate1.getDistance(place.coordinate2)
        if (placeSize < nearRadius) throw Exception("Это разве бесконечность")
        return if (placeSize < viewRadius) InformativeAboutInfinity.MEANINGFUL else InformativeAboutInfinity.MEANINGLESS
    }
}
