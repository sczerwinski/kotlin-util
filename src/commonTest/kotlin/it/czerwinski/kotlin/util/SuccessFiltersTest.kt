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
import kotlin.test.assertTrue

class SuccessFiltersTest {

    @Test
    fun filterShouldReturnTheSameSuccessIfPredicateIsTrue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filter { it == "text" }
        // then:
        assertEquals(success, result)
    }

    @Test
    fun filterShouldReturnFailureIfPredicateIsFalse() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filter { it == "other text" }
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    fun filterNotShouldReturnTheSameSuccessIfPredicateIsFalse() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filterNot { it == "other text" }
        // then:
        assertEquals(success, result)
    }

    @Test
    fun filterNotShouldReturnFailureIfPredicateIsTrue() {
        // given:
        val success: Try<String> = Success("text")
        // when:
        val result = success.filterNot { it == "text" }
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    fun filterNotNullShouldReturnTheSameSuccessIfValueIsNotNull() {
        // given:
        val success: Try<String?> = Success("text")
        // when:
        val result: Try<String> = success.filterNotNull()
        // then:
        assertEquals(Success("text"), result)
    }

    @Test
    fun filterNotNullShouldReturnFailureIfValueIsNull() {
        // given:
        val success: Try<String?> = Success(null)
        // when:
        val result: Try<String> = success.filterNotNull()
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    fun filterIsInstanceShouldReturnSuccessIfIsInstance() {
        // given:
        val success: Try<Number> = Success(1)
        // when:
        val result: Try<Int> = success.filterIsInstance()
        // then:
        assertEquals(Success(1), result)
    }

    @Test
    fun filterIsInstanceShouldReturnFailureIfIsNotInstance() {
        // given:
        val success: Try<Any> = Success("1")
        // when:
        val result: Try<Int> = success.filterIsInstance()
        // then:
        assertTrue(result.isFailure)
    }

    @Test
    fun filterOrElseShouldReturnTheSameSuccessIfPredicateIsTrue() {
        // given:
        val success: Try<Int> = Success(1)
        val exception = IllegalStateException("Negative number")
        // when:
        val result = success.filterOrElse(
            predicate = { it >= 0 },
            throwable = { exception }
        )
        // then:
        assertEquals(success, result)
    }

    @Test
    fun filterOrElseShouldReturnFailureIfPredicateIsFalse() {
        // given:
        val success: Try<Int> = Success(-1)
        val exception = IllegalStateException("Negative number")
        // when:
        val result = success.filterOrElse(
            predicate = { it >= 0 },
            throwable = { exception }
        )
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    fun filterOrElseShouldReturnFailureThrownByPredicate() {
        // given:
        val success: Try<Int> = Success(-1)
        val exception = IllegalStateException("Negative number")
        val predicateException = NullPointerException()
        // when:
        val result = success.filterOrElse(
            predicate = { throw predicateException },
            throwable = { exception }
        )
        // then:
        assertEquals(Failure(predicateException), result)
    }
}
