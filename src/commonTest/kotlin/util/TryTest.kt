package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals

class TryTest {

    @Test
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
    fun shouldCreateSuccess() {
        // given:
        val text = "text"
        // when:
        val result = Try { text }
        // then:
        assertEquals(Success(text), result)
    }

    @Test
    fun shouldCreateFailure() {
        // given:
        val exception = RuntimeException("Test exception")
        // when:
        val result = Try { throw exception }
        // then:
        assertEquals(Failure(exception), result)
    }
}
