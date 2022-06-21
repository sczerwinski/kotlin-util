/*
 * Copyright 2018-2021 Slawomir Czerwinski
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

class TryTest {

    @Test
    fun shouldBeEitherSuccessOrFailure() {
        // when:
        @Suppress("MoveVariableDeclarationIntoWhen")
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

    @Test
    fun shouldThrowFatalError() {
        // given:
        val error = AssertionError("Test exception")
        // then:
        assertFailsWith(AssertionError::class) {
            Try { throw error }
        }
    }
}
