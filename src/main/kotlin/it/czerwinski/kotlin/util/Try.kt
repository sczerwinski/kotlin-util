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
     * Returns a [Success] with an exception it this is a [Failure] or a [Failure] if this is a [Success].
     *
     * @return A [Success] with an exception it this is a [Failure] or a [Failure] if this is a [Success].
     */
    abstract val failed: Try<Throwable>

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

    /**
     * Returns the same [Success] if the [predicate] is satisfied for the value. Otherwise returns a [Failure].
     *
     * @param predicate Predicate function.
     *
     * @return The same [Success] if the [predicate] is satisfied for the value. Otherwise returns a [Failure].
     */
    abstract fun filter(predicate: (T) -> Boolean): Try<T>

    /**
     * Returns the same [Success] if the [predicate] is not satisfied for the value. Otherwise returns a [Failure].
     *
     * @param predicate Predicate function.
     *
     * @return The same [Success] if the [predicate] is not satisfied for the value. Otherwise returns a [Failure].
     */
    abstract fun filterNot(predicate: (T) -> Boolean): Try<T>

    /**
     * Transforms a [Success] using [successTransform] or a [Failure] using [failureTransform].
     *
     * @param successTransform Function transforming value of a [Success] to a new [Try].
     * @param failureTransform Function transforming exception from a [Failure] to a new [Try].
     *
     * @return New [Try] being a result of a transformation of a [Success] with [successTransform]
     * or a [Failure] with [failureTransform].
     */
    fun <R> transform(successTransform: (T) -> Try<R>, failureTransform: (Throwable) -> Try<R>): Try<R> =
            try {
                when (this) {
                    is Success -> successTransform(value)
                    is Failure -> failureTransform(exception)
                }
            } catch (exception: Throwable) {
                Failure(exception)
            }

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

/**
 * Returns this [Try] if this is a [Success] or a [Try] created for the [rescue] operation if this is a [Failure].
 *
 * @param rescue Function creating a new value from the exception of a [Failure].
 *
 * @return This [Try] if this is a [Success] or a [Try] created for the [rescue] operation if this is a [Failure].
 */
fun <T> Try<T>.recover(rescue: (Throwable) -> T): Try<T> = when (this) {
    is Success -> this
    is Failure -> Try { rescue(exception) }
}

/**
 * Returns this [Try] if this is a [Success] or a [Try] created by the [rescue] function if this is a [Failure].
 *
 * @param rescue Function creating a new [Try] from the exception of a [Failure].
 *
 * @return This [Try] if this is a [Success] or a [Try] created by the [rescue] function if this is a [Failure].
 */
fun <T> Try<T>.recoverWith(rescue: (Throwable) -> Try<T>): Try<T> = when (this) {
    is Success -> this
    is Failure -> try {
        rescue(exception)
    } catch (exception: Throwable) {
        Failure(exception)
    }
}

data class Success<out T>(val value: T) : Try<T>() {

    override val isSuccess: Boolean
        get() = true

    override val isFailure: Boolean
        get() = false

    override val failed: Try<Throwable>
        get() = Failure(UnsupportedOperationException("Unsupported operation: Success::failed"))

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

    override fun filter(predicate: (T) -> Boolean): Try<T> =
            try {
                if (predicate(value)) this
                else throw NoSuchElementException("Predicate not satisfied for $value")
            } catch (exception: Throwable) {
                Failure(exception)
            }
    override fun filterNot(predicate: (T) -> Boolean): Try<T> =
            try {
                if (!predicate(value)) this
                else throw NoSuchElementException("Predicate not satisfied for $value")
            } catch (exception: Throwable) {
                Failure(exception)
            }
}

data class Failure(val exception: Throwable) : Try<Nothing>() {

    override val isSuccess: Boolean
        get() = false

    override val isFailure: Boolean
        get() = true

    override val failed: Try<Throwable>
        get() = Success(exception)

    override fun get(): Nothing = throw exception
    override fun getOrNull(): Nothing? = null

    override fun forEach(action: (Nothing) -> Unit) = Unit
    override fun <R> map(transform: (Nothing) -> R): Try<R> = this
    override fun <R> flatMap(transform: (Nothing) -> Try<R>): Try<R> = this

    override fun filter(predicate: (Nothing) -> Boolean): Try<Nothing> = this
    override fun filterNot(predicate: (Nothing) -> Boolean): Try<Nothing> = this
}
