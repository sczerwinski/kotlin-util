package it.czerwinski.kotlin.collections

import org.junit.Assert.assertFalse
import org.junit.Assert.fail
import org.junit.Test

class EmptyIteratorTest {

    @Test
    @Throws(Exception::class)
    fun hasNextShouldReturnFalse() {
        // given:
        val iterator: Iterator<String> = EmptyIterator
        // when:
        val result = iterator.hasNext()
        // then:
        assertFalse(result)
    }

    @Test(expected = NoSuchElementException::class)
    @Throws(Exception::class)
    fun nextShouldThrowException() {
        // given:
        val iterator: Iterator<String> = EmptyIterator
        // when:
        iterator.next()
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotIterate() {
        // given:
        val iterator: Iterator<String> = EmptyIterator
        // when:
        for (string in iterator) {
            // then:
            fail()
        }
    }
}
