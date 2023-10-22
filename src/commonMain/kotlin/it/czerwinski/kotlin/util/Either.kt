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
 * Disjoint union. The instance might be either [Left] or [Right].
 *
 * This type is based on `scala.Either`.
 *
 * @param L Type of [Left]
 * @param R Type of [Right]
 */
sealed class Either<out L, out R> {

    /**
     * Returns `true` if this is a [Left] or `false` if this is [Right].
     *
     * @return `true` if this is a [Left] or `false` if this is [Right].
     */
    abstract val isLeft: Boolean

    /**
     * Returns `true` if this is a [Right] or `false` if this is [Left].
     *
     * @return `true` if this is a [Right] or `false` if this is [Left].
     */
    abstract val isRight: Boolean

    /**
     * Projects [Either] as [Left].
     */
    val left: LeftProjection<L, R>
        inline get() = LeftProjection(this)

    /**
     * Projects [Either] as [Right].
     *
     * Deprecated in 1.3. [Either] is right-biased.
     * All methods from [RightProjection] should be called directly on [Either].
     */
    @Suppress("DeprecatedCallableAddReplaceWith", "DEPRECATION")
    @Deprecated(
        message = "Either is right-biased. All methods from `RightProjection` should be called directly on `Either`.",
        level = DeprecationLevel.WARNING
    )
    val right: RightProjection<L, R>
        inline get() = RightProjection(this)

    /**
     * Gets value of this [Right].
     *
     * @return Value of this [Right].
     *
     * @throws NoSuchElementException If this is [Left].
     *
     * @since 1.3
     */
    fun get(): R = when (this) {
        is Left -> throw NoSuchElementException("Getting Right value from Left")
        is Right -> value
    }

    /**
     * Gets value of this [Right] or `null` if this is [Left].
     *
     * @return Value of this [Right] or `null`.
     *
     * @since 1.3
     */
    fun getOrNull(): R? = when (this) {
        is Left -> null
        is Right -> value
    }

    /**
     * Runs [action] if this is a [Right]. Returns [Unit] without any action if this is a [Left].
     *
     * @param action Action to be run on a [Right].
     *
     * @since 1.3
     */
    inline fun forEach(action: (R) -> Unit) = when (this) {
        is Left -> Unit
        is Right -> action(value)
    }

    /**
     * Maps value of this [Right] using [transform].
     *
     * @param transform Function transforming a [Right].
     *
     * @return [Right] mapped using [transform] or this object if this is a [Left].
     *
     * @since 1.3
     */
    inline fun <T> map(transform: (R) -> T): Either<L, T> = when (this) {
        is Left -> this
        is Right -> Right(transform(value))
    }

    /**
     * Returns the result of applying the [predicate] to the value if this is [Right]
     * or `true` if this is [Left].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Right]
     * or `true` if this is [Left].
     *
     * @since 1.3
     */
    inline fun all(predicate: (R) -> Boolean): Boolean = when (this) {
        is Left -> true
        is Right -> predicate(value)
    }

    /**
     * Returns the result of applying the [predicate] to the value if this is [Right]
     * or `false` if this is [Left].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Right]
     * or `false` if this is [Left].
     *
     * @since 1.3
     */
    inline fun any(predicate: (R) -> Boolean): Boolean = when (this) {
        is Left -> false
        is Right -> predicate(value)
    }

    /**
     * Returns `false` if the [predicate] is met by the value if this is [Right] or `true` otherwise.
     *
     * @param predicate Predicate function.
     *
     * @return `false` if the [predicate] is met by the value if this is [Right] or `true` otherwise.
     *
     * @since 1.3
     */
    inline fun none(predicate: (R) -> Boolean): Boolean = when (this) {
        is Left -> true
        is Right -> !predicate(value)
    }

    /**
     * Returns the same [Right] if the [predicate] is satisfied for the value. Otherwise returns `null`.
     *
     * @param predicate Predicate function.
     *
     * @return The same [Right] if the [predicate] is satisfied for the value. Otherwise returns `null`.
     *
     * @since 1.3
     */
    inline fun filter(predicate: (R) -> Boolean): Either<L, R>? = when (this) {
        is Left -> null
        is Right -> takeIf { predicate(value) }
    }

