package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class RightBiasedEitherTest {

    @Test(expected = NoSuchElementException::class)
    @Throws(Exception::class)
    fun getShouldThrowExceptionIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        either.get()
    }

    @Test
    @Throws(Exception::class)
    fun getShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.get()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.getOrNull()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnDefaultValueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.getOrElse { -1f }
        // then:
        assertEquals(-1f, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.getOrElse { -1f }
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldNotBeCalledIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        var result = "default"
        // when:
        either.forEach { result = it.toString() }
        // then:
        assertEquals("default", result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldBeCalledIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        var result = "default"
        // when:
        either.forEach { result = it.toString() }
        // then:
        assertEquals("3.14", result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.map { it.toString() }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.map { it.toString() }
        // then:
        assertEquals(Right("3.14"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<Int, String> = either.flatMap { Right(it.toString()) }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldTransformRightToLeft() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.flatMap { Left(it.toInt()) }
        // then:
        assertEquals(Left(3), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldTransformRightToRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.flatMap { Right(it.toString()) }
        // then:
        assertEquals(Right("3.14"), result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnTrueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnTrueIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.all { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnFalseIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.all { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnFalseIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnFalseIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.any { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnFalseIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.any { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun noneShouldReturnTrueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun noneShouldReturnFalseIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.none { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun noneShouldReturnTrueIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.none { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun toOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.toOption()
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun toOptionShouldReturnSomeWithRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.toOption()
        // then:
        assertEquals(Some(3.14f), result)
    }
}
