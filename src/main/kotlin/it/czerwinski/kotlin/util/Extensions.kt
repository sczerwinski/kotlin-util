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
