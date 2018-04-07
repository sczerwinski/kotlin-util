package it.czerwinski.kotlin.util

/**
 * Representation of an operation that might successfully return a value or throw an exception.
 *
 * An instance of [Try] may be either a [Success] or a [Failure].
 *
 * This type is based on `scala.util.Try`.
 *
 * @param T Type of the value of a successful operation.
 */
sealed class Try<out T> {

    /**
     * Returns `true` if this is a [Success] or `false` if this is [Failure].
     *
     * @return `true` if this is a [Success] or `false` if this is [Failure].
     */
    abstract val isSuccess: Boolean

    /**
     * Returns `true` if this is a [Failure] or `false` if this is [Success].
     *
     * @return `true` if this is a [Failure] or `false` if this is [Success].
     */
    abstract val isFailure: Boolean

    /**
     * Gets the value of a [Success] or throw an exception from a [Failure].
     *
     * @return Value of a [Success].
     */
    abstract fun get(): T

    companion object {
        /**
         * Creates a new [Try] based on the result of the [callable].
         *
         * @param callable A callable operation.
         *
         * @return An instance of [Success] or [Failure], depending on whether the operation.
         */
        operator fun <T> invoke(callable: () -> T): Try<T> =
                try {
                    Success(callable())
                } catch (exception: Throwable) {
                    Failure(exception)
                }
    }
}

data class Success<out T>(val value: T) : Try<T>() {

    override val isSuccess: Boolean = true
    override val isFailure: Boolean = false

    override fun get(): T = value
}

data class Failure(val exception: Throwable) : Try<Nothing>() {

    override val isSuccess: Boolean = false
    override val isFailure: Boolean = true

    override fun get(): Nothing = throw exception
}
