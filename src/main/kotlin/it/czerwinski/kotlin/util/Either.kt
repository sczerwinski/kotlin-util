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
     * Transforms [Left] with [leftTransform] or [Right] with [rightTransform].
     *
     * @param leftTransform Function transforming [Left] value.
     * @param rightTransform Function transforming [Right] value.
     *
     * @return Result of applying [leftTransform] on [Left] or [rightTransform] on [Right].
     */
    abstract fun <T> fold(leftTransform: (L) -> T, rightTransform: (R) -> T): T

    /**
     * Swaps [Left] to [Right] and [Right] to [Left].
     *
     * @return [Left] if this is [Right] or [Right] if this is [Left].
     */
    abstract fun swap(): Either<R, L>
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

    override fun <T> fold(leftTransform: (L) -> T, rightTransform: (Nothing) -> T): T = leftTransform(value)

    override fun swap(): Either<Nothing, L> = Right(value)
}

data class Right<out R>(val value: R) : Either<Nothing, R>() {

    override val isLeft: Boolean
        get() = false

    override val isRight: Boolean
        get() = true

    override fun <T> fold(leftTransform: (Nothing) -> T, rightTransform: (R) -> T): T = rightTransform(value)

    override fun swap(): Either<R, Nothing> = Left(value)
}
