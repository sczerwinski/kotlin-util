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
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LeftProjectionTest {

    @Test
    fun getShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.get()
        // then:
        assertEquals(256, result)
    }

    @Test
    fun getShouldThrowExceptionIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // then:
        assertFailsWith<NoSuchElementException>(message = "Getting Left value from Right") {
            either.left.get()
        }
    }

    @Test
    fun getOrNullShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.getOrNull()
        // then:
        assertEquals(256, result)
    }

    @Test
    fun getOrNullShouldReturnNullIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    fun getOrElseShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.getOrElse { -1 }
        // then:
        assertEquals(256, result)
    }

    @Test
    fun getOrElseShouldReturnDefaultValueIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.getOrElse { -1 }
        // then:
        assertEquals(-1, result)
    }

    @Test
    fun forEachShouldBeCalledIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        var result = "default"
        // when:
        either.left.forEach { result = it.toString() }
        // then:
        assertEquals("256", result)
    }

    @Test
    fun forEachShouldNotBeCalledIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        var result = "default"
        // when:
        either.left.forEach { result = it.toString() }
        // then:
        assertEquals("default", result)
    }

    @Test
    fun mapShouldTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.map { it.toString() }
        // then:
        assertEquals(Left("256"), result)
    }

    @Test
    fun mapShouldNotTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.map { it.toString() }
        // then:
        assertEquals<Either<*, Float>>(either, result)
    }

    @Test
    fun flatMapShouldTransformLeftToLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<String, Float> = either.left.flatMap { Left(it.toString()) }
        // then:
        assertEquals(Left("256"), result)
    }

    @Test
    fun flatMapShouldTransformLeftToRight() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<String, Float> = either.left.flatMap { Right(it * 0.1f) }
        // then:
        assertEquals(Right(25.6f), result)
    }

    @Test
    fun flatMapShouldNotTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<String, Float> = either.left.flatMap { Left(it.toString()) }
        // then:
        assertEquals<Either<*, Float>>(either, result)
    }

    @Test
    fun allShouldReturnTrueIfLeftAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun allShouldReturnFalseIfLeftAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.all { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun allShouldReturnTrueIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun anyShouldReturnTrueIfLeftAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.any { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun anyShouldReturnFalseIfLeftAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun anyShouldReturnFalseIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnFalseIfLeftAndPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.none { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    fun noneShouldReturnTrueIfLeftAndPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun noneShouldReturnTrueIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    fun toOptionShouldReturnSomeWithLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.toOption()
        // then:
        assertEquals(Some(256), result)
    }

    @Test
    fun toOptionShouldReturnNoneIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.toOption()
        // then:
        assertEquals(None, result)
    }
}
