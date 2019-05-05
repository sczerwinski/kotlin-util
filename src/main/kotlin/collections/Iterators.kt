/*
 *
 * This Kotlin code is based on Scala, licensed under the BSD 3-Clause License.
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

package it.czerwinski.kotlin.collections

/**
 * Iterator producing no values.
 */
object EmptyIterator : Iterator<Nothing> {

    override fun hasNext(): Boolean = false
    override fun next(): Nothing = throw NoSuchElementException("Empty iterator")
}

/**
 * Iterator producing a single [element].
 *
 * @param element The element to be produced by the iterator.
 *
 * @param T Type of the [element].
 */
class SingletonIterator<T>(private val element: T) : Iterator<T> {

    private var hasNext = true

    override fun hasNext(): Boolean = hasNext
    override fun next(): T =
        if (hasNext) {
            hasNext = false
            element
        } else {
            throw NoSuchElementException("Only one element available")
        }
}
