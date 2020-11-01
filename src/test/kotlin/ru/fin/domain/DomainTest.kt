package ru.fin.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.Exception
import kotlin.test.assertNull

class DomainTest {

    @Test
    fun setInCarTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val car = Car(Coordinate(0.0, 0.0), 100.0)

        person.seatInCar(car)

        assertEquals(car, person.car)
        assertEquals(person, car.landingPlace)
    }

    @Test
    fun setInCarSoFarTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val car = Car(Coordinate(10.0, 10.0), 100.0)

        assertThrows(Exception::class.java) { person.seatInCar(car) }
    }

    @Test
    fun setInCarAlreadyInCarTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val car = Car(Coordinate(0.0, 0.0), 100.0)

        person.seatInCar(car)
        assertThrows(Exception::class.java) { person.seatInCar(car) }
    }

    @Test
    fun setInCarNotEmptyCarTest() {
        val person1 = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val person2 = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val car = Car(Coordinate(0.0, 0.0), 100.0)

        person1.seatInCar(car)
        assertThrows(Exception::class.java) { person2.seatInCar(car) }
    }

    @Test
    fun leaveCarTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val car = Car(Coordinate(0.0, 0.0), 100.0).also { it.landingPlace = person }
        person.car = car

        person.leaveCar()

        assertNull(person.car)
        assertNull(car.landingPlace)
    }

    @Test
    fun leaveCarAlreadyLeaveTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)

        assertThrows(Exception::class.java) { person.leaveCar() }
    }

    @Test
    fun goInCarTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val car = Car(Coordinate(0.0, 0.0), 100.0).also { it.landingPlace = person }
        person.car = car

        val destination = Coordinate(0.0, 100.0)
        person.go(destination)

        assertEquals(20.0, car.fuel)
        assertEquals(destination, person.coordinate)
        assertEquals(destination, car.coordinate)
    }

    @Test
    fun goInCarFuelIsEmptyTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val car = Car(Coordinate(0.0, 0.0), 1.0).also { it.landingPlace = person }

        person.car = car

        assertThrows(Exception::class.java) { person.go(Coordinate(100.0, 100.0)) }
    }

    @Test
    fun goOnFootTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)

        val destination = Coordinate(0.0, 100.0)
        person.go(destination)

        assertEquals(destination, person.coordinate)
    }

    @Test
    fun infinityMeaninglessTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val place = Place(Coordinate(0.0, 0.0), Coordinate(100.0, 100.0))

        assertEquals(InformativeAboutInfinity.MEANINGLESS, person.getInformationAboutInfinityOfPlace(place))
    }

    @Test
    fun infinityMeaningfulTest() {
        val person = Person(Coordinate(0.0, 0.0), 5.0, 100.0)
        val place = Place(Coordinate(0.0, 0.0), Coordinate(50.0, 50.0))

        assertEquals(InformativeAboutInfinity.MEANINGFUL, person.getInformationAboutInfinityOfPlace(place))
    }

    @Test
    fun infinityNotInfinityTest() {
        val person = Person(Coordinate(0.0, 0.0), 10.0, 100.0)
        val place = Place(Coordinate(0.0, 0.0), Coordinate(5.0, 5.0))

        assertThrows(Exception::class.java) { person.getInformationAboutInfinityOfPlace(place) }
    }
}
