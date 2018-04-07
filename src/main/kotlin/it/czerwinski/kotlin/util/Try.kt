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
     *
     * @throws Throwable If this is a [Failure].
     */
    abstract fun get(): T

    /**
     * Gets the value of a [Success] or `null` if this is a [Failure].
     *
     * @return Value of a [Success] or `null`.
     */
    abstract fun getOrNull(): T?

    /**
     * Runs [action] if this is a [Success]. Returns [Unit] without any action if this is a [Failure].
     *
     * @param action Action to be run on a value of a [Success].
     */
    abstract fun forEach(action: (T) -> Unit)

    /**
     * Maps value of a [Success] using [transform] or returns the same [Try] if this is a [Failure].
     *
     * @param transform Function transforming value of a [Success].
     *
     * @return [Try] with a value mapped using [transform] or this object if this is a [Failure].
     */
    abstract fun <R> map(transform: (T) -> R): Try<R>

    /**
     * Maps value of a [Success] to a new [Try] using [transform] or returns the same [Try] if this is a [Failure].
     *
     * @param transform Function transforming value of a [Success] to a [Try].
     *
     * @return [Try] returned by [transform] or this object if this is a [Failure].
     */
    abstract fun <R> flatMap(transform: (T) -> Try<R>): Try<R>

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

/**
 * Gets the value of a [Success] or [default] value if this is a [Failure].
 *
 * @param default Default value provider.
 *
 * @return Value of a [Success] or [default] value.
 */
fun <T> Try<T>.getOrElse(default: () -> T): T =
        if (isSuccess) get() else default()

/**
 * Returns this [Try] if this is a [Success] or [default] if this is a [Failure].
 *
 * @param default Default [Try] provider.
 *
 * @return This [Success] or [default].
 */
fun <T> Try<T>.orElse(default: () -> Try<T>): Try<T> =
        if (isSuccess) this else default()

/**
 * Transforms a nested [Try] to a not nested [Try].
 *
 * @return [Try] nested in a [Success] or this object if this is a [Failure].
 */
fun <T> Try<Try<T>>.flatten(): Try<T> = when (this) {
    is Success -> value
    is Failure -> this
}

data class Success<out T>(val value: T) : Try<T>() {

    override val isSuccess: Boolean = true
    override val isFailure: Boolean = false

    override fun get(): T = value
    override fun getOrNull(): T? = value

    override fun forEach(action: (T) -> Unit) = action(value)
    override fun <R> map(transform: (T) -> R): Try<R> = Try { transform(value) }
    override fun <R> flatMap(transform: (T) -> Try<R>): Try<R> =
            try {
                transform(value)
            } catch (exception: Throwable) {
                Failure(exception)
            }
}

data class Failure(val exception: Throwable) : Try<Nothing>() {

    override val isSuccess: Boolean = false
    override val isFailure: Boolean = true

    override fun get(): Nothing = throw exception
    override fun getOrNull(): Nothing? = null

    override fun forEach(action: (Nothing) -> Unit) = Unit
    override fun <R> map(transform: (Nothing) -> R): Try<R> = this
    override fun <R> flatMap(transform: (Nothing) -> Try<R>): Try<R> = this
}
