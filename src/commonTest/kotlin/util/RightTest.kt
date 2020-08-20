package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RightTest {

    @Test
    fun shouldNotBeLeft() {
        // given:
        val either: Either<Int, String> = Right("text")
        // when:
        val result = either.isLeft
        // then:
        assertFalse(result)
    }

    @Test
    fun shouldBeRight() {
        // given:
        val either: Either<Int, String> = Right("text")
        // when:
        val result = either.isRight
        // then:
        assertTrue(result)
    }

    @Test
    fun foldShouldTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(1.2f)
        // when:
        val result = either.fold(
            { left -> left.toString() },
            { right -> right.toString() }
        )
        // then:
        assertEquals("1.2", result)
    }

    @Test
    fun swapShouldReturnLeft() {
        // given:
        val either: Either<Int, Float> = Right(1.2f)
        // when:
        val result = either.swap()
        // then:
        assertEquals(Left(1.2f), result)
    }

    @Test
    fun mergeShouldReturnRightValue() {
        // given:
        val either: Either<String, String> = Right("right text")
        // when:
        val result = either.merge()
        // then:
        assertEquals("right text", result)
    }

    @Test
    fun joinLeftShouldReturnThisRight() {
        // given:
        val either: Either<Either<String, Int>, Number> = Right(12.3f)
        // when:
        val result = either.joinLeft()
        // then:
        assertEquals(Right(12.3f), result)
    }

    @Test
    fun joinRightShouldReturnInnerLeft() {
        // given:
        val either: Either<Number, Either<Float, String>> = Right(Left(3.14f))
        // when:
        val result = either.joinRight()
        // then:
        assertEquals(Left(3.14f), result)
    }

    @Test
    fun joinRightShouldReturnInnerRight() {
        // given:
        val either: Either<Number, Either<Float, String>> = Right(Right("text"))
        // when:
        val result = either.joinRight()
        // then:
        assertEquals(Right("text"), result)
    }

    @Test
    fun containsShouldReturnTrueIfTheSameElementOfTypeRight() {
        // given:
        val either: Either<Int, Float> = Right(12.3f)
        // when:
        val result = 12.3f in either
        // then:
        assertTrue(result)
    }

    @Test
    fun containsShouldReturnFalseIfDifferentElementOfTypeRight() {
        // given:
        val either: Either<Int, Float> = Right(12.3f)
        // when:
        val result = 3.14f in either
        // then:
        assertFalse(result)
    }

    @Test
    fun containsShouldReturnFalseIfElementOfTypeLeft() {
        // given:
        val either: Either<Int, Float> = Right(12.3f)
        // when:
        val result = 12 in either
        // then:
        assertFalse(result)
    }
}