    /**
     * Returns the same [Right] if the [predicate] is not satisfied for the value. Otherwise returns `null`.
     *
     * @param predicate Predicate function.
     *
     * @return The same [Right] if the [predicate] is not satisfied for the value. Otherwise returns `null`.
     *
     * @since 1.3
     */
    inline fun filterNot(predicate: (R) -> Boolean): Either<L, R>? = when (this) {
        is Left -> null
        is Right -> takeUnless { predicate(value) }
    }

    /**
     * Returns the same [Right] casted to type [T] if it is [T]. Otherwise returns `null`.
     *
     * @param T Required type of the optional value.
     *
     * @return The same [Right] casted to type [T] if it is [T]. Otherwise returns `null`.
     *
     * @since 1.3
     */
    inline fun <reified T> filterIsInstance(): Either<L, T>? = when (this) {
        is Left -> null
        is Right -> (value as? T)?.let { Right(it) }
    }

    /**
     * Returns [Some] containing the same [Right] if the [predicate] is satisfied for the value.
     * Otherwise returns [None].
     *
     * @param predicate Predicate function.
     *
     * @return [Some] containing the same [Right] if the [predicate] is satisfied for the value.
     * Otherwise returns [None].
     *
     * @since 1.3
     */
    inline fun filterToOption(predicate: (R) -> Boolean): Option<Either<L, R>> = when (this) {
        is Left -> None
        is Right -> if (predicate(value)) Some(this) else None
    }

    /**
     * Returns [Some] containing the same [Right] if the [predicate] is not satisfied for the value.
     * Otherwise returns [None].
     *
     * @param predicate Predicate function.
     *
     * @return [Some] containing the same [Right] if the [predicate] is not satisfied for the value.
     * Otherwise returns [None].
     *
     * @since 1.3
     */
    inline fun filterNotToOption(predicate: (R) -> Boolean): Option<Either<L, R>> = when (this) {
        is Left -> None
        is Right -> if (predicate(value)) None else Some(this)
    }

    /**
     * Returns [Some] containing the same [Right] casted to type [T] if it is [T]. Otherwise returns [None].
     *
     * @param T Required type of the optional value.
     *
     * @return [Some] containing the same [Right] casted to type [T] if it is [T]. Otherwise returns [None].
     *
     * @since 1.3
     */
    inline fun <reified T> filterIsInstanceToOption(): Option<Either<L, T>> = when (this) {
        is Left -> None
        is Right -> (value as? T)?.let { Right(it) }.asOption()
    }

    /**
     * Returns a [Some] containing the [Right] value if it exists, or a [None] if this is a [Left].
     *
     * @return a [Some] containing the [Right] value if it exists, or a [None] if this is a [Left].
     *
     * @since 1.3
     */
    fun toOption(): Option<R> = when (this) {
        is Left -> None
        is Right -> Option(value)
    }

    /**
     * Transforms [Left] with [leftTransform] or [Right] with [rightTransform].
     *
     * @param leftTransform Function transforming [Left] value.
     * @param rightTransform Function transforming [Right] value.
     *
     * @return Result of applying [leftTransform] on [Left] or [rightTransform] on [Right].
     */
    inline fun <T> fold(leftTransform: (L) -> T, rightTransform: (R) -> T): T =
        when (this) {
            is Left -> leftTransform(value)
            is Right -> rightTransform(value)
        }

    /**
     * Swaps [Left] to [Right] and [Right] to [Left].
     *
     * @return [Left] if this is [Right] or [Right] if this is [Left].
     */
    abstract fun swap(): Either<R, L>

    /**
     * Returns `true` if the [element] is equal to the value of this [Either], or `false` otherwise.
     *
     * @param element An element of any type.
     *
     * @return `true` if the [element] is equal to the value of this [Either], or `false` otherwise.
     */
    abstract operator fun contains(element: Any): Boolean
}

