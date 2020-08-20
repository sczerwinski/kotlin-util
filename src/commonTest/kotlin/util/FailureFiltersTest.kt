package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals

class FailureFiltersTest {

    @Test
    fun filterShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.filter { it == "text" }
        // then:
        assertEquals(failure, result)
    }

    @Test
    fun filterNotShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.filterNot { it == "text" }
        // then:
        assertEquals(failure, result)
    }

    @Test
    fun filterNotNullShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String?> = Failure(RuntimeException("Test exception"))
        // when:
        val result: Try<String> = failure.filterNotNull()
        // then:
        assertEquals(failure, result)
    }

    @Test
    fun filterIsInstanceShouldReturnTheSameFailure() {
        // given:
        val failure: Try<Number> = Failure(RuntimeException("Test exception"))
        // when:
        val result: Try<Int> = failure.filterIsInstance()
        // then:
        assertEquals(failure, result)
    }

    @Test
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
