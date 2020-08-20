package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@Suppress("DEPRECATION")
class RightProjectionTest {

    @Test
    fun getShouldThrowExceptionIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // then:
        assertFailsWith<NoSuchElementException>(message = "Getting Right value from Left") {
            either.right.get()
        }
    }

    @Test
    fun getShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.get()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    fun getOrNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    fun getOrNullShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.getOrNull()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    fun getOrElseShouldReturnDefaultValueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.getOrElse { -1f }
        // then:
        assertEquals(-1f, result)
    }

    @Test
    fun getOrElseShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.getOrElse { -1f }
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    fun forEachShouldNotBeCalledIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        var result = "default"
        // when:
        either.right.forEach { result = it.toString() }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun forEachShouldBeCalledIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        var result = "default"
        // when:
        either.right.forEach { result = it.toString() }
        // then:
        assertEquals("3.14", result)
    }

    @Test
    fun mapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.map { it.toString() }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun mapShouldTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.map { it.toString() }
        // then:
        assertEquals(Right("3.14"), result)
    }

    @Test
    fun flatMapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<Int, String> = either.right.flatMap { Right(it.toString()) }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun flatMapShouldTransformRightToLeft() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.right.flatMap { Left(it.toInt()) }
        // then:
        assertEquals(Left(3), result)
    }

    @Test
    fun flatMapShouldTransformRightToRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.right.flatMap { Right(it.toString()) }
        // then:
        assertEquals(Right("3.14"), result)
    }

    @Test
    fun allShouldReturnTrueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun allShouldReturnTrueIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.all { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    fun allShouldReturnFalseIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.all { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    fun anyShouldReturnFalseIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun anyShouldReturnFalseIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.any { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    fun anyShouldReturnFalseIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.any { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnTrueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun noneShouldReturnFalseIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.none { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnTrueIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.none { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    fun toOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.toOption()
        // then:
        assertEquals(None, result)
    }

    @Test
    fun toOptionShouldReturnSomeWithRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.toOption()
        // then:
        assertEquals(Some(3.14f), result)
    }
}
