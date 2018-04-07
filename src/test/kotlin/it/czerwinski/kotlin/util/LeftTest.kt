package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test
import java.util.*

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

    @Test
    @Throws(Exception::class)
    fun foldShouldTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(12)
        // when:
        val result = either.fold(
                { left -> "%03d".format(Locale.US, left) },
                { right -> "%.2f".format(Locale.US, right) })
        // then:
        assertEquals("012", result)
    }

    @Test
    @Throws(Exception::class)
    fun swapShouldReturnRight() {
        // given:
        val either: Either<Int, Float> = Left(12)
        // when:
        val result = either.swap()
        // then:
        assertEquals(Right(12), result)
    }

    @Test
    @Throws(Exception::class)
    fun mergeShouldReturnLeftValue() {
        // given:
        val either: Either<String, String> = Left("left text")
        // when:
        val result = either.merge()
        // then:
        assertEquals("left text", result)
    }
}
