package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RightProjectionFiltersTest {

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.filter { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNullIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.filter { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.filterNot { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnTheSameRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.filterNot { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNullIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.filterNot { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnTheSameRightIfValueIsNotNull() {
        // given:
        val either: Either<Int, Float?> = Right(3.14f)
        // when:
        val result: Either<Int, Float>? = either.right.filterNotNull()
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnNullIfValueIsNull() {
        // given:
        val either: Either<Int, Float?> = Right(null)
        // when:
        val result: Either<Int, Float>? = either.right.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float?> = Left(1)
        // when:
        val result: Either<Int, Float>? = either.right.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnTheSameRightIfIsInstance() {
        // given:
        val either: Either<String, Number> = Right(1)
        // when:
        val result: Either<String, Int>? = either.right.filterIsInstance()
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNullIfIsNotInstance() {
        // given:
        val either: Either<String, Number> = Right(1f)
        // when:
        val result: Either<String, Int>? = either.right.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNullIfLeft() {
        // given:
        val either: Either<String, Number> = Left("text")
        // when:
        val result: Either<String, Int>? = either.right.filterIsInstance()
        // then:
        assertNull(result)
    }
}
