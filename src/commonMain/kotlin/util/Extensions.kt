/*
 *
 * This Kotlin code is based on Scala, licensed under the Apache License 2.0.
 *
 * ===== SCALA LICENSE =====
 *
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
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
