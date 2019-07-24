package it.czerwinski.kotlin.collections

import org.junit.Assert.*
import org.junit.Test

class SingletonIteratorTest {

    @Test
    @Throws(Exception::class)
    fun hasNextShouldReturnTrue() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        val result = iterator.hasNext()
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
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
    @Throws(Exception::class)
    fun nextShouldReturnElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        val result = iterator.next()
        // then:
        assertEquals("text", result)
    }

    @Test(expected = NoSuchElementException::class)
    @Throws(Exception::class)
    fun nextShouldThrowExceptionAfterFirstElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        iterator.next()
        iterator.next()
    }

    @Test
    @Throws(Exception::class)
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
