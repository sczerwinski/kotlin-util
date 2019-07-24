package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class RightBiasedEitherFiltersTest {

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filter { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNullIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filter { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterNot { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnTheSameRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterNot { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNullIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterNot { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnTheSameRightIfValueIsNotNull() {
        // given:
        val either: Either<Int, Float?> = Right(3.14f)
        // when:
        val result: Either<Int, Float>? = either.filterNotNull()
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnNullIfValueIsNull() {
        // given:
        val either: Either<Int, Float?> = Right(null)
        // when:
        val result: Either<Int, Float>? = either.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float?> = Left(1)
        // when:
        val result: Either<Int, Float>? = either.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnTheSameRightIfIsInstance() {
        // given:
        val either: Either<String, Number> = Right(1)
        // when:
        val result: Either<String, Int>? = either.filterIsInstance()
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNullIfIsNotInstance() {
        // given:
        val either: Either<String, Number> = Right(1f)
        // when:
        val result: Either<String, Int>? = either.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNullIfLeft() {
        // given:
        val either: Either<String, Number> = Left("text")
        // when:
        val result: Either<String, Int>? = either.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun filterToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterToOptionShouldReturnSomeWithTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterToOption { it > 0f }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterToOptionShouldReturnNoneIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterToOption { it > 0f }
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterNotToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotToOptionShouldReturnSomeWithTheSameRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterNotToOption { it > 0f }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotToOptionShouldReturnNoneIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterNotToOption { it > 0f }
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullToOptionShouldReturnSomeWithTheSameRightIfValueIsNotNull() {
        // given:
        val either: Either<Int, Float?> = Right(3.14f)
        // when:
        val result: Option<Either<Int, Float>> = either.filterNotNullToOption()
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullToOptionShouldReturnNoneIfValueIsNull() {
        // given:
        val either: Either<Int, Float?> = Right(null)
        // when:
        val result: Option<Either<Int, Float>> = either.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float?> = Left(1)
        // when:
        val result: Option<Either<Int, Float>> = either.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceToOptionShouldReturnSomeWithTheSameRightIfIsInstance() {
        // given:
        val either: Either<String, Number> = Right(1)
        // when:
        val result: Option<Either<String, Int>> = either.filterIsInstanceToOption()
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceToOptionShouldReturnNoneIfIsNotInstance() {
        // given:
        val either: Either<String, Number> = Right(1f)
        // when:
        val result: Option<Either<String, Int>> = either.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<String, Number> = Left("text")
        // when:
        val result: Option<Either<String, Int>> = either.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterOrElseShouldReturnTheSameLeftIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterOrElseShouldReturnTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterOrElseShouldReturnNewLeftIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(Left(0), result)
    }
}