/**
 * Gets value of this [Right] or [default] value if this is [Left].
 *
 * @param default Default value provider.
 *
 * @return Value of this [Right] or [default].
 *
 * @since 1.3
 */
inline fun <L, R> Either<L, R>.getOrElse(default: () -> R): R = when (this) {
    is Left -> default()
    is Right -> value
}

/**
 * Maps value of this [Right] to a new [Either] using [transform].
 *
 * @param transform Function transforming a [Right] to an [Either].
 *
 * @return [Either] mapped using [transform] or this object if this is a [Left].
 *
 * @since 1.3
 */
inline fun <L, R, T> Either<L, R>.flatMap(transform: (R) -> Either<L, T>): Either<L, T> = when (this) {
    is Left -> this
    is Right -> transform(value)
}

/**
 * Returns the same [Right] if its value is not `null`. Otherwise returns `null`.
 *
 * @return The same [Right] if its value is not `null`. Otherwise returns `null`.
 *
 * @since 1.3
 */
fun <L, R> Either<L, R?>.filterNotNull(): Either<L, R>? = when (this) {
    is Left -> null
    is Right -> value?.let { Right(it) }
}

/**
 * Returns [Some] containing the same [Right] if its value is not `null`. Otherwise returns [None].
 *
 * @return [Some] containing the same [Right] if its value is not `null`. Otherwise returns [None].
 *
 * @since 1.3
 */
fun <L, R> Either<L, R?>.filterNotNullToOption(): Option<Either<L, R>> = when (this) {
    is Left -> None
    is Right -> value?.let { Right(it) }.asOption()
}

/**
 * Returns the same [Right] if the [predicate] is satisfied for the value,
 * `Left(zero)` if the [predicate] is not satisfied for the value,
 * or the same [Left] if this is [Left].
 *
 * @param predicate Predicate function.
 * @param zero Provider of the value used if the [predicate] is not satisfied.
 *
 * @return The same [Right] if the [predicate] is satisfied for the value,
 * `Left(zero)` if the [predicate] is not satisfied for the value,
 * or the same [Left] if this is [Left].
 *
 * @since 1.3
 */
inline fun <L, R> Either<L, R>.filterOrElse(predicate: (R) -> Boolean, zero: () -> L): Either<L, R> =
    when (this) {
        is Right -> if (predicate(value)) this else Left(zero())
        is Left -> this
    }

/**
 * Merges [Left] and [Right] of the same type to a single value.
 *
 * @return Value of either [Left] or [Right].
 */
fun <T> Either<T, T>.merge(): T = when (this) {
    is Left -> value
    is Right -> value
}

/**
 * Returns this if this is [Right]. Otherwise returns value of [Left].
 *
 * Requires [Left] to be an [Either].
 *
 * @return This if this is [Right]. Otherwise returns value of [Left].
 */
fun <L, R> Either<Either<L, R>, R>.joinLeft(): Either<L, R> = when (this) {
    is Left -> value
    is Right -> this
}

/**
 * Returns this if this is [Left]. Otherwise returns value of [Right].
 *
 * Requires [Right] to be an [Either].
 *
 * @return This if this is [Left]. Otherwise returns value of [Right].
 */
fun <L, R> Either<L, Either<L, R>>.joinRight(): Either<L, R> = when (this) {
    is Left -> this
    is Right -> value
}

data class Left<out L>(val value: L) : Either<L, Nothing>() {

    override val isLeft: Boolean
        get() = true

    override val isRight: Boolean
        get() = false

    override fun swap(): Either<Nothing, L> = Right(value)

    override fun contains(element: Any): Boolean = value == element
}

data class Right<out R>(val value: R) : Either<Nothing, R>() {

    override val isLeft: Boolean
        get() = false

    override val isRight: Boolean
        get() = true

    override fun swap(): Either<R, Nothing> = Left(value)

    override fun contains(element: Any): Boolean = value == element
}

