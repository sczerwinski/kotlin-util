<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Kotlin Utilities Changelog

## [Unreleased]

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