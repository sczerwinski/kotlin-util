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
import kotlin.test.assertSame

class OptionTest {

    @Test
    fun shouldCreateSome() {
        // given:
        val text = "text"
        // when:
        val result: Option<String> = Option(text)
        // then:
        assertEquals(Some(text), result)
    }

    @Test
    fun shouldReturnNone() {
        // given:
        val text: String? = null
        // when:
        val result: Option<String> = Option(text)
        // then:
        assertSame(None, result)
    }

    @Test
    fun emptyShouldReturnNone() {
        // when:
        val result: Option<String> = Option.empty()
        // then:
        assertSame(None, result)
    }

    @Test
    fun toOptionShouldCreateSome() {
        // given:
        val text = "text"
        // when:
        val result: Option<String> = text.asOption()
        // then:
        assertEquals(Some(text), result)
    }

    @Test
    fun toOptionShouldReturnNone() {
        // given:
        val text: String? = null
        // when:
        val result: Option<String> = text.asOption()
        // then:
        assertSame(None, result)
    }
}
