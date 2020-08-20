package it.czerwinski.kotlin.collections

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.fail

class EmptyIteratorTest {

    @Test
    fun hasNextShouldReturnFalse() {
        // given:
        val iterator: Iterator<String> = EmptyIterator
        // when:
        val result = iterator.hasNext()
        // then:
        assertFalse(result)
    }

    @Test
    fun nextShouldThrowException() {
        // given:
        val iterator: Iterator<String> = EmptyIterator
        // then:
        assertFailsWith<NoSuchElementException>(message = "Empty iterator") {
            iterator.next()
        }
    }

    @Test
    fun shouldNotIterate() {
        // given:
        val iterator: Iterator<String> = EmptyIterator
        // when:
        for (string in iterator) {
            // then:
            fail("should not be called")
        }
    }
}
