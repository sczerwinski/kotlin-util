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
