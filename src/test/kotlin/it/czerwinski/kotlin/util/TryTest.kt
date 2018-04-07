package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Test

class TryTest {

    @Test
    @Throws(Exception::class)
    fun shouldBeEitherSuccessOrFailure() {
        // when:
        val result = Try {}
        // then:
        @Suppress("UNUSED_VARIABLE")
        val value: Any = when (result) {
            is Success -> result.value
            is Failure -> result.exception
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldCreateSuccess() {
        // given:
        val text = "text"
        // when:
        val result = Try { text }
        // then:
        assertEquals(Success(text), result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldCreateFailure() {
        // given:
        val exception = RuntimeException("Test exception")
        // when:
        val result = Try { throw exception }
        // then:
        assertEquals(Failure(exception), result)
    }
}
