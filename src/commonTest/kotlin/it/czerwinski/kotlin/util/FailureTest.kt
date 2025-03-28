/*
 * Copyright 2018-2022 Slawomir Czerwinski
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FailureTest {

    @Test
    fun shouldNotBeSuccess() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.isSuccess
        // then:
        assertFalse(result)
    }

    @Test
    fun shouldBeFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.isFailure
        // then:
        assertTrue(result)
    }

    @Test
    fun getShouldThrowException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // then:
        assertFailsWith<RuntimeException>(message = "Test exception") {
            failure.get()
        }
    }

    @Test
    fun getOrNullShouldReturnNull() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    fun getOrElseShouldReturnDefaultValue() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.getOrElse { "default" }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun getOrElseShouldThrowExceptionThrownByDefault() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // then:
        assertFailsWith<NullPointerException>(message = "Default exception") {
            failure.getOrElse { throw NullPointerException("Default exception") }
        }
    }

    @Test
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
    fun onEachShouldNotBeCalled() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        var result = "default"
        // when:
        failure.onEach { result = it }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun onEachShouldReturnThis() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.onEach {}
        // then:
        assertEquals(failure, result)
    }

    @Test
    fun onSuccessShouldNotBeCalled() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        var result = "default"
        // when:
        failure.onSuccess { result = it }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun onSuccessShouldReturnThis() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.onSuccess {}
        // then:
        assertEquals(failure, result)
    }

    @Test
    fun onFailureShouldBeCalled() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        var result = "default"
        // when:
        failure.onFailure { result = it.message.orEmpty() }
        // then:
        assertEquals("Test exception", result)
    }

    @Test
    fun onFailureShouldReturnThis() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.onFailure {}
        // then:
        assertEquals(failure, result)
    }

    @Test
    fun mapShouldReturnTheSameFailure() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.map { it.toInt() }
        // then:
        assertEquals<Try<*>>(failure, result)
    }

    @Test
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
    fun flattenShouldReturnTheSameFailure() {
        // given:
        val failure: Try<Try<String>> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.flatten()
        // then:
        assertEquals<Try<*>>(failure, result)
    }

    @Test
    fun recoverShouldReturnSuccess() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.recover { exception -> exception.message }
        // then:
        assertEquals(Success("Test exception"), result)
    }

    @Test
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
    fun recoverWithShouldReturnSuccess() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.recoverWith { exception -> Success(exception.message) }
        // then:
        assertEquals(Success("Test exception"), result)
    }

    @Test
    fun recoverWithShouldReturnFailureWithNewException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val newException = NullPointerException()
        // when:
        val result = failure.recoverWith { Failure(newException) }
        // then:
        assertEquals(Failure(newException), result)
    }

    @Test
    fun recoverWithShouldReturnFailureWithThrownException() {
        // given:
        val failure: Try<String> = Failure(RuntimeException("Test exception"))
        val newException = NullPointerException()
        // when:
        val result = failure.recoverWith { throw newException }
        // then:
        assertEquals(Failure(newException), result)
    }

    @Test
    fun foldShouldTransformFailure() {
        // given:
        val failure: Try<Int> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.fold(
            { value -> value.toString() },
            { exception -> exception.message }
        )
        // then:
        assertEquals("Test exception", result)
    }

    @Test
    fun transformShouldReturnNewSuccess() {
        // given:
        val failure: Try<Int> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.transform(
            { value -> Success(value.toString()) },
            { exception -> Success(exception.message) }
        )
        // then:
        assertEquals(Success("Test exception"), result)
    }

    @Test
    fun transformShouldReturnFailureWithThrownException() {
        // given:
        val failure: Try<Int> = Failure(RuntimeException("Test exception"))
        val successException = IllegalArgumentException()
        val failureException = NullPointerException()
        // when:
        val result: Try<String> = failure.transform(
            { throw successException },
            { throw failureException }
        )
        // then:
        assertEquals(Failure(failureException), result)
    }

    @Test
    fun zipSuccessShouldReturnFailure() {
        // given:
        val exception = IllegalStateException()
        val try1: Try<Int> = Failure(exception)
        val try2: Try<String> = Success("text")
        // when:
        val result = try1 zip try2
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    fun zipFailureShouldReturnFailure() {
        // given:
        val exception1 = IllegalStateException()
        val exception2 = NullPointerException()
        val try1: Try<Int> = Failure(exception1)
        val try2: Try<String> = Failure(exception2)
        // when:
        val result = try1 zip try2
        // then:
        assertEquals(Failure(exception1), result)
    }

    @Test
    fun zipSuccessWithTransformShouldReturnFailure() {
        // given:
        val exception = IllegalStateException()
        val try1: Try<Int> = Failure(exception)
        val try2: Try<Float> = Success(3.14f)
        // when:
        val result = try1.zip(try2) { a, b -> a * b }
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    fun zipFailureWithTransformShouldReturnFailure() {
        // given:
        val exception1 = IllegalStateException()
        val exception2 = NullPointerException()
        val try1: Try<Int> = Failure(exception1)
        val try2: Try<Float> = Failure(exception2)
        // when:
        val result = try1.zip(try2) { a, b -> a * b }
        // then:
        assertEquals(Failure(exception1), result)
    }

    @Test
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
    fun toEitherShouldReturnLeft() {
        // given:
        val exception = RuntimeException("Test exception")
        val failure: Try<Int> = Failure(exception)
        // when:
        val result = failure.toEither()
        // then:
        assertEquals(Left(exception), result)
    }

    @Test
    fun toOptionShouldReturnNone() {
        // given:
        val exception = RuntimeException("Test exception")
        val failure: Try<Int> = Failure(exception)
        // when:
        val result = failure.toOption()
        // then:
        assertEquals(None, result)
    }
}
