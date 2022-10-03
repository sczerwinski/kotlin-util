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

/**
 * Extracts an [Option] nested in the [Try] to a not nested [Option].
 *
 * @return [Option] nested in a [Success] or [None] if this is a [Failure].
 */
fun <T> Try<Option<T>>.flatten(): Option<T> = when (this) {
    is Success -> value
    is Failure -> None
}

/**
 * Returns [Some] if this [Some] contains a [Success]. Otherwise returns [None].
 *
 * @return [Some] if this [Some] contains a [Success]. Otherwise returns [None].
 */
fun <T> Option<Try<T>>.flatten(): Option<T> =
    if (isEmpty) None else get().toOption()

/**
 * Returns nested [List] if this is [Some]. Otherwise returns an empty [List].
 *
 * @return Nested [List] if this is [Some]. Otherwise returns an empty [List].
 */
fun <T> Option<Iterable<T>>.flatten(): List<T> =
    if (isEmpty) emptyList() else get().toList()

/**
 * Returns [List] of values of each [Some] in this [Iterable].
 *
 * @return [List] of values of each [Some] in this [Iterable].
 */
fun <T> Iterable<Option<T>>.flatten(): List<T> =
    flatMap { it.toList() }

/**
 * Moves inner [Option] outside of the outer [Try].
 *
 * @return [Try] nested in an [Option] for an [Option] nested in a [Try].
 *
 * @since 1.4.0
 */
fun <T> Try<Option<T>>.evert(): Option<Try<T>> = when (this) {
    is Success -> value.map { Success(it) }
    is Failure -> Some(this)
}

/**
 * Moves inner [Try] outside of the outer [Option].
 *
 * @return [Option] nested in a [Try] for a [Try] nested in an [Option].
 *
 * @since 1.4.0
 */
fun <T> Option<Try<T>>.evert(): Try<Option<T>> = when (this) {
    is Some -> value.map { Some(it) }
    is None -> Success(None)
}
