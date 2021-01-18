/*
 *
 * This Kotlin code is based on Scala, licensed under the Apache License 2.0.
 *
 * ===== SCALA LICENSE =====
 *
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 */

package it.czerwinski.kotlin.util.control

/**
 * Matcher for non-fatal Throwables. Will not match fatal errors like `VirtualMachineError`
 * (for example, `OutOfMemoryError`, subclass of `VirtualMachineError`), `ThreadDeath`,
 * `LinkageError`, `InterruptedException`.
 *
 * _Note: [AssertionError] is not matched by `NonFatal`, as it's intended to be thrown iff
 * the assumptions about the code are false._
 *
 * _Note: [NotImplementedError] is not matched by `NonFatal`, as it's intended to indicate
 * a method that remains to be implemented._
 *
 * @since 1.4.30
 */
actual object NonFatal {

    /**
     * Returns `true` if the provided `Throwable` is to be considered non-fatal,
     * or `false` if it is to be considered fatal.
     */
    actual operator fun invoke(exception: Throwable): Boolean = when (exception) {
        is StackOverflowError -> true
        is VirtualMachineError,
        is ThreadDeath,
        is InterruptedException,
        is LinkageError,
        is NotImplementedError,
        is AssertionError -> false
        else -> true
    }
}
