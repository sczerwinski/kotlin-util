package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LeftTest {

    @Test
    fun shouldBeLeft() {
        // given:
        val either: Either<Int, String> = Left(123)
        // when:
        val result = either.isLeft
        // then:
        assertTrue(result)
    }

    @Test
    fun shouldNotBeRight() {
        // given:
        val either: Either<Int, String> = Left(123)
        // when:
        val result = either.isRight
        // then:
        assertFalse(result)
    }

    @Test
    fun foldShouldTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(12)
        // when:
        val result = either.fold(
            { left -> left.toString() },
            { right -> right.toString() }
        )
        // then:
        assertEquals("12", result)
    }

    @Test
    fun swapShouldReturnRight() {
        // given:
        val either: Either<Int, Float> = Left(12)
        // when:
        val result = either.swap()
        // then:
        assertEquals(Right(12), result)
    }

    @Test
    fun mergeShouldReturnLeftValue() {
        // given:
        val either: Either<String, String> = Left("left text")
        // when:
        val result = either.merge()
        // then:
        assertEquals("left text", result)
    }

    @Test
    fun joinLeftShouldReturnInnerLeft() {
        // given:
        val either: Either<Either<String, Int>, Number> = Left(Left("text"))
        // when:
        val result = either.joinLeft()
        // then:
        assertEquals(Left("text"), result)
    }

    @Test
    fun joinLeftShouldReturnInnerRight() {
        // given:
        val either: Either<Either<String, Int>, Number> = Left(Right(123))
        // when:
        val result = either.joinLeft()
        // then:
        assertEquals(Right(123), result)
    }

    @Test
    fun joinRightShouldReturnThisLeft() {
        // given:
        val either: Either<Number, Either<Float, String>> = Left(123)
        // when:
        val result = either.joinRight()
        // then:
        assertEquals(Left(123), result)
    }

    @Test
    fun containsShouldReturnTrueIfTheSameElementOfTypeLeft() {
        // given:
        val either: Either<Int, Float> = Left(12)
        // when:
        val result = 12 in either
        // then:
        assertTrue(result)
    }

    @Test
    fun containsShouldReturnFalseIfDifferentElementOfTypeLeft() {
        // given:
        val either: Either<Int, Float> = Left(12)
        // when:
        val result = 13 in either
        // then:
        assertFalse(result)
    }

    @Test
    fun containsShouldReturnFalseIfElementOfTypeRight() {
        // given:
        val either: Either<Int, Float> = Left(12)
        // when:
        val result = 3.14f in either
        // then:
        assertFalse(result)
    }
}