data class LeftProjection<out L, out R>(val either: Either<L, R>) {

    /**
     * Gets value of this [Left].
     *
     * @return Value of this [Left].
     *
     * @throws NoSuchElementException If this is [Right].
     */
    fun get(): L = when (either) {
        is Left -> either.value
        is Right -> throw NoSuchElementException("Getting Left value from Right")
    }

    /**
     * Gets value of this [Left] or `null` if this is [Right].
     *
     * @return Value of this [Left] or `null`.
     */
    fun getOrNull(): L? = when (either) {
        is Left -> either.value
        is Right -> null
    }

    /**
     * Runs [action] if this is a [Left]. Returns [Unit] without any action if this is a [Right].
     *
     * @param action Action to be run on a [Left].
     */
    inline fun forEach(action: (L) -> Unit) = when (either) {
        is Left -> action(either.value)
        is Right -> Unit
    }

    /**
     * Maps value of this [Left] using [transform].
     *
     * @param transform Function transforming a [Left].
     *
     * @return [Left] mapped using [transform] or this object if this is a [Right].
     */
    inline fun <T> map(transform: (L) -> T): Either<T, R> = when (either) {
        is Left -> Left(transform(either.value))
        is Right -> either
    }

    /**
     * Returns the result of applying the [predicate] to the value if this is [Left]
     * or `true` if this is [Right].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Left]
     * or `true` if this is [Right].
     */
    inline fun all(predicate: (L) -> Boolean): Boolean = when (either) {
        is Left -> predicate(either.value)
        is Right -> true
    }

    /**
     * Returns the result of applying the [predicate] to the value if this is [Left]
     * or `false` if this is [Right].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Left]
     * or `false` if this is [Right].
     */
    inline fun any(predicate: (L) -> Boolean): Boolean = when (either) {
        is Left -> predicate(either.value)
        is Right -> false
    }

    /**
     * Returns `false` if the [predicate] is met by the value if this is [Left] or `true` otherwise.
     *
     * @param predicate Predicate function.
     *
     * @return `false` if the [predicate] is met by the value if this is [Left] or `true` otherwise.
     *
     * @since 1.1
     */
    inline fun none(predicate: (L) -> Boolean): Boolean = when (either) {
        is Left -> !predicate(either.value)
        is Right -> true
    }

    /**
     * Returns the same [Left] if the [predicate] is satisfied for the value. Otherwise returns `null`.
     *
     * @param predicate Predicate function.
     *
     * @return The same [Left] if the [predicate] is satisfied for the value. Otherwise returns `null`.
     */
    inline fun filter(predicate: (L) -> Boolean): Either<L, R>? = when (either) {
        is Left -> either.takeIf { predicate(it.value) }
        is Right -> null
    }

    /**
     * Returns the same [Left] if the [predicate] is not satisfied for the value. Otherwise returns `null`.
     *
     * @param predicate Predicate function.
     *
     * @return The same [Left] if the [predicate] is not satisfied for the value. Otherwise returns `null`.
     */
    inline fun filterNot(predicate: (L) -> Boolean): Either<L, R>? = when (either) {
        is Left -> either.takeUnless { predicate(it.value) }
        is Right -> null
    }

    /**
     * Returns the same [Left] casted to type [T] if it is [T]. Otherwise returns `null`.
     *
     * @param T Required type of the optional value.
     *
     * @return The same [Left] casted to type [T] if it is [T]. Otherwise returns `null`.
     */
    inline fun <reified T> filterIsInstance(): Either<T, R>? = when (either) {
        is Left -> (either.value as? T)?.let { Left(it) }
        is Right -> null
    }

    /**
     * Returns [Some] containing the same [Left] if the [predicate] is satisfied for the value.
     * Otherwise returns [None].
     *
     * @param predicate Predicate function.
     *
     * @return [Some] containing the same [Left] if the [predicate] is satisfied for the value.
     * Otherwise returns [None].
     */
    inline fun filterToOption(predicate: (L) -> Boolean): Option<Either<L, R>> = when (either) {
        is Left -> if (predicate(either.value)) Some(either) else None
        is Right -> None
    }

