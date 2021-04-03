<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Kotlin Utilities Changelog

## [Unreleased]

## [v1.4.32]
### Changed
- Kotlin 1.4.32
- Dependencies:
  - Upgrade `io.gitlab.arturbosch.detekt` to `1.16.0`
  - Upgrade `org.jetbrains.dokka` to `1.4.30`

## [v1.4.31]
### Changed
- Kotlin 1.4.31
- Gradle Wrapper 6.8.3

## [v1.4.30]
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

## [v1.4.21]
### Changed
- Kotlin 1.4.21
- Gradle Wrapper 6.7.1

## [v1.4.20]
### Added
- Publish artifacts for all native targets

### Changed
- Kotlin 1.4.20

## [v1.4.10]
### Changed
- Kotlin 1.4.10

## [v1.4.0]
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

## [1.3]
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

## [1.2]
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

## [1.1]
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

## [1.0]
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

### Fixed
* Correct implementation of `None::toString()`

## [0.2]
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

## [0.1]
### Added
- Supported type: `Either`
- Supported type: `Try`
