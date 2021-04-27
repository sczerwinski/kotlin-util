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
import kotlin.test.assertNull
import kotlin.test.assertSame

class RightBiasedEitherFiltersTest {

    @Test
    fun filterShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    fun filterShouldReturnTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filter { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterShouldReturnNullIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filter { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterNot { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotShouldReturnTheSameRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterNot { it > 0f }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterNotShouldReturnNullIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterNot { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotNullShouldReturnTheSameRightIfValueIsNotNull() {
        // given:
        val either: Either<Int, Float?> = Right(3.14f)
        // when:
        val result: Either<Int, Float>? = either.filterNotNull()
        // then:
        assertEquals<Either<Int, Float?>?>(either, result)
    }

    @Test
    fun filterNotNullShouldReturnNullIfValueIsNull() {
        // given:
        val either: Either<Int, Float?> = Right(null)
        // when:
        val result: Either<Int, Float>? = either.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float?> = Left(1)
        // when:
        val result: Either<Int, Float>? = either.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    fun filterIsInstanceShouldReturnTheSameRightIfIsInstance() {
        // given:
        val either: Either<String, Number> = Right(1)
        // when:
        val result: Either<String, Int>? = either.filterIsInstance()
        // then:
        assertEquals<Either<String, Number>?>(either, result)
    }

    @Test
    fun filterIsInstanceShouldReturnNullIfIsNotInstance() {
        // given:
        val either: Either<String, Any> = Right("1")
        // when:
        val result: Either<String, Int>? = either.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    fun filterIsInstanceShouldReturnNullIfLeft() {
        // given:
        val either: Either<String, Number> = Left("text")
        // when:
        val result: Either<String, Int>? = either.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    fun filterToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterToOptionShouldReturnSomeWithTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterToOption { it > 0f }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterToOptionShouldReturnNoneIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterToOption { it > 0f }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterNotToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotToOptionShouldReturnSomeWithTheSameRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterNotToOption { it > 0f }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterNotToOptionShouldReturnNoneIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterNotToOption { it > 0f }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnSomeWithTheSameRightIfValueIsNotNull() {
        // given:
        val either: Either<Int, Float?> = Right(3.14f)
        // when:
        val result: Option<Either<Int, Float>> = either.filterNotNullToOption()
        // then:
        assertEquals<Option<Either<Int, Float?>>>(Some(either), result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnNoneIfValueIsNull() {
        // given:
        val either: Either<Int, Float?> = Right(null)
        // when:
        val result: Option<Either<Int, Float>> = either.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<Int, Float?> = Left(1)
        // when:
        val result: Option<Either<Int, Float>> = either.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnSomeWithTheSameRightIfIsInstance() {
        // given:
        val either: Either<String, Number> = Right(1)
        // when:
        val result: Option<Either<String, Int>> = either.filterIsInstanceToOption()
        // then:
        assertEquals<Option<Either<String, Number>>>(Some(either), result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnNoneIfIsNotInstance() {
        // given:
        val either: Either<String, Any> = Right("1")
        // when:
        val result: Option<Either<String, Int>> = either.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnNoneIfLeft() {
        // given:
        val either: Either<String, Number> = Left("text")
        // when:
        val result: Option<Either<String, Int>> = either.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterOrElseShouldReturnTheSameLeftIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterOrElseShouldReturnTheSameRightIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Right(1f)
        // when:
        val result = either.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterOrElseShouldReturnNewLeftIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Right(-1f)
        // when:
        val result = either.filterOrElse(
            predicate = { it > 0f },
            zero = { 0 }
        )
        // then:
        assertEquals(Left(0), result)
    }
}
