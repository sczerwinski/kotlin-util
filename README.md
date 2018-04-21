[![Build Status](https://travis-ci.org/sczerwinski/kotlin-util.svg?branch=develop)](https://travis-ci.org/sczerwinski/kotlin-util)
[ ![Download](https://api.bintray.com/packages/sczerwinski/kotlin/kotlin-util/images/download.svg) ](https://bintray.com/sczerwinski/kotlin/kotlin-util/_latestVersion)

# Kotlin Utilities

## Supported types based on Scala

### `EmptyIterator`

Iterator based on the result of `scala.collection.Iterator.empty`, producing no values.

Kotlin defines an internal `EmptyIterator`, which can only be obtained indirectly
from empty collections, e.g.:

```kotlin
emptyList<Nothing>().iterator()
```

### `SingletonIterator`

Iterator based on the result of `scala.collection.Iterator.single[A](elem: A)`, producing a single value.

An iterator for a singleton list is defined in Java, in a package-private static method
`java.util.Collections.singletonIterator(E e)`. It can only be obtained indirectly
from a new instance of a singleton list, e.g.:
```kotlin
java.util.Collections.singletonList(element).iterator()
```

### `Option`

[![Scala documentation](https://img.shields.io/badge/scala-docs-blue.svg)](http://www.scala-lang.org/api/2.12.0/scala/Option.html)
[![Scala sources](https://img.shields.io/badge/scala-sources-blue.svg)](https://github.com/scala/scala/blob/v2.12.0/src/library/scala/Option.scala)

Implementation differences:

* `exists` has been replaced with `any` – Kotlin convention
* `forall` has been replaced with `all` – Kotlin convention
* `foreach` has been replaced with `forEach` – Kotlin convention
* `orNull` has been replaced with `getOrNull` – Kotlin convention
* implemented additional function `filterNotNull` – Kotlin convention

Kotlin introduces its own null-safety mechanisms.
Most of the times, `Option`s can be replaced by nullable types in Kotlin, e.g.:

```kotlin
var number: Option<Int> = …

number.map { it.toString() }.getOrElse { "" }
```

gives the same result as:

```kotlin
var number: Int? = …

number?.let { it.toString() }.orEmpty()
```

However, `Option`s might be useful whenever `null` values are not allowed,
e.g. RxJava 2.x `Observable`.

### `Either`

[![Scala documentation](https://img.shields.io/badge/scala-docs-blue.svg)](http://www.scala-lang.org/api/2.12.0/scala/util/Either.html)
[![Scala sources](https://img.shields.io/badge/scala-sources-blue.svg)](https://github.com/scala/scala/blob/v2.12.0/src/library/scala/util/Either.scala)

Implementation differences:

* `exists` has been replaced with `any` – Kotlin convention
* `forall` has been replaced with `all` – Kotlin convention
* `foreach` has been replaced with `forEach` – Kotlin convention
* implemented additional functions: `filterNot`, `filterNotNull` – Kotlin convention
* implemented additional function `getOrNull` in addition to `toOption` – Kotlin uses its own null-safety mechanisms

Unlike in Scala, this implementation is not right-biased,
i.e. it is not possible to use `Either.map {}` instead of `Either.right.map {}`.
However, the Scala convention that “dictates that `Left` is used for failure and `Right` is used for success”
(Scala Standard Library Documentation) is still a preferred way of using this class.

### `Try`

[![Scala documentation](https://img.shields.io/badge/scala-docs-blue.svg)](http://www.scala-lang.org/api/2.12.0/scala/util/Try.html)
[![Scala sources](https://img.shields.io/badge/scala-sources-blue.svg)](https://github.com/scala/scala/blob/v2.12.0/src/library/scala/util/Try.scala)

Implementation differences:

* `foreach` has been replaced with `forEach` – Kotlin convention
* implemented additional functions: `filterNot`, `filterNotNull` – Kotlin convention
* implemented additional function `getOrNull` in addition to `toOption` – Kotlin uses its own null-safety mechanisms

## Build configuration

### Maven

```xml
<dependency>
  <groupId>it.czerwinski</groupId>
  <artifactId>kotlin-util</artifactId>
  <version>0.1</version>
</dependency>
```

### Gradle

```groovy
implementation 'it.czerwinski:kotlin-util:0.1'
```
