package ru.fin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.math.tan

internal class FunctionTangentTest {
    private val tangent = FunctionTangent()

    @Test
    fun zeroTest() {
        val actualZero = tangent.tangent(.0, 1E-10)
        assertEquals(0.0, actualZero, 1E-10)
    }

    @Test
    fun rightPartTest() {
        val actualZero = tangent.tangent(PI / 4, 1E-10)
        assertEquals(tan(PI / 4), actualZero, 1E-10)
    }

    @Test
    fun leftPartTest() {
        val actual = tangent.tangent(-PI / 4, 1E-10)
        assertEquals(tan(-PI / 4), actual, 1E-10)
    }

    @Test
    fun rightFirstPartTest() {
        val actualZero = tangent.tangent(0.1, 1E-10)
        assertEquals(tan(0.1), actualZero, 1E-10)
    }

    @Test
    fun leftFirstPartTest() {
        val actual = tangent.tangent(-0.1, 1E-10)
        assertEquals(tan(-0.1), actual, 1E-10)
    }

    @Test
    fun rightSecondPartTest() {
        val actualZero = tangent.tangent(1.3, 1E-10)
        assertEquals(tan(1.3), actualZero, 1E-10)
    }

    @Test
    fun leftSecondPartTest() {
        val actual = tangent.tangent(-1.3, 1E-10)
        assertEquals(tan(-1.3), actual, 1E-10)
    }

    @Test
    fun rightErrorTest() {
        assertThrows(IllegalArgumentException::class.java) { tangent.tangent(PI / 2, 1E-10) }
    }

    @Test
    fun leftErrorTest() {
        assertThrows(IllegalArgumentException::class.java) { tangent.tangent(-PI / 2, 1E-10) }
    }
}
