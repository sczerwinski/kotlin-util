[![Build Status](https://travis-ci.org/sczerwinski/kotlin-util.svg?branch=develop)](https://travis-ci.org/sczerwinski/kotlin-util)
[ ![Download](https://api.bintray.com/packages/sczerwinski/kotlin/kotlin-util/images/download.svg) ](https://bintray.com/sczerwinski/kotlin/kotlin-util/_latestVersion)

# Kotlin Utilities

## Supported types based on Scala

### `Either`

[![Scala documentation](https://img.shields.io/badge/scala-docs-blue.svg)](http://www.scala-lang.org/api/2.12.0/scala/util/Either.html)
[![Scala sources](https://img.shields.io/badge/scala-sources-blue.svg)](https://github.com/scala/scala/blob/v2.12.0/src/library/scala/util/Either.scala)

Implementation differences:

* `toOption` has been replaced with `getOrNull` – Kotlin uses its own null-safety mechanisms
* `exists` has been replaced with `any` – Kotlin convention
* `forall` has been replaced with `all` – Kotlin convention
* `foreach` has been replaced with `forEach` – Kotlin convention
* implemented additional functions: `filterNot`, `filterNotNull` – Kotlin convention

### `Try`

[![Scala documentation](https://img.shields.io/badge/scala-docs-blue.svg)](http://www.scala-lang.org/api/2.12.0/scala/util/Try.html)
[![Scala sources](https://img.shields.io/badge/scala-sources-blue.svg)](https://github.com/scala/scala/blob/v2.12.0/src/library/scala/util/Try.scala)

Implementation differences:

* `toOption` has been replaced with `getOrNull` – Kotlin uses its own null-safety mechanisms
* `foreach` has been replaced with `forEach` – Kotlin convention
* implemented additional functions: `filterNot`, `filterNotNull` – Kotlin convention

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
