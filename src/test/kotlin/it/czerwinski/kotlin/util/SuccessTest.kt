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
    fun getShouldReturnValue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.get()
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnValue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.getOrNull()
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnValue() {
        // given:
        val success = Success("text")
        // when:
        val result = success.getOrElse { "default" }
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun orElseShouldReturnThis() {
        // given:
        val success = Success("text")
        // when:
        val result = success.orElse { Failure(NullPointerException()) }
        // then:
        assertEquals(success, result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldBeCalled() {
        // given:
        val success = Success("text")
        var result = "default"
        // when:
        success.forEach { result = it }
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldMapValueOfSuccess() {
        // given:
        val success = Success("123")
        // when:
        val result = success.map { it.toInt() }
        // then:
        assertEquals(Success(123), result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldMapValueOfSuccessToFailure() {
        // given:
        val success = Success("123")
        val exception = NullPointerException()
        // when:
        val result = success.map { throw exception }
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldReturnNewTry() {
        // given:
        val success = Success("123")
        val newTry = Failure(NullPointerException())
        // when:
        val result = success.flatMap { newTry }
        // then:
        assertEquals(newTry, result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldReturnFailureIfExceptionIsThrown() {
        // given:
        val success = Success("123")
        val exception = RuntimeException()
        // when:
        val result: Try<Int> = success.flatMap { throw exception }
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenShouldReturnInnerSuccess() {
        // given:
        val success: Try<Try<String>> = Success(Success("text"))
        // when:
        val result = success.flatten()
        // then:
        assertEquals(Success("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenShouldReturnInnerFailure() {
        // given:
        val exception = RuntimeException()
        val success: Try<Try<String>> = Success(Failure(exception))
        // when:
        val result = success.flatten()
        // then:
        assertEquals(Failure(exception), result)
    }
}
