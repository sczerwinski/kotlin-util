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

class NoneTest {

    @Test
    fun toStringShouldReturnNone() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.toString()
        // then:
        assertEquals("None", result)
    }

    @Test
    fun shouldBeEmpty() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.isEmpty
        // then:
        assertTrue(result)
    }

    @Test
    fun shouldNotBeNotEmpty() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.isNotEmpty
        // then:
        assertFalse(result)
    }

    @Test
    fun shouldNotBeDefined() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.isDefined
        // then:
        assertFalse(result)
    }

    @Test
    fun iteratorShouldBeEmpty() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.iterator()
        // then:
        assertFalse(result.hasNext())
    }

    @Test
    fun getShouldThrowException() {
        // given:
        val option: Option<String> = None
        // then:
        assertFailsWith<NoSuchElementException>(message = "Getting value of None") {
            option.get()
        }
    }

    @Test
    fun getOrNullShouldReturnNull() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    fun getOrElseShouldReturnDefaultValue() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.getOrElse { "default" }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun orElseShouldReturnDefaultOption() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.orElse { Some("default") }
        // then:
        assertEquals(Some("default"), result)
    }

    @Test
    fun forEachShouldNotBeCalled() {
        // given:
        val option: Option<String> = None
        var result = "default"
        // when:
        option.forEach { result = it }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun mapShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Option<String> = option.map { it.toString() }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun flatMapShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Option<String> = option.flatMap { Option(it.toString()) }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun flattenShouldReturnNone() {
        // given:
        val option: Option<Option<String>> = None
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(None, result)
    }

    @Test
    fun allShouldReturnTrue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun anyShouldReturnFalse() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnTrue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun foldShouldReturnDefaultValue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.fold("text") { it.toString() }
        // then:
        assertEquals("text", result)
    }

    @Test
    fun foldShouldReturnDefaultResult() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.fold({ "text" }) { it.toString() }
        // then:
        assertEquals("text", result)
    }

    @Test
    fun zipSomeShouldReturnNone() {
        // given:
        val option1: Option<Int> = None
        val option2: Option<String> = Some("text")
        // when:
        val result = option1 zip option2
        // then:
        assertEquals(None, result)
    }

    @Test
    fun zipNoneShouldReturnNone() {
        // given:
        val option1: Option<Int> = None
        val option2: Option<String> = None
        // when:
        val result = option1 zip option2
        // then:
        assertEquals(None, result)
    }

    @Test
    fun zipSomeWithTransformShouldReturnNone() {
        // given:
        val option1: Option<Int> = None
        val option2: Option<Float> = Some(3.14f)
        // when:
        val result = option1.zip(option2) { a, b -> a * b }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun zipNoneWithTransformShouldReturnNone() {
        // given:
        val option1: Option<Int> = None
        val option2: Option<Float> = None
        // when:
        val result = option1.zip(option2) { a, b -> a * b }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun unzipNonePairShouldReturnPairOfNone() {
        // given:
        val option: Option<Pair<Int, String>> = None
        // when:
        val result = option.unzip()
        // then:
        assertEquals(None to None, result)
    }

    @Test
    fun unzipNoneTripleShouldReturnTripleOfNone() {
        // given:
        val option: Option<Triple<Int, String, Float>> = None
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Triple(None, None, None), result)
    }

    @Test
    fun containsShouldReturnFalse() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = 123 in option
        // then:
        assertFalse(result)
    }

    @Test
    fun toListShouldReturnEmptyList() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.toList()
        // then:
        assertTrue(result.isEmpty())
    }

    @Test
    fun toLeftShouldReturnRightWithDefaultValue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Either<Int, String> = option.toLeft { "text" }
        // then:
        assertEquals(Right("text"), result)
    }

    @Test
    fun toRightShouldReturnLeftWithDefaultValue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Either<String, Int> = option.toRight { "text" }
        // then:
        assertEquals(Left("text"), result)
    }

    @Test
    fun asIterableShouldReturnEmptyIterable() {
        // given:
        val option: Option<String> = None
        // when:
        val result: Iterable<String> = option.asIterable()
        // then:
        assertFalse(result.iterator().hasNext())
    }

    @Test
    fun asSequenceShouldReturnEmptySequence() {
        // given:
        val option: Option<String> = None
        // when:
        val result: Sequence<String> = option.asSequence()
        // then:
        assertFalse(result.iterator().hasNext())
    }
}
