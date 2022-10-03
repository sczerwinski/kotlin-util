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

package it.czerwinski.kotlin.util.control

/**
 * Matcher for non-fatal Throwables. Will not match fatal errors like `OutOfMemoryError`.
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
        is OutOfMemoryError,
        is NotImplementedError,
        is AssertionError -> false
        else -> true
    }
}
