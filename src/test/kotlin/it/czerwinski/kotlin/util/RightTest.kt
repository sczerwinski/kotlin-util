package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test
import java.util.*

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

    @Test
    @Throws(Exception::class)
    fun foldShouldTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(1.2f)
        // when:
        val result = either.fold(
                { left -> "%03d".format(Locale.US, left) },
                { right -> "%.2f".format(Locale.US, right) })
        // then:
        assertEquals("1.20", result)
    }

    @Test
    @Throws(Exception::class)
    fun swapShouldReturnLeft() {
        // given:
        val either: Either<Int, Float> = Right(1.2f)
        // when:
        val result = either.swap()
        // then:
        assertEquals(Left(1.2f), result)
    }

    @Test
    @Throws(Exception::class)
    fun mergeShouldReturnRightValue() {
        // given:
        val either: Either<String, String> = Right("right text")
        // when:
        val result = either.merge()
        // then:
        assertEquals("right text", result)
    }
}