    /**
     * Returns [Some] containing the same [Left] if the [predicate] is not satisfied for the value.
     * Otherwise returns [None].
     *
     * @param predicate Predicate function.
     *
     * @return [Some] containing the same [Left] if the [predicate] is not satisfied for the value.
     * Otherwise returns [None].
     */
    inline fun filterNotToOption(predicate: (L) -> Boolean): Option<Either<L, R>> = when (either) {
        is Left -> if (predicate(either.value)) None else Some(either)
        is Right -> None
    }

    /**
     * Returns [Some] containing the same [Left] casted to type [T] if it is [T]. Otherwise returns [None].
     *
     * @param T Required type of the optional value.
     *
     * @return [Some] containing the same [Left] casted to type [T] if it is [T]. Otherwise returns [None].
     */
    inline fun <reified T> filterIsInstanceToOption(): Option<Either<T, R>> = when (either) {
        is Left -> (either.value as? T)?.let { Left(it) }.asOption()
        is Right -> None
    }

    /**
     * Returns a [Some] containing the [Left] value if it exists, or a [None] if this is a [Right].
     *
     * @return a [Some] containing the [Left] value if it exists, or a [None] if this is a [Right].
     */
    fun toOption(): Option<L> = when (either) {
        is Left -> Option(either.value)
        is Right -> None
    }
}

/**
 * Gets value of this [Left] or [default] value if this is [Right].
 *
 * @param default Default value provider.
 *
 * @return Value of this [Left] or [default].
 */
inline fun <L, R> LeftProjection<L, R>.getOrElse(default: () -> L): L = when (either) {
    is Left -> either.value
    is Right -> default()
}

/**
 * Maps value of this [Left] to a new [Either] using [transform].
 *
 * @param transform Function transforming a [Left] to an [Either].
 *
 * @return [Either] mapped using [transform] or this object if this is a [Right].
 */
inline fun <L, R, T> LeftProjection<L, R>.flatMap(transform: (L) -> Either<T, R>): Either<T, R> = when (either) {
    is Left -> transform(either.value)
    is Right -> either
}

/**
 * Returns the same [Left] if its value is not `null`. Otherwise returns `null`.
 *
 * @return The same [Left] if its value is not `null`. Otherwise returns `null`.
 */
fun <L, R> LeftProjection<L?, R>.filterNotNull(): Either<L, R>? = when (either) {
    is Left -> either.value?.let { Left(it) }
    is Right -> null
}

/**
 * Returns [Some] containing the same [Left] if its value is not `null`. Otherwise returns [None].
 *
 * @return [Some] containing the same [Left] if its value is not `null`. Otherwise returns [None].
 */
fun <L, R> LeftProjection<L?, R>.filterNotNullToOption(): Option<Either<L, R>> = when (either) {
    is Left -> either.value?.let { Left(it) }.asOption()
    is Right -> None
}

/**
 * Returns the same [Left] if the [predicate] is satisfied for the value,
 * `Right(zero)` if the [predicate] is not satisfied for the value,
 * or the same [Right] if this is [Right].
 *
 * @param predicate Predicate function.
 * @param zero Provider of the value used if the [predicate] is not satisfied.
 *
 * @return The same [Left] if the [predicate] is satisfied for the value,
 * `Right(zero)` if the [predicate] is not satisfied for the value,
 * or the same [Right] if this is [Right].
 *
 * @since 1.2
 */
inline fun <L, R> LeftProjection<L, R>.filterOrElse(predicate: (L) -> Boolean, zero: () -> R): Either<L, R> =
    when (either) {
        is Left -> if (predicate(either.value)) either else Right(zero())
        is Right -> either
    }

@Deprecated(
    message = "Either is right-biased. All methods from `RightProjection` should be called directly on `Either`.",
    level = DeprecationLevel.WARNING
)
data class RightProjection<out L, out R>(val either: Either<L, R>) {

