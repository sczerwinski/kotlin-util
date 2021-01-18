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

import it.czerwinski.kotlin.collections.EmptyIterator
import it.czerwinski.kotlin.collections.SingletonIterator

/**
 * Representation of an optional value. The instance might be either [Some] value or [None].
 *
 * @param T Type of the optional value.
 */
sealed class Option<out T> {

    /**
     * Returns `true` if the option is [None]. Otherwise returns `false`.
     */
    abstract val isEmpty: Boolean

    /**
     * Returns `true` if the option is [Some]. Otherwise returns `false`.
     */
    val isDefined: Boolean
        inline get() = !isEmpty

    /**
     * Returns `true` if the option is [Some]. Otherwise returns `false`.
     */
    val isNotEmpty: Boolean
        inline get() = isDefined

    /**
     * Gets the value of a [Some] or throws an exception.
     *
     * @return Value of a [Some].
     *
     * @throws NoSuchElementException If this is [None].
     */
    abstract fun get(): T

    /**
     * Gets the value of a [Some] or `null` if this is a [None].
     *
     * @return Value of a [Some] or `null`.
     */
    abstract fun getOrNull(): T?

    /**
     * Returns a singleton iterator returning the option's value if it is defined,
     * or an empty iterator if the option is empty.
     *
     * @since 1.2
     */
    abstract fun iterator(): Iterator<T>

    /**
     * Runs [action] if this is a [Some]. Returns [Unit] without any action if this is [None].
     *
     * @param action Action to be run on a value of a [Some].
     */
    inline fun forEach(action: (T) -> Unit) {
        if (isDefined) action(get())
    }

    /**
     * Maps value of a [Some] using [transform] or returns the same [None].
     *
     * @param transform Function transforming value of a [Some].
     *
     * @return [Some] with a value mapped using [transform] or this object if this is a [None].
     */
    inline fun <R> map(transform: (T) -> R): Option<R> =
        if (isEmpty) None else Some(transform(get()))

    /**
     * Maps value of a [Some] to a new [Option] using [transform] or returns the same [None].
     *
     * @param transform Function transforming value of a [Some] to an [Option].
     *
     * @return [Option] returned by [transform] or this object if this is a [None].
     */
    inline fun <R> flatMap(transform: (T) -> Option<R>): Option<R> =
        if (isEmpty) None else transform(get())

    /**
     * Returns the result of applying the [predicate] to the value if this is [Some]
     * or `true` if this is [None].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Some]
     * or `true` if this is [None].
     */
    inline fun all(predicate: (T) -> Boolean): Boolean =
        isEmpty || predicate(get())

    /**
     * Returns the result of applying the [predicate] to the value if this is [Some]
     * or `false` if this is [None].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Some]
     * or `false` if this is [None].
     */
    inline fun any(predicate: (T) -> Boolean): Boolean =
        isDefined && predicate(get())

    /**
     * Returns `false` if the [predicate] is met by the value if this is [Some] or `true` otherwise.
     *
     * @param predicate Predicate function.
     *
     * @return `false` if the [predicate] is met by the value if this is [Some] or `true` otherwise.
     *
     * @since 1.1
     */
    inline fun none(predicate: (T) -> Boolean): Boolean =
        isEmpty || !predicate(get())

    /**
     * Returns the same [Some] if the [predicate] is satisfied for the value. Otherwise returns a [None].
     *
     * @param predicate Predicate function.
     *
     * @return The same [Some] if the [predicate] is satisfied for the value. Otherwise returns a [None].
     */
    inline fun filter(predicate: (T) -> Boolean): Option<T> =
        if (isEmpty || predicate(get())) this else None

    /**
     * Returns the same [Some] if the [predicate] is not satisfied for the value. Otherwise returns a [None].
     *
     * @param predicate Predicate function.
     *
     * @return The same [Some] if the [predicate] is not satisfied for the value. Otherwise returns a [None].
     */
    inline fun filterNot(predicate: (T) -> Boolean): Option<T> =
        if (isEmpty || !predicate(get())) this else None

