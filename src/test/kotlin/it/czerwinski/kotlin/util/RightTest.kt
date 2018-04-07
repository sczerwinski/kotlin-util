package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class RightTest {

    @Test
    @Throws(Exception::class)
    fun shouldNotBeLeft() {
        // given:
        val either: Either<Int, String> = Right("text")
        // when:
        val result = either.isLeft
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldBeRight() {
        // given:
        val either: Either<Int, String> = Right("text")
        // when:
        val result = either.isRight
        // then:
        assertTrue(result)
    }
}