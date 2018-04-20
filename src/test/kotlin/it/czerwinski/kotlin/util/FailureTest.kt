package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class FailureTest {

    @Test
    @Throws(Exception::class)
    fun shouldNotBeSuccess() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.isSuccess
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldBeFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.isFailure
        // then:
        assertTrue(result)
    }

    @Test(expected = RuntimeException::class)
    @Throws(Exception::class)
    fun getShouldThrowException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        failure.get()
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnNull() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnDefaultValue() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.getOrElse { "default" }
        // then:
        assertEquals("default", result)
    }

    @Test(expected = NullPointerException::class)
    @Throws(Exception::class)
    fun getOrElseShouldThrowExceptionThrownByDefault() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        failure.getOrElse { throw NullPointerException("Default exception") }
    }

    @Test
    @Throws(Exception::class)
    fun orElseShouldReturnDefaultValue() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val defaultException = NullPointerException()
        // when:
        val result = failure.orElse { Failure(defaultException) }
        // then:
        assertEquals(Failure(defaultException), result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldNotBeCalled() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        var result = "default"
        // when:
        failure.forEach { result = it }
        // then:
        assertEquals("default", result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.map { it.toInt() }
        // then:
        assertEquals(failure, result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldReturnTheSameFailureIfMappedWithNewException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val exception = NullPointerException()
        // when:
        val result = failure.map { throw exception }
        // then:
        assertEquals(failure, result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val newTry = Failure(NullPointerException())
        // when:
        val result = failure.flatMap { newTry }
        // then:
        assertEquals(failure, result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenShouldReturnTheSameFailure() {
        // given:
        val failure: Try<Try<String>> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.flatten()
        // then:
        assertEquals(failure, result)
    }

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
    fun recoverShouldReturnSuccess() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.recover { exception -> exception.message }
        // then:
        assertEquals(Success("Test exception"), result)
    }

    @Test
    @Throws(Exception::class)
    fun recoverShouldReturnFailureWithFrownException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val exception = NullPointerException()
        // when:
        val result = failure.recover { throw exception }
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    @Throws(Exception::class)
    fun recoverWithShouldReturnSuccess() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.recoverWith { exception -> Success(exception.message) }
        // then:
        assertEquals(Success("Test exception"), result)
    }

    @Test
    @Throws(Exception::class)
    fun recoverWithShouldReturnFailureWithNewException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val newException = NullPointerException()
        // when:
        val result = failure.recoverWith { _ -> Failure(newException) }
        // then:
        assertEquals(Failure(newException), result)
    }

    @Test
    @Throws(Exception::class)
    fun recoverWithShouldReturnFailureWithThrownException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val newException = NullPointerException()
        // when:
        val result = failure.recoverWith { _ -> throw newException }
        // then:
        assertEquals(Failure(newException), result)
    }

    @Test
    @Throws(Exception::class)
    fun transformShouldReturnNewSuccess() {
        // given:
        val failure: Try<Int> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.transform(
                { value -> Success(value.toString()) },
                { exception -> Success(exception.message) })
        // then:
        assertEquals(Success("Test exception"), result)
    }

    @Test
    @Throws(Exception::class)
    fun transformShouldReturnFailureWithThrownException() {
        // given:
        val failure: Try<Int> = Failure(RuntimeException("Test exception"))
        val successException = IllegalArgumentException()
        val failureException = NullPointerException()
        // when:
        val result: Try<String> = failure.transform(
                { _ -> throw successException },
                { _ -> throw failureException })
        // then:
        assertEquals(Failure(failureException), result)
    }

    @Test
    @Throws(Exception::class)
    fun failedShouldReturnSuccessWithTheSameException() {
        // given:
        val exception = RuntimeException("Test exception")
        val failure: Try<Int> = Failure(exception)
        // when:
        val result = failure.failed
        // then:
        assertEquals(Success(exception), result)
    }

    @Test
    @Throws(Exception::class)
    fun toEitherShouldReturnLeft() {
        // given:
        val exception = RuntimeException("Test exception")
        val failure: Try<Int> = Failure(exception)
        // when:
        val result = failure.toEither()
        // then:
        assertEquals(Left(exception), result)
    }
}