    /**
     * Gets value of this [Right].
     *
     * @return Value of this [Right].
     *
     * @throws NoSuchElementException If this is [Left].
     */
    fun get(): R = when (either) {
        is Left -> throw NoSuchElementException("Getting Right value from Left")
        is Right -> either.value
    }

    /**
     * Gets value of this [Right] or `null` if this is [Left].
     *
     * @return Value of this [Right] or `null`.
     */
    fun getOrNull(): R? = when (either) {
        is Left -> null
        is Right -> either.value
    }

    /**
     * Runs [action] if this is a [Right]. Returns [Unit] without any action if this is a [Left].
     *
     * @param action Action to be run on a [Right].
     */
    inline fun forEach(action: (R) -> Unit) = when (either) {
        is Left -> Unit
        is Right -> action(either.value)
    }

    /**
     * Maps value of this [Right] using [transform].
     *
     * @param transform Function transforming a [Right].
     *
     * @return [Right] mapped using [transform] or this object if this is a [Left].
     */
    inline fun <T> map(transform: (R) -> T): Either<L, T> = when (either) {
        is Left -> either
        is Right -> Right(transform(either.value))
    }

    /**
     * Returns the result of applying the [predicate] to the value if this is [Right]
     * or `true` if this is [Left].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Right]
     * or `true` if this is [Left].
     */
    inline fun all(predicate: (R) -> Boolean): Boolean = when (either) {
        is Left -> true
        is Right -> predicate(either.value)
    }

    /**
     * Returns the result of applying the [predicate] to the value if this is [Right]
     * or `false` if this is [Left].
     *
     * @param predicate Predicate function.
     *
     * @return The result of applying the [predicate] to the value if this is [Right]
     * or `false` if this is [Left].
     */
    inline fun any(predicate: (R) -> Boolean): Boolean = when (either) {
        is Left -> false
        is Right -> predicate(either.value)
    }

    /**
     * Returns `false` if the [predicate] is met by the value if this is [Right] or `true` otherwise.
     *
     * @param predicate Predicate function.
     *
     * @return `false` if the [predicate] is met by the value if this is [Right] or `true` otherwise.
     *
     * @since 1.1
     */
    inline fun none(predicate: (R) -> Boolean): Boolean = when (either) {
        is Left -> true
        is Right -> !predicate(either.value)
    }

    /**
     * Returns the same [Right] if the [predicate] is satisfied for the value. Otherwise returns `null`.
     *
     * @param predicate Predicate function.
     *
     * @return The same [Right] if the [predicate] is satisfied for the value. Otherwise returns `null`.
     */
    inline fun filter(predicate: (R) -> Boolean): Either<L, R>? = when (either) {
        is Left -> null
        is Right -> either.takeIf { predicate(it.value) }
    }

    /**
     * Returns the same [Right] if the [predicate] is not satisfied for the value. Otherwise returns `null`.
     *
     * @param predicate Predicate function.
     *
     * @return The same [Right] if the [predicate] is not satisfied for the value. Otherwise returns `null`.
     */
    inline fun filterNot(predicate: (R) -> Boolean): Either<L, R>? = when (either) {
        is Left -> null
        is Right -> either.takeUnless { predicate(it.value) }
    }

    /**
     * Returns the same [Right] casted to type [T] if it is [T]. Otherwise returns `null`.
     *
     * @param T Required type of the optional value.
     *
     * @return The same [Right] casted to type [T] if it is [T]. Otherwise returns `null`.
     */
    inline fun <reified T> filterIsInstance(): Either<L, T>? = when (either) {
        is Left -> null
        is Right -> (either.value as? T)?.let { Right(it) }
    }

    /**
     * Returns [Some] containing the same [Right] if the [predicate] is satisfied for the value.
     * Otherwise returns [None].
     *
     * @param predicate Predicate function.
     *
     * @return [Some] containing the same [Right] if the [predicate] is satisfied for the value.
     * Otherwise returns [None].
     */
    inline fun filterToOption(predicate: (R) -> Boolean): Option<Either<L, R>> = when (either) {
        is Left -> None
        is Right -> if (predicate(either.value)) Some(either) else None
    }

