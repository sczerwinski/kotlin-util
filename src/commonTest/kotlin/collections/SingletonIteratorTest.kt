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

package it.czerwinski.kotlin.collections

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SingletonIteratorTest {

    @Test
    fun hasNextShouldReturnTrue() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        val result = iterator.hasNext()
        // then:
        assertTrue(result)
    }

    @Test
    fun hasNextShouldReturnFalseAfterFirstElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        iterator.next()
        val result = iterator.hasNext()
        // then:
        assertFalse(result)
    }

    @Test
    fun nextShouldReturnElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        val result = iterator.next()
        // then:
        assertEquals("text", result)
    }

    @Test
    fun nextShouldThrowExceptionAfterFirstElement() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        // when:
        iterator.next()
        // then:
        assertFailsWith<NoSuchElementException>(message = "Only one element available") {
            iterator.next()
        }
    }

    @Test
    fun shouldIterateOnce() {
        // given:
        val iterator: Iterator<String> = SingletonIterator("text")
        var count = 0
        // when:
        for (string in iterator) {
            count++
        }
        // then:
        assertEquals(1, count)
    }
}
