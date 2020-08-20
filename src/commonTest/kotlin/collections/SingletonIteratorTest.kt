package it.czerwinski.kotlin.collections

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SingletonIteratorTest {

    @Test
    fun hasNextShouldReturnTrue() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        val result = iterator.hasNext()
        // then:
        assertTrue(result)
    }

    @Test
    fun hasNextShouldReturnFalseAfterFirstElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        iterator.next()
        val result = iterator.hasNext()
        // then:
        assertFalse(result)
    }

    @Test
    fun nextShouldReturnElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        val result = iterator.next()
        // then:
        assertEquals("text", result)
    }

    @Test
    fun nextShouldThrowExceptionAfterFirstElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        iterator.next()
        // then:
        assertFailsWith<NoSuchElementException>(message = "Only one element available") {
            iterator.next()
        }
    }

    @Test
    fun shouldIterateOnce() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        var count = 0
        // when:
        for (string in iterator) {
            count++
        }
        // then:
        assertEquals(1, count)
    }
}
