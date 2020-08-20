package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

@Suppress("DEPRECATION")
class RightProjectionFiltersTest {

    @Test
    fun filterShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    fun filterShouldReturnTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.filter { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterShouldReturnNullIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.filter { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.filterNot { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotShouldReturnTheSameRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.filterNot { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterNotShouldReturnNullIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.filterNot { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotNullShouldReturnTheSameRightIfValueIsNotNull() {
        // given:
        val either: Either<Int, Float?> = Right(3.14f)
        // when:
        val result: Either<Int, Float>? = either.right.filterNotNull()
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterNotNullShouldReturnNullIfValueIsNull() {
        // given:
        val either: Either<Int, Float?> = Right(null)
        // when:
        val result: Either<Int, Float>? = either.right.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float?> = Left(1)
        // when:
        val result: Either<Int, Float>? = either.right.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    fun filterIsInstanceShouldReturnTheSameRightIfIsInstance() {
        // given:
        val either: Either<String, Number> = Right(1)
        // when:
        val result: Either<String, Int>? = either.right.filterIsInstance()
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterIsInstanceShouldReturnNullIfIsNotInstance() {
        // given:
        val either: Either<String, Any> = Right("1")
        // when:
        val result: Either<String, Int>? = either.right.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    fun filterIsInstanceShouldReturnNullIfLeft() {
        // given:
        val either: Either<String, Number> = Left("text")
        // when:
        val result: Either<String, Int>? = either.right.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    fun filterToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.filterToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterToOptionShouldReturnSomeWithTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.filterToOption { it > 0f }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterToOptionShouldReturnNoneIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.filterToOption { it > 0f }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.filterNotToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotToOptionShouldReturnSomeWithTheSameRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.filterNotToOption { it > 0f }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterNotToOptionShouldReturnNoneIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.filterNotToOption { it > 0f }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnSomeWithTheSameRightIfValueIsNotNull() {
        // given:
        val either: Either<Int, Float?> = Right(3.14f)
        // when:
        val result: Option<Either<Int, Float>> = either.right.filterNotNullToOption()
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnNoneIfValueIsNull() {
        // given:
        val either: Either<Int, Float?> = Right(null)
        // when:
        val result: Option<Either<Int, Float>> = either.right.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float?> = Left(1)
        // when:
        val result: Option<Either<Int, Float>> = either.right.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnSomeWithTheSameRightIfIsInstance() {
        // given:
        val either: Either<String, Number> = Right(1)
        // when:
        val result: Option<Either<String, Int>> = either.right.filterIsInstanceToOption()
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnNoneIfIsNotInstance() {
        // given:
        val either: Either<String, Any> = Right("1")
        // when:
        val result: Option<Either<String, Int>> = either.right.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<String, Number> = Left("text")
        // when:
        val result: Option<Either<String, Int>> = either.right.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterOrElseShouldReturnTheSameLeftIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterOrElseShouldReturnTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.right.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterOrElseShouldReturnNewLeftIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.right.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(Left(0), result)
    }
}
