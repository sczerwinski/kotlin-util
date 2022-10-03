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
