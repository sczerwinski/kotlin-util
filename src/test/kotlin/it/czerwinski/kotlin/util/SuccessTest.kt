package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class SuccessTest {

    @Test
    @Throws(Exception::class)
    fun shouldNotBeSuccess() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.isSuccess
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldBeFailure() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.isFailure
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetValue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.get()
        // then:
        assertEquals("text", result)
    }
}
