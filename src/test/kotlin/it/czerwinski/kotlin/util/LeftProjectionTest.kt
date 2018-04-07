package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class LeftProjectionTest {

    @Test
    @Throws(Exception::class)
    fun getShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.get()
        // then:
        assertEquals(256, result)
    }

    @Test(expected = NoSuchElementException::class)
    @Throws(Exception::class)
    fun getShouldThrowExceptionIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        either.left.get()
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.getOrNull()
        // then:
        assertEquals(256, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnNullIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.getOrElse { -1 }
        // then:
        assertEquals(256, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnDefaultValueIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.getOrElse { -1 }
        // then:
        assertEquals(-1, result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldBeCalledIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        var result = "default"
        // when:
        either.left.forEach { result = it.toString() }
        // then:
        assertEquals("256", result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldNotBeCalledIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        var result = "default"
        // when:
        either.left.forEach { result = it.toString() }
        // then:
        assertEquals("default", result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.map { it.toString() }
        // then:
        assertEquals(Left("256"), result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldNotTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.map { it.toString() }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldTransformLeftToLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<String, Float> = either.left.flatMap { Left(it.toString()) }
        // then:
        assertEquals(Left("256"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldTransformLeftToRight() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<String, Float> = either.left.flatMap { Right(it * 0.1f) }
        // then:
        assertEquals(Right(25.6f), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldNotTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<String, Float> = either.left.flatMap { Left(it.toString()) }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnTrueIfLeftAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnFalseIfLeftAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.all { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnTrueIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnTrueIfLeftAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.any { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnFalseIfLeftAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnFalseIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.any { it > 0 }
        // then:
        assertFalse(result)
    }
}
