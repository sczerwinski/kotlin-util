package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LeftProjectionFiltersTest {

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnTheSameLeftIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.filter { it > 0 }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNullIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNullIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnTheSameLeftIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.filterNot { it > 0 }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNullIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.filterNot { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNullIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.filterNot { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnTheSameLeftIfValueIsNotNull() {
        // given:
        val either: Either<Int?, Float> = Left(1)
        // when:
        val result: Either<Int, Float>? = either.left.filterNotNull()
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnNullIfValueIsNull() {
        // given:
        val either: Either<Int?, Float> = Left(null)
        // when:
        val result: Either<Int, Float>? = either.left.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnNullIfRight() {
        // given:
        val either: Either<Int?, Float> = Right(3.14f)
        // when:
        val result: Either<Int, Float>? = either.left.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnTheSameLeftIfIsInstance() {
        // given:
        val either: Either<Number, String> = Left(1)
        // when:
        val result: Either<Int, String>? = either.left.filterIsInstance()
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNullIfIsNotInstance() {
        // given:
        val either: Either<Number, String> = Left(1f)
        // when:
        val result: Either<Int, String>? = either.left.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNullIfRight() {
        // given:
        val either: Either<Number, String> = Right("text")
        // when:
        val result: Either<Int, String>? = either.left.filterIsInstance()
        // then:
        assertNull(result)
    }
}
