/*
 *
 * This Kotlin code is based on Scala, licensed under the BSD 3-Clause License.
 *
 * ===== SCALA LICENSE =====
 *
 * Copyright (c) 2002-2018 EPFL
 * Copyright (c) 2011-2018 Lightbend, Inc.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the EPFL nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package it.czerwinski.kotlin.util

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
        get() = !isEmpty

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
     * Maps value of a [Some] using [transform] or returns the same [None].
     *
     * @param transform Function transforming value of a [Some].
     *
     * @return [Some] with a value mapped using [transform] or this object if this is a [None].
     */
    fun <R> map(transform: (T) -> R): Option<R> =
            if (isEmpty) None else Some(transform(get()))

    /**
     * Maps value of a [Some] to a new [Option] using [transform] or returns the same [None].
     *
     * @param transform Function transforming value of a [Some] to an [Option].
     *
     * @return [Option] returned by [transform] or this object if this is a [None].
     */
    fun <R> flatMap(transform: (T) -> Option<R>): Option<R> =
            if (isEmpty) None else transform(get())

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
fun <T> Option<T>.getOrElse(default: () -> T): T =
        if (isEmpty) default() else get()

/**
 * Returns this [Option] if this is a [Some] or [default] if this is [None].
 *
 * @param default Default [Option] provider.
 *
 * @return This [Some] or [default].
 */
fun <T> Option<T>.orElse(default: () -> Option<T>): Option<T> =
        if (isEmpty) default() else this

/**
 * Transforms a nested [Option] to a not nested [Option].
 *
 * @return [Option] nested in a [Some] or [None] if this option is empty.
 */
fun <T> Option<Option<T>>.flatten(): Option<T> =
        if (isEmpty) None else get()

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
}

/**
 * Representation of a non-existent value.
 */
object None : Option<Nothing>() {

    override val isEmpty: Boolean = true

    override fun get(): Nothing = throw NoSuchElementException("Getting value of None")
    override fun getOrNull(): Nothing? = null
}