    /**
     * Returns [Some] containing the same [Right] if the [predicate] is not satisfied for the value.
     * Otherwise returns [None].
     *
     * @param predicate Predicate function.
     *
     * @return [Some] containing the same [Right] if the [predicate] is not satisfied for the value.
     * Otherwise returns [None].
     */
    inline fun filterNotToOption(predicate: (R) -> Boolean): Option<Either<L, R>> = when (either) {
        is Left -> None
        is Right -> if (predicate(either.value)) None else Some(either)
    }

    /**
     * Returns [Some] containing the same [Right] casted to type [T] if it is [T]. Otherwise returns [None].
     *
     * @param T Required type of the optional value.
     *
     * @return [Some] containing the same [Right] casted to type [T] if it is [T]. Otherwise returns [None].
     */
    inline fun <reified T> filterIsInstanceToOption(): Option<Either<L, T>> = when (either) {
        is Left -> None
        is Right -> (either.value as? T)?.let { Right(it) }.asOption()
    }

    /**
     * Returns a [Some] containing the [Right] value if it exists, or a [None] if this is a [Left].
     *
     * @return a [Some] containing the [Right] value if it exists, or a [None] if this is a [Left].
     */
    fun toOption(): Option<R> = when (either) {
        is Left -> None
        is Right -> Option(either.value)
    }
}

/**
 * Gets value of this [Right] or [default] value if this is [Left].
 *
 * @param default Default value provider.
 *
 * @return Value of this [Right] or [default].
 */
@Suppress("DEPRECATION")
inline fun <L, R> RightProjection<L, R>.getOrElse(default: () -> R): R = when (either) {
    is Left -> default()
    is Right -> either.value
}

/**
 * Maps value of this [Right] to a new [Either] using [transform].
 *
 * @param transform Function transforming a [Right] to an [Either].
 *
 * @return [Either] mapped using [transform] or this object if this is a [Left].
 */
@Suppress("DEPRECATION")
inline fun <L, R, T> RightProjection<L, R>.flatMap(transform: (R) -> Either<L, T>): Either<L, T> = when (either) {
    is Left -> either
    is Right -> transform(either.value)
}

/**
 * Returns the same [Right] if its value is not `null`. Otherwise returns `null`.
 *
 * @return The same [Right] if its value is not `null`. Otherwise returns `null`.
 */
@Suppress("DEPRECATION")
fun <L, R> RightProjection<L, R?>.filterNotNull(): Either<L, R>? = when (either) {
    is Left -> null
    is Right -> either.value?.let { Right(it) }
}

/**
 * Returns [Some] containing the same [Right] if its value is not `null`. Otherwise returns [None].
 *
 * @return [Some] containing the same [Right] if its value is not `null`. Otherwise returns [None].
 */
@Suppress("DEPRECATION")
fun <L, R> RightProjection<L, R?>.filterNotNullToOption(): Option<Either<L, R>> = when (either) {
    is Left -> None
    is Right -> either.value?.let { Right(it) }.asOption()
}

/**
 * Returns the same [Right] if the [predicate] is satisfied for the value,
 * `Left(zero)` if the [predicate] is not satisfied for the value,
 * or the same [Left] if this is [Left].
 *
 * @param predicate Predicate function.
 * @param zero Provider of the value used if the [predicate] is not satisfied.
 *
 * @return The same [Right] if the [predicate] is satisfied for the value,
 * `Left(zero)` if the [predicate] is not satisfied for the value,
 * or the same [Left] if this is [Left].
 *
 * @since 1.2
 */
@Suppress("DEPRECATION")
inline fun <L, R> RightProjection<L, R>.filterOrElse(predicate: (R) -> Boolean, zero: () -> L): Either<L, R> =
    when (either) {
        is Right -> if (predicate(either.value)) either else Left(zero())
        is Left -> either
    }
