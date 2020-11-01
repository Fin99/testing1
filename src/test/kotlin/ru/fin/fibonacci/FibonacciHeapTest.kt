package ru.fin.fibonacci

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.NoSuchElementException
import kotlin.test.assertEquals

class FibonacciHeapTest {
    @Test
    fun popTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        heap.enqueue(2, 2.0)

        assertEquals(1, heap.pop().value)
        assertEquals(1, heap.size())
    }

    @Test
    fun oneEntryPopTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)

        assertEquals(1, heap.pop().value)
    }

    @Test
    fun degreePopTest() {
        val heap = Fibonacci<Int>()
        val entry3 = heap.enqueue(3, 3.0)
        heap.enqueue(1, 1.0)
        val entry2 = heap.enqueue(2, 2.0)

        assertEquals(1, heap.pop().value)

        assertEquals(1, entry2.degree)
        assertEquals(0, entry3.degree)

        assertEquals(2, heap.pop().value)
    }

    @Test
    fun emptyTest() {
        val heap = Fibonacci<Int>()

        assertThrows(NoSuchElementException::class.java) { heap.min() }
        assertThrows(NoSuchElementException::class.java) { heap.pop() }
    }

    @Test
    fun sizeTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        heap.enqueue(2, 2.0)

        assertEquals(2, heap.size())
    }

    @Test
    fun minTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        heap.enqueue(2, 2.0)

        assertEquals(1, heap.min().value)
        assertEquals(2, heap.size())
    }

    @Test
    fun deleteWithoutTreeTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        val deleted = heap.enqueue(2, 2.0)

        heap.delete(deleted)

        assertEquals(1, heap.min().value)
        assertEquals(1, heap.size())
    }

    @Test
    fun deleteLeafTest() {
        val heap = Fibonacci<Int>()
        val deleted = heap.enqueue(3, 3.0)
        heap.enqueue(1, 1.0)
        val entry2 = heap.enqueue(2, 2.0)

        assertEquals(1, heap.pop().value)

        assertEquals(1, entry2.degree)
        assertEquals(0, deleted.degree)

        heap.delete(deleted)

        assertEquals(2, heap.min().value)
        assertEquals(1, heap.size())
    }

    @Test
    fun deleteSubTreeTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        val entry2 = heap.enqueue(2, 2.0)
        val entry3 = heap.enqueue(3, 3.0)
        val entry4 = heap.enqueue(4, 4.0)
        val entry5 = heap.enqueue(5, 5.0)
        val entry6 = heap.enqueue(6, 6.0)

        heap.pop()

        assertEquals(0, entry2.degree)
        assertEquals(2, entry3.degree)
        assertEquals(0, entry4.degree)
        assertEquals(1, entry5.degree)
        assertEquals(0, entry6.degree)

        heap.delete(entry5)

        assertEquals(2, heap.min().value)
        assertEquals(4, heap.size())
    }

    @Test
    fun deleteSubLeafTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        val entry2 = heap.enqueue(2, 2.0)
        val entry3 = heap.enqueue(3, 3.0)
        val entry4 = heap.enqueue(4, 4.0)
        val entry5 = heap.enqueue(5, 5.0)
        val entry6 = heap.enqueue(6, 6.0)

        heap.pop()

        assertEquals(0, entry2.degree)
        assertEquals(2, entry3.degree)
        assertEquals(0, entry4.degree)
        assertEquals(1, entry5.degree)
        assertEquals(0, entry6.degree)

        heap.delete(entry4)

        assertEquals(2, heap.min().value)
        assertEquals(4, heap.size())
    }

    @Test
    fun lazyDegreeTest() {
        val heap = Fibonacci<Int>()
        assertEquals(0, heap.enqueue(1, 1.0).degree)
        assertEquals(0, heap.enqueue(2, 2.0).degree)
        assertEquals(0, heap.enqueue(3, 3.0).degree)
    }

    @Test
    fun easyDegreeTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        val entry2 = heap.enqueue(2, 2.0)
        val entry3 = heap.enqueue(3, 3.0)

        heap.pop()

        assertEquals(1, entry2.degree)
        assertEquals(0, entry3.degree)
    }

    @Test
    fun hardDegreeTest() {
        val heap = Fibonacci<Int>()
        heap.enqueue(1, 1.0)
        val entry2 = heap.enqueue(2, 2.0)
        val entry3 = heap.enqueue(3, 3.0)
        val entry4 = heap.enqueue(4, 4.0)
        val entry5 = heap.enqueue(5, 5.0)
        val entry6 = heap.enqueue(6, 6.0)

        heap.pop()

        assertEquals(0, entry2.degree)
        assertEquals(2, entry3.degree)
        assertEquals(0, entry4.degree)
        assertEquals(1, entry5.degree)
        assertEquals(0, entry6.degree)
    }

}
