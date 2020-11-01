package ru.fin

import org.apache.commons.math3.fraction.BigFraction
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.pow

class FunctionTangent {
    fun tangent(x: Double, precision: Double): Double {
        if (!x.isFinite() || abs(x) >= Math.PI / 2)
            throw IllegalArgumentException("Значение \"x\" должно быть в промежутке (-π/2; π/2)")

        var result = 0.0
        var currentChange = x
        var number = 1

        while (abs(currentChange) >= precision / 10) {
            result += currentChange
            val denominator = (1 - 4.0.pow(number + 1)) * x.pow(2) * -4
            val numerator = (2 * number + 1) * (2 * number + 2) * (1 - 4.0.pow(number))
            currentChange *= (denominator / numerator) * bernoulli((2 * number + 2), (2 * number))
            number++
        }
        return result
    }

    private fun Int.bernoulli(): BigDecimal {
        val a = Array(this + 1) { BigFraction(1, (it + 1)) }
        for (m in 0..this) {
            for (j in m downTo 1) {
                a[j - 1] = (a[j - 1].subtract(a[j])).multiply(BigFraction(j))
            }
        }
        return a[0].bigDecimalValue(16, RoundingMode.HALF_UP.ordinal)
    }

    private fun bernoulli(denominator: Int, nominator: Int): Double =
        denominator.bernoulli().divide(nominator.bernoulli(), 16, RoundingMode.HALF_UP).toDouble()
}
