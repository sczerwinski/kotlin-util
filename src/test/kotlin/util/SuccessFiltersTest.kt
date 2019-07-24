package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.IllegalStateException
import java.lang.NullPointerException

class SuccessFiltersTest {

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnTheSameSuccessIfPredicateIsTrue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filter { it == "text" }
        // then:
        assertEquals(success, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnFailureIfPredicateIsFalse() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filter { it == "other text" }
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnTheSameSuccessIfPredicateIsFalse() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filterNot { it == "other text" }
        // then:
        assertEquals(success, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnFailureIfPredicateIsTrue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filterNot { it == "text" }
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnTheSameSuccessIfValueIsNotNull() {
        // given:
        val success: Try<String?> = Success("text")
        // when:
        val result: Try<String> = success.filterNotNull()
        // then:
        assertEquals(Success("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnFailureIfValueIsNull() {
        // given:
        val success: Try<String?> = Success(null)
        // when:
        val result: Try<String> = success.filterNotNull()
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnSuccessIfIsInstance() {
        // given:
        val success: Try<Number> = Success(1)
        // when:
        val result: Try<Int> = success.filterIsInstance()
        // then:
        assertEquals(Success(1), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnFailureIfIsNotInstance() {
        // given:
        val success: Try<Number> = Success(1f)
        // when:
        val result: Try<Int> = success.filterIsInstance()
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    @Throws(Exception::class)
    fun filterOrElseShouldReturnTheSameSuccessIfPredicateIsTrue() {
        // given:
        val success: Try<Int> = Success(1)
        val exception = IllegalStateException("Negative number")
        // when:
        val result = success.filterOrElse(
            predicate = { it >= 0 },
            throwable = { exception }
        )
        // then:
        assertEquals(success, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterOrElseShouldReturnFailureIfPredicateIsFalse() {
        // given:
        val success: Try<Int> = Success(-1)
        val exception = IllegalStateException("Negative number")
        // when:
        val result = success.filterOrElse(
            predicate = { it >= 0 },
            throwable = { exception }
        )
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterOrElseShouldReturnFailureThrownByPredicate() {
        // given:
        val success: Try<Int> = Success(-1)
        val exception = IllegalStateException("Negative number")
        val predicateException = NullPointerException()
        // when:
        val result = success.filterOrElse(
            predicate = { throw predicateException },
            throwable = { exception }
        )
        // then:
        assertEquals(Failure(predicateException), result)
    }
}
