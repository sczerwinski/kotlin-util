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
