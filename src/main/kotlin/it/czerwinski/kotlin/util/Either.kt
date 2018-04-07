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
}

data class Left<out L>(val value: L) : Either<L, Nothing>() {

    override val isLeft: Boolean
        get() = true

    override val isRight: Boolean
        get() = false
}

data class Right<out R>(val value: R) : Either<Nothing, R>() {

    override val isLeft: Boolean
        get() = false

    override val isRight: Boolean
        get() = true
}