    /**
     * Returns the same [Some] casted to type [R] if it is [R]. Otherwise returns a [None].
     *
     * @param R Required type of the optional value.
     *
     * @return The same [Some] casted to type [R] if it is [R]. Otherwise returns a [None].
     */
    inline fun <reified R> filterIsInstance(): Option<R> =
        (getOrNull() as? R).asOption()

    /**
     * Returns result of applying [transform] on the value of [Some] or [default] if this is [None].
     *
     * @param default Value to be returned if the option is empty.
     * @param transform Function transforming [Some] value.
     *
     * @return Result of applying [transform] on the value of [Some] or [default] if this is [None].
     */
    inline fun <R> fold(default: R, transform: (T) -> R): R =
        if (isEmpty) default else transform(get())

    /**
     * Returns result of applying [transform] on the value of [Some] or [default] if this is [None].
     *
     * @param default Function producing value to be returned if the option is empty.
     * @param transform Function transforming [Some] value.
     *
     * @return Result of applying [transform] on the value of [Some] or [default] if this is [None].
     */
    inline fun <R> fold(default: () -> R, transform: (T) -> R): R =
        if (isEmpty) default() else transform(get())

    /**
     * Returns [Some] containing a `Pair` of values of this and [other] [Option] if both [Option]s are [Some].
     * Otherwise returns [None].
     *
     * @param other Other [Option].
     *
     * @return [Some] containing a `Pair` of values of this and [other] [Option] if both [Option]s are [Some].
     * Otherwise returns [None].
     *
     * @since 1.1
     */
    infix fun <R> zip(other: Option<R>): Option<Pair<T, R>> =
        if (isDefined && other.isDefined) Some(get() to other.get()) else None

    /**
     * Returns [Some] containing the result of applying [transform] to both values of this and [other] [Option]
     * if both [Option]s are [Some]. Otherwise returns [None].
     *
     * @param other Other [Option].
     * @param transform Function transforming values of both [Some].
     *
     * @return [Some] containing the result of applying [transform] to both values of this and [other] [Option]
     * if both [Option]s are [Some]. Otherwise returns [None].
     *
     * @since 1.1
     */
    inline fun <T1, R> zip(other: Option<T1>, transform: (T, T1) -> R): Option<R> =
        if (isDefined && other.isDefined) Some(transform(get(), other.get())) else None

    /**
     * Returns a singleton list containing the option's value if it is defined,
     * or an empty list if the option is empty.
     *
     * @return A singleton list returning the option's value if it is defined,
     * or an empty iterator if the option is empty.
     */
    abstract fun toList(): List<T>

    /**
     * Returns a [Right] containing the given argument [right] if this is empty,
     * or a [Left] containing this option's value if it is defined.
     *
     * @param right Producer of the fallback [Right] value.
     *
     * @param R The type of the [Right] value.
     *
     * @return a [Right] containing the given argument [right] if this is empty,
     * or a [Left] containing this option's value if it is defined.
     */
    inline fun <R> toLeft(right: () -> R): Either<T, R> =
        if (isEmpty) Right(right()) else Left(get())

    /**
     * Returns a [Left] containing the given argument [left] if this is empty,
     * or a [Right] containing this option's value if it is defined.
     *
     * @param left Producer of the fallback [Left] value.
     *
     * @param L The type of the [Left] value.
     *
     * @return a [Left] containing the given argument [left] if this is empty,
     * or a [Right] containing this option's value if it is defined.
     */
    inline fun <L> toRight(left: () -> L): Either<L, T> =
        if (isEmpty) Left(left()) else Right(get())

    /**
     * Returns an iterable that wraps this [Option] returning its value if it is defined,
     * or an empty iterable if the option is empty.
     *
     * @return An iterable that wraps this [Option] returning its value if it is defined,
     * or an empty iterable if the option is empty.
     *
     * @since 1.1
     */
    abstract fun asIterable(): Iterable<T>

    /**
     * Returns a sequence that wraps this [Option] returning its value if it is defined,
     * or an empty sequence if the option is empty.
     *
     * @return A sequence that wraps this [Option] returning its value if it is defined,
     * or an empty sequence if the option is empty.
     *
     * @since 1.1
     */
    abstract fun asSequence(): Sequence<T>

