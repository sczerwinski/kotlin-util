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
import kotlin.test.assertTrue

class SomeTest {

    @Test
    fun toStringShouldReturnSomeOfValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.toString()
        // then:
        assertEquals("Some(value=text)", result)
    }

    @Test
    fun shouldFailToCreateSomeWithNull() {
        // given:
        val value = null
        // then:
        assertFailsWith<IllegalArgumentException>(message = "Cannot create Some(null)") {
            @Suppress("UNUSED_VARIABLE")
            val some: Option<String?> = Some(value)
        }
    }

    @Test
    fun shouldBeDefined() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.isDefined
        // then:
        assertTrue(result)
    }

    @Test
    fun shouldNotBeEmpty() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.isEmpty
        // then:
        assertFalse(result)
    }

    @Test
    fun shouldBeNotEmpty() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.isNotEmpty
        // then:
        assertTrue(result)
    }

    @Test
    fun iteratorShouldProduceSingleElement() {
        // given:
        val option: Option<String> = Some("text")
        val result = mutableListOf<String>()
        // when:
        option.iterator().forEach { result.add(it) }
        // then:
        assertEquals(listOf("text"), result)
    }

    @Test
    fun getShouldReturnValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.get()
        // then:
        assertEquals("text", result)
    }

    @Test
    fun getOrNullShouldReturnValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.getOrNull()
        // then:
        assertEquals("text", result)
    }

    @Test
    fun getOrElseShouldReturnValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.getOrElse { "default" }
        // then:
        assertEquals("text", result)
    }

    @Test
    fun orElseShouldReturnThis() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.orElse { Some("default") }
        // then:
        assertEquals(Some("text"), result)
    }

    @Test
    fun forEachShouldBeCalled() {
        // given:
        val option: Option<String> = Some("text")
        var result = "default"
        // when:
        option.forEach { result = it }
        // then:
        assertEquals("text", result)
    }

    @Test
    fun onEachShouldBeCalled() {
        // given:
        val option: Option<String> = Some("text")
        var result = "default"
        // when:
        option.onEach { result = it }
        // then:
        assertEquals("text", result)
    }

    @Test
    fun onEachShouldReturnThis() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.onEach {}
        // then:
        assertEquals(option, result)
    }

    @Test
    fun mapShouldReturnNewSome() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Option<String> = option.map { it.toString() }
        // then:
        assertEquals(Some("123"), result)
    }

    @Test
    fun flatMapShouldReturnNewSome() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Option<String> = option.flatMap { Option(it.toString()) }
        // then:
        assertEquals(Some("123"), result)
    }

    @Test
    fun flatMapShouldReturnNone() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Option<String> = option.flatMap { None }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun flattenShouldReturnInnerSome() {
        // given:
        val option: Option<Option<String>> = Some(Some("text"))
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(Some("text"), result)
    }

    @Test
    fun flattenShouldReturnInnerNone() {
        // given:
        val option: Option<Option<String>> = Some(None)
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(None, result)
    }

    @Test
    fun allShouldReturnTrueIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun allShouldReturnFalseIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.all { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun anyShouldReturnTrueIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.any { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun anyShouldReturnFalseIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnFalseIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.none { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnTrueIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun foldShouldReturnTransformedValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = option.fold("text") { it.toString() }
        // then:
        assertEquals("123", result)
    }

    @Test
    fun foldWithDefaultLambdaShouldReturnTransformedValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = option.fold({ "text" }) { it.toString() }
        // then:
        assertEquals("123", result)
    }

    @Test
    fun zipSomeShouldReturnSome() {
        // given:
        val option1: Option<Int> = Some(123)
        val option2: Option<String> = Some("text")
        // when:
        val result = option1 zip option2
        // then:
        assertEquals(Some(123 to "text"), result)
    }

    @Test
    fun zipNoneShouldReturnNone() {
        // given:
        val option1: Option<Int> = Some(123)
        val option2: Option<String> = None
        // when:
        val result = option1 zip option2
        // then:
        assertEquals(None, result)
    }

    @Test
    fun zipSomeWithTransformShouldReturnSome() {
        // given:
        val option1: Option<Int> = Some(2)
        val option2: Option<Float> = Some(3.14f)
        // when:
        val result = option1.zip(option2) { a, b -> a * b }
        // then:
        assertEquals(Some(6.28f), result)
    }

    @Test
    fun zipNoneWithTransformShouldReturnNone() {
        // given:
        val option1: Option<Int> = Some(2)
        val option2: Option<Float> = None
        // when:
        val result = option1.zip(option2) { a, b -> a * b }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun unzipSomePairShouldReturnPairOfSome() {
        // given:
        val option: Option<Pair<Int, String>> = Some(123 to "text")
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Some(123) to Some("text"), result)
    }

    @Test
    fun unzipSomePairWithNullFirstShouldReturnPairOfNoneAndSome() {
        // given:
        val option: Option<Pair<Int?, String?>> = Some(null to "text")
        // when:
        val result = option.unzip()
        // then:
        assertEquals(None to Some("text"), result)
    }

    @Test
    fun unzipSomePairWithNullSecondShouldReturnPairOfSomeAndNone() {
        // given:
        val option: Option<Pair<Int?, String?>> = Some(123 to null)
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Some(123) to None, result)
    }

    @Test
    fun unzipSomePairWithNullsShouldReturnPairOfNone() {
        // given:
        val option: Option<Pair<Int?, String?>> = Some(null to null)
        // when:
        val result = option.unzip()
        // then:
        assertEquals(None to None, result)
    }

    @Test
    fun unzipSomeTripleShouldReturnTripleOfSome() {
        // given:
        val option: Option<Triple<Int, String, Float>> = Some(Triple(123, "text", 4.5f))
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Triple(Some(123), Some("text"), Some(4.5f)), result)
    }

    @Test
    fun unzipSomeTripleWithNullFirstShouldReturnTripleOfNoneSomeAndSome() {
        // given:
        val option: Option<Triple<Int?, String?, Float?>> = Some(Triple(null, "text", 4.5f))
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Triple(None, Some("text"), Some(4.5f)), result)
    }

    @Test
    fun unzipSomeTripleWithNullSecondShouldReturnTripleOfSomeNoneAndSome() {
        // given:
        val option: Option<Triple<Int?, String?, Float?>> = Some(Triple(123, null, 4.5f))
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Triple(Some(123), None, Some(4.5f)), result)
    }

    @Test
    fun unzipSomeTripleWithNullThirdShouldReturnTripleOfSomeSomeAndNone() {
        // given:
        val option: Option<Triple<Int?, String?, Float?>> = Some(Triple(123, "text", null))
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Triple(Some(123), Some("text"), None), result)
    }

    @Test
    fun unzipSomeTripleWithNullsShouldReturnTripleOfNone() {
        // given:
        val option: Option<Triple<Int?, String?, Float?>> = Some(Triple(null, null, null))
        // when:
        val result = option.unzip()
        // then:
        assertEquals(Triple(None, None, None), result)
    }

    @Test
    fun containsShouldReturnTrueIfValueEqualsElement() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = 123 in option
        // then:
        assertTrue(result)
    }

    @Test
    fun containsShouldReturnFalseIfValueNotEqualsElement() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = 135 in option
        // then:
        assertFalse(result)
    }

    @Test
    fun containsShouldReturnFalseIfValueOfDifferentType() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = 3.14f in option
        // then:
        assertFalse(result)
    }

    @Test
    fun toListShouldReturnListContainingValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = option.toList()
        // then:
        assertEquals(listOf(123), result)
    }

    @Test
    fun toLeftShouldReturnLeftContainingValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Either<Int, String> = option.toLeft { "text" }
        // then:
        assertEquals(Left(123), result)
    }

    @Test
    fun toRightShouldReturnRightContainingValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Either<String, Int> = option.toRight { "text" }
        // then:
        assertEquals(Right(123), result)
    }

    @Test
    fun asIterableShouldReturnIterableReturningValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Iterable<Int> = option.asIterable()
        // then:
        assertEquals(listOf(123), result.toList())
    }

    @Test
    fun asSequenceShouldReturnSequenceReturningValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Sequence<Int> = option.asSequence()
        // then:
        assertEquals(listOf(123), result.toList())
    }
}
