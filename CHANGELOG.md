<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Kotlin Utilities Changelog

## Unreleased

### Added
- Java Modules (JPMS) support

### Changed
- Minimum Java version set to 11
- Kotlin 1.9.10
- Gradle Wrapper 8.4
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.23.1`
  - Upgrade `org.jetbrains.dokka` to `1.9.10`
  - Upgrade `org.jetbrains.changelog` to `2.2.0`

### Deprecated

### Removed

### Fixed

### Security

## 1.9.0 - 2023-07-21

### Changed
- Make `None` a data object
- Build and publish artifacts for all platforms from macOS 13
- Kotlin 1.9.0
- Gradle Wrapper 8.2.1
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.23.0`
  - Upgrade `org.jetbrains.dokka` to `1.8.20`
  - Upgrade `org.jetbrains.changelog` to `2.1.2`

### Removed
- Remove support for deprecated legacy JS compiler. Using IR compiler only.

## 1.8.0 - 2023-01-29

### Changed
- Kotlin 1.8.0
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.22.0`
  - Upgrade `org.jetbrains.dokka` to `1.7.20`

## 1.7.1

### Changed
- Make all higher order functions inline
- Kotlin 1.7.20
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.21.0`
  - Upgrade `org.jetbrains.dokka` to `1.7.10`

## 1.7.0

### Added
- Compatibility with [Kotlin/JS IR compiler](https://kotlinlang.org/docs/js-ir-compiler.html)

### Changed
- Method `filterOrElse` returns not null value for `Either`, `LeftProjection` and `RightProjection`
- Kotlin 1.7.0
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.20.0`
  - Upgrade `org.jetbrains.dokka` to `1.7.0`

## 1.6.0

### Changed
- Kotlin 1.6.0
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.18.1`
  - Upgrade `org.jetbrains.dokka` to `1.5.31`
  - Upgrade `org.jetbrains.changelog` to `1.3.1`

## 1.5.30

### Changed
- Kotlin 1.5.30
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.18.0`
  - Upgrade `org.jetbrains.dokka` to `1.5.0`
  - Upgrade `org.jetbrains.changelog` to `1.3.0`

## 1.5.20

### Changed
- Kotlin 1.5.20
- Gradle Wrapper 7.1

## 1.5.10

### Changed
- Kotlin 1.5.10
- Gradle Wrapper 7.0.2
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.17.1`

## 1.5.0

### Changed
- Kotlin 1.5.0
- Dependencies:
  - Upgrade `org.jetbrains.dokka` to `1.4.32`

## 1.4.32

### Changed
- Kotlin 1.4.32
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.16.0`
  - Upgrade `org.jetbrains.dokka` to `1.4.30`

## 1.4.31

### Changed
- Kotlin 1.4.31
- Gradle Wrapper 6.8.3

## 1.4.30

### Added
- `NonFatal` exception matcher
- Dependabot integration

### Changed
- Kotlin 1.4.30
- **BREAKING CHANGE:** `Try` only catches non-fatal exceptions
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.15.0`
  - Upgrade `org.jetbrains.dokka` to `1.4.20`
  - Upgrade `kotlin-multiplatform` to `1.4.21-2`

## 1.4.21

### Changed
- Kotlin 1.4.21
- Gradle Wrapper 6.7.1

## 1.4.20

### Added
- Publish artifacts for all native targets

### Changed
- Kotlin 1.4.20

## 1.4.10

### Changed
- Kotlin 1.4.10

## 1.4.0

### Added
- Multiplatform Kotlin project
- New `evert()` methods for:
  - `Try<Option<T>>` 
  - `Option<Try<T>>`

### Changed
- Kotlin 1.4.0
- Multiplatform Kotlin tests
- JVM tests using JUnit 5

### Removed
- Properties in `Option` deprecated since 1.2 (due to name clashes in JavaScript):
  - `iterator`

## 1.3.0

### Added
- New methods in `Option`:
  - `unzip()` – for `Option<Pair>` and `Option<Triple>`
- New methods in `Either`:
  - all methods previously defined in `RightProjection`

### Changed
- Kotlin 1.3.41

### Deprecated
- Deprecated properties in `Either`:
  - `right`
- Deprecated `RightProjection`

## 1.2.0

### Added
- New methods in `Option`:
  - `iterator()`
- New methods in `LeftProjection` and `RightProjection`:
  - `filterOrElse()`
- New methods in `Try`:
  - `filterOrElse()`

### Changed
- Kotlin 1.3.21

### Deprecated
- Deprecated properties in `Option`:
  - `iterator`

## 1.1.0

### Added
- New methods in `Option`:
  - `none()`
  - `zip()`
  - `asIterable()`
  - `asSequence()`
- New methods in `LeftProjection` and `RightProjection`:
  - `none()`
- New methods in `Try`:
  - `zip()`

### Changed
- Kotlin 1.2.71

## 1.0.0

### Added
- New methods in `Option`:
  - `filterIsInstance()`
- New methods in `LeftProjection` and `RightProjection`:
  - `filterIsInstance()`
  - `filterToOption()`
  - `filterNotToOption()`
  - `filterNotNullToOption()`
- New methods in `Try`:
  - `filterIsInstance()`
- New `flatten()` methods for:
  - `Try<Option<T>>` 
  - `Option<Try<T>>`
  - `Iterable<Option<T>>`
  - `Option<Iterable<T>>`

## 0.2.0

### Added
- Supported type: `EmptyIterator`
- Supported type: `SingletonIterator`
- Supported type: `Option`
- New method: `Either::contains()`
- New methods in `LeftProjection` and `RightProjection`:
  - `filterNot()` 
  - `filterNotNull()` 
  - `toOption()`
- New methods in `Try`:
  - `filterNotNull()` 
  - `fold()`
  - `toOption()`

## 0.1.0

### Added
- Supported type: `Either`
- Supported type: `Try`
