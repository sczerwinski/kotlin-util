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

class RightBiasedEitherTest {

    @Test
    fun getShouldThrowExceptionIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // then:
        assertFailsWith<NoSuchElementException>(message = "Getting Right value from Left") {
            either.get()
        }
    }

    @Test
    fun getShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.get()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    fun getOrNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    fun getOrNullShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.getOrNull()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    fun getOrElseShouldReturnDefaultValueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.getOrElse { -1f }
        // then:
        assertEquals(-1f, result)
    }

    @Test
    fun getOrElseShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.getOrElse { -1f }
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    fun forEachShouldNotBeCalledIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        var result = "default"
        // when:
        either.forEach { result = it.toString() }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun forEachShouldBeCalledIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        var result = "default"
        // when:
        either.forEach { result = it.toString() }
        // then:
        assertEquals("3.14", result)
    }

    @Test
    fun mapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.map { it.toString() }
        // then:
        assertEquals<Either<Int, *>>(either, result)
    }

    @Test
    fun mapShouldTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.map { it.toString() }
        // then:
        assertEquals(Right("3.14"), result)
    }

    @Test
    fun flatMapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<Int, String> = either.flatMap { Right(it.toString()) }
        // then:
        assertEquals<Either<Int, *>>(either, result)
    }

    @Test
    fun flatMapShouldTransformRightToLeft() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.flatMap { Left(it.toInt()) }
        // then:
        assertEquals(Left(3), result)
    }

    @Test
    fun flatMapShouldTransformRightToRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.flatMap { Right(it.toString()) }
        // then:
        assertEquals(Right("3.14"), result)
    }

    @Test
    fun allShouldReturnTrueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun allShouldReturnTrueIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.all { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    fun allShouldReturnFalseIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.all { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    fun anyShouldReturnFalseIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun anyShouldReturnFalseIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.any { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    fun anyShouldReturnFalseIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.any { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnTrueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun noneShouldReturnFalseIfRightAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.none { it > 0f }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnTrueIfRightAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.none { it > 0f }
        // then:
        assertTrue(result)
    }

    @Test
    fun toOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.toOption()
        // then:
        assertEquals(None, result)
    }

    @Test
    fun toOptionShouldReturnSomeWithRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.toOption()
        // then:
        assertEquals(Some(3.14f), result)
    }
}
