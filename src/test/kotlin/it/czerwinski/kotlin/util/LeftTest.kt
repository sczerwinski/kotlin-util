package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class LeftTest {

    @Test
    @Throws(Exception::class)
    fun shouldBeLeft() {
        // given:
        val either: Either<Int, String> = Left(123)
        // when:
        val result = either.isLeft
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeRight() {
        // given:
        val either: Either<Int, String> = Left(123)
        // when:
        val result = either.isRight
        // then:
        assertFalse(result)
    }
}