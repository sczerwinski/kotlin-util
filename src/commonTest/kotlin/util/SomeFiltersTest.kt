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

class SomeFiltersTest {

    @Test
    fun filterShouldReturnTheSameSomeIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(option, result)
    }

    @Test
    fun filterShouldReturnNoneIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun filterNotShouldReturnTheSameSomeIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(option, result)
    }

    @Test
    fun filterNotShouldReturnNoneIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun filterIsInstanceShouldReturnSomeIfIsInstance() {
        // given:
        val option: Option<Number> = Some(1)
        // when:
        val result: Option<Int> = option.filterIsInstance<Int>()
        // then:
        assertEquals(Some(1), result)
    }

    @Test
    fun filterIsInstanceShouldReturnNoneIfIsNotInstance() {
        // given:
        val option: Option<Any> = Some("1")
        // when:
        val result: Option<Float> = option.filterIsInstance<Float>()
        // then:
        assertEquals(None, result)
    }
}
