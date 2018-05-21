package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

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
}
