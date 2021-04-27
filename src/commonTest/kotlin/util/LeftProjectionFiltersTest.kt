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

class LeftProjectionFiltersTest {

    @Test
    fun filterShouldReturnTheSameLeftIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.filter { it > 0 }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterShouldReturnNullIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    fun filterShouldReturnNullIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.filter { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotShouldReturnTheSameLeftIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.filterNot { it > 0 }
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterNotShouldReturnNullIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.filterNot { it > 0 }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotShouldReturnNullIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.filterNot { it > 0f }
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotNullShouldReturnTheSameLeftIfValueIsNotNull() {
        // given:
        val either: Either<Int?, Float> = Left(1)
        // when:
        val result: Either<Int, Float>? = either.left.filterNotNull()
        // then:
        assertEquals<Either<Int?, Float>?>(either, result)
    }

    @Test
    fun filterNotNullShouldReturnNullIfValueIsNull() {
        // given:
        val either: Either<Int?, Float> = Left(null)
        // when:
        val result: Either<Int, Float>? = either.left.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    fun filterNotNullShouldReturnNullIfRight() {
        // given:
        val either: Either<Int?, Float> = Right(3.14f)
        // when:
        val result: Either<Int, Float>? = either.left.filterNotNull()
        // then:
        assertNull(result)
    }

    @Test
    fun filterIsInstanceShouldReturnTheSameLeftIfIsInstance() {
        // given:
        val either: Either<Number, String> = Left(1)
        // when:
        val result: Either<Int, String>? = either.left.filterIsInstance()
        // then:
        assertEquals<Either<Number?, String>?>(either, result)
    }

    @Test
    fun filterIsInstanceShouldReturnNullIfIsNotInstance() {
        // given:
        val either: Either<Any, String> = Left("1")
        // when:
        val result: Either<Int, String>? = either.left.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    fun filterIsInstanceShouldReturnNullIfRight() {
        // given:
        val either: Either<Number, String> = Right("text")
        // when:
        val result: Either<Int, String>? = either.left.filterIsInstance()
        // then:
        assertNull(result)
    }

    @Test
    fun filterToOptionShouldReturnSomeWithTheSameLeftIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.filterToOption { it > 0 }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterToOptionShouldReturnNoneIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.filterToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterToOptionShouldReturnNoneIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.filterToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotShouldToOptionReturnSomeWithTheSameLeftIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.filterNotToOption { it > 0 }
        // then:
        assertEquals(Some(either), result)
    }

    @Test
    fun filterNotToOptionShouldReturnNoneIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.filterNotToOption { it > 0 }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotToOptionShouldReturnNoneIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.filterNotToOption { it > 0f }
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnSomeWithTheSameLeftIfValueIsNotNull() {
        // given:
        val either: Either<Int?, Float> = Left(1)
        // when:
        val result: Option<Either<Int, Float>> = either.left.filterNotNullToOption()
        // then:
        assertEquals<Option<Either<Int?, Float>>>(Some(either), result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnNoneIfValueIsNull() {
        // given:
        val either: Either<Int?, Float> = Left(null)
        // when:
        val result: Option<Either<Int, Float>> = either.left.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterNotNullToOptionShouldReturnNoneIfRight() {
        // given:
        val either: Either<Int?, Float> = Right(3.14f)
        // when:
        val result: Option<Either<Int, Float>> = either.left.filterNotNullToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnSomeWithTheSameLeftIfIsInstance() {
        // given:
        val either: Either<Number, String> = Left(1)
        // when:
        val result: Option<Either<Int, String>> = either.left.filterIsInstanceToOption()
        // then:
        assertEquals<Option<Either<Number, String>>>(Some(either), result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnNoneIfIsNotInstance() {
        // given:
        val either: Either<Any, String> = Left("1")
        // when:
        val result: Option<Either<Int, String>> = either.left.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterIsInstanceToOptionShouldReturnNoneIfRight() {
        // given:
        val either: Either<Number, String> = Right("text")
        // when:
        val result: Option<Either<Int, String>> = either.left.filterIsInstanceToOption()
        // then:
        assertSame(None, result)
    }

    @Test
    fun filterOrElseShouldReturnTheSameLeftIfPredicateIsTrue() {
        // given:
        val either: Either<Int, Float> = Left(1)
        // when:
        val result = either.left.filterOrElse(
            predicate = { it > 0 },
            zero = { 0f }
        )
        // then:
        assertEquals(either, result)
    }

    @Test
    fun filterOrElseShouldReturnNewRightIfPredicateIsFalse() {
        // given:
        val either: Either<Int, Float> = Left(-1)
        // when:
        val result = either.left.filterOrElse(
            predicate = { it > 0 },
            zero = { 0f }
        )
        // then:
        assertEquals(Right(0f), result)
    }

    @Test
    fun filterOrElseShouldReturnTheSameRightIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.filterOrElse(
            predicate = { it > 0 },
            zero = { 0f }
        )
        // then:
        assertEquals(either, result)
    }
}
