package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalStateException

class FailureFiltersTest {

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.filter { it == "text" }
        // then:
        assertEquals(failure, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.filterNot { it == "text" }
        // then:
        assertEquals(failure, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotNullShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String?> = Failure(RuntimeException("Test exception"))
        // when:
        val result: Try<String> = failure.filterNotNull()
        // then:
        assertEquals(failure, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnTheSameFailure() {
        // given:
        val failure: Try<Number> = Failure(RuntimeException("Test exception"))
        // when:
        val result: Try<Int> = failure.filterIsInstance()
        // then:
        assertEquals(failure, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterOrElseShouldReturnTheSameFailure() {
        // given:
        val failure: Try<Int> = Failure(RuntimeException("Test exception"))
        val exception = IllegalStateException("Negative number")
        // when:
        val result = failure.filterOrElse(
            predicate = { it >= 0 },
            throwable = { exception }
        )
        // then:
        assertEquals(failure, result)
    }
}
