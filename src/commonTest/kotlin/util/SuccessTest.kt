package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SuccessTest {

    @Test
    fun shouldBeSuccess() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.isSuccess
        // then:
        assertTrue(result)
    }

    @Test
    fun shouldNotBeFailure() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.isFailure
        // then:
        assertFalse(result)
    }

    @Test
    fun getShouldReturnValue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.get()
        // then:
        assertEquals("text", result)
    }

    @Test
    fun getOrNullShouldReturnValue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.getOrNull()
        // then:
        assertEquals("text", result)
    }

    @Test
    fun getOrElseShouldReturnValue() {
        // given:
        val success = Success("text")
        // when:
        val result = success.getOrElse { "default" }
        // then:
        assertEquals("text", result)
    }

    @Test
    fun orElseShouldReturnThis() {
        // given:
        val success = Success("text")
        // when:
        val result = success.orElse { Failure(NullPointerException()) }
        // then:
        assertEquals(success, result)
    }

    @Test
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
    fun mapShouldMapValueOfSuccess() {
        // given:
        val success = Success("123")
        // when:
        val result = success.map { it.toInt() }
        // then:
        assertEquals(Success(123), result)
    }

    @Test
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
    fun flattenShouldReturnInnerSuccess() {
        // given:
        val success: Try<Try<String>> = Success(Success("text"))
        // when:
        val result = success.flatten()
        // then:
        assertEquals(Success("text"), result)
    }

    @Test
    fun flattenShouldReturnInnerFailure() {
        // given:
        val exception = RuntimeException()
        val success: Try<Try<String>> = Success(Failure(exception))
        // when:
        val result = success.flatten()
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    fun recoverShouldReturnTheSameSuccess() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.recover { exception -> exception.message }
        // then:
        assertEquals(Success("text"), result)
    }

    @Test
    fun recoverWithShouldReturnTheSameSuccess() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.recoverWith { exception -> Success(exception.message) }
        // then:
        assertEquals(Success("text"), result)
    }

    @Test
    fun foldShouldTransformSuccess() {
        // given:
        val success: Try<Int> = Success(123)
        // when:
        val result = success.fold(
            { value -> value.toString() },
            { exception -> exception.message }
        )
        // then:
        assertEquals("123", result)
    }

    @Test
    fun transformShouldReturnNewSuccess() {
        // given:
        val success: Try<Int> = Success(123)
        // when:
        val result = success.transform(
            { value -> Success(value.toString()) },
            { exception -> Success(exception.message) }
        )
        // then:
        assertEquals(Success("123"), result)
    }

    @Test
    fun transformShouldReturnFailureWithThrownException() {
        // given:
        val success: Try<Int> = Success(123)
        val successException = IllegalArgumentException()
        val failureException = NullPointerException()
        // when:
        val result: Try<String> = success.transform(
            { _ -> throw successException },
            { _ -> throw failureException }
        )
        // then:
        assertEquals(Failure(successException), result)
    }

    @Test
    fun zipSuccessShouldReturnSuccess() {
        // given:
        val try1: Try<Int> = Success(123)
        val try2: Try<String> = Success("text")
        // when:
        val result = try1 zip try2
        // then:
        assertEquals(Success(123 to "text"), result)
    }

    @Test
    fun zipFailureShouldReturnFailure() {
        // given:
        val exception = NullPointerException()
        val try1: Try<Int> = Success(123)
        val try2: Try<String> = Failure(exception)
        // when:
        val result = try1 zip try2
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    fun zipSuccessWithTransformShouldReturnSuccess() {
        // given:
        val try1: Try<Int> = Success(2)
        val try2: Try<Float> = Success(3.14f)
        // when:
        val result = try1.zip(try2) { a, b -> a * b }
        // then:
        assertEquals(Success(6.28f), result)
    }

    @Test
    fun zipFailureWithTransformShouldReturnFailure() {
        // given:
        val exception = NullPointerException()
        val try1: Try<Int> = Success(123)
        val try2: Try<Float> = Failure(exception)
        // when:
        val result = try1.zip(try2) { a, b -> a * b }
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    fun failedShouldReturnFailure() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.failed
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    fun toEitherShouldReturnRight() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.toEither()
        // then:
        assertEquals(Right("text"), result)
    }

    @Test
    fun toOptionShouldReturnSome() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.toOption()
        // then:
        assertEquals(Some("text"), result)
    }
}