    companion object {

        /**
         * Returns [Some] if the [value] is not `null` or [None] if the [value] is `null`.
         *
         * @param value Nullable value.
         *
         * @param T Type of the optional value.
         *
         * @return [Some] if the [value] is not `null` or [None] if the [value] is `null`.
         */
        operator fun <T> invoke(value: T?): Option<T> = when (value) {
            null -> None
            else -> Some(value)
        }

        /**
         * Returns [None].
         *
         * @param T Type of the optional value.
         *
         * @return [None].
         */
        fun <T> empty(): Option<T> = None
    }
}

/**
 * Gets the value of a [Some] or [default] value if this is [None].
 *
 * @param default Default value provider.
 *
 * @return Value of a [Some] or [default] value.
 */
inline fun <T> Option<T>.getOrElse(default: () -> T): T =
    if (isEmpty) default() else get()

/**
 * Returns this [Option] if this is a [Some] or [default] if this is [None].
 *
 * @param default Default [Option] provider.
 *
 * @return This [Some] or [default].
 */
inline fun <T> Option<T>.orElse(default: () -> Option<T>): Option<T> =
    if (isEmpty) default() else this

/**
 * Transforms a nested [Option] to a not nested [Option].
 *
 * @return [Option] nested in a [Some] or [None] if this option is empty.
 */
fun <T> Option<Option<T>>.flatten(): Option<T> =
    if (isEmpty) None else get()

/**
 * Transforms an [Option] of a `Pair` into a `Pair` of an [Option] of the first value
 * and an [Option] of the second value.
 *
 * @return A `Pair` of an [Option] of the first value and an [Option] of the second value.
 */
fun <A, B> Option<Pair<A, B>>.unzip(): Pair<Option<A>, Option<B>> =
    if (isEmpty) None to None else with(get()) { Option(first) to Option(second) }

/**
 * Transforms an [Option] of a `Triple` into a `Triple` of an [Option] of the first value,
 * an [Option] of the second value, and an [Option] of the third value.
 *
 * @return A `Triple` of an [Option] of the first value, an [Option] of the second value,
 * and an [Option] of the third value.
 */
fun <A, B, C> Option<Triple<A, B, C>>.unzip(): Triple<Option<A>, Option<B>, Option<C>> =
    if (isEmpty) Triple(None, None, None)
    else with(get()) { Triple(Option(first), Option(second), Option(third)) }

/**
 * Tests whether the [Option] contains the given [element].
 *
 * @param element An element to be tested.
 *
 * @return `true` if the [element] is equal to the value of this [Some], or `false` otherwise.
 */
operator fun <T> Option<T>.contains(element: T): Boolean =
    isDefined && get() == element

/**
 * Returns [Some] if this is not `null` or [None] if this is `null`.
 *
 * @param T Type of the nullable value.
 *
 * @return [Some] if this is not `null` or [None] if this is `null`.
 */
fun <T> T?.asOption(): Option<T> = Option(this)

/**
 * Representation of a value of type [T].
 *
 * @param T Type of the value.
 */
data class Some<T>(val value: T) : Option<T>() {

    init {
        require(value != null) { "Cannot create Some(null)" }
    }

    override val isEmpty: Boolean = false

    override fun get(): T = value
    override fun getOrNull(): T? = value

    override fun iterator(): Iterator<T> = SingletonIterator(value)

    override fun toList(): List<T> = listOf(value)

    override fun asIterable(): Iterable<T> = Iterable { iterator() }
    override fun asSequence(): Sequence<T> = Sequence { iterator() }
}

/**
 * Representation of a non-existent value.
 */
object None : Option<Nothing>() {

    override val isEmpty: Boolean = true

    override fun get(): Nothing = throw NoSuchElementException("Getting value of None")
    override fun getOrNull(): Nothing? = null

    override fun iterator(): Iterator<Nothing> = EmptyIterator

    override fun toList(): List<Nothing> = emptyList()

    override fun asIterable(): Iterable<Nothing> = emptyList()
    override fun asSequence(): Sequence<Nothing> = emptySequence()

    override fun toString(): String = "None"
}
