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

    @Test
    @Throws(Exception::class)
    fun joinLeftShouldReturnInnerLeft() {
        // given:
        val either: Either<Either<String, Int>, Number> = Left(Left("text"))
        // when:
        val result = either.joinLeft()
        // then:
        assertEquals(Left("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun joinLeftShouldReturnInnerRight() {
        // given:
        val either: Either<Either<String, Int>, Number> = Left(Right(123))
        // when:
        val result = either.joinLeft()
        // then:
        assertEquals(Right(123), result)
    }

    @Test
    @Throws(Exception::class)
    fun joinRightShouldReturnThisLeft() {
        // given:
        val either: Either<Number, Either<Float, String>> = Left(123)
        // when:
        val result = either.joinRight()
        // then:
        assertEquals(Left(123), result)
    }
}
