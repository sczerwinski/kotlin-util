# Kotlin Utilities Release Notes

## Version 1.2
🔖 `kotlin-util-1.2`

### Dependencies

* Kotlin 1.3.21

### New features

* ✨ New methods in `Option`:
  * `iterator()`
* ✨ New methods in `LeftProjection` and `RightProjection`:
  * `filterOrElse()`
* ✨ New methods in `Try`:
  * `filterOrElse()`

### Deprecations

* 🚫 Deprecated properties in `Option`:
  * `iterator`

## Version 1.1
🔖 `kotlin-util-1.1`

### Dependencies

* Kotlin 1.2.71

### New features

* ✨ New methods in `Option`:
  * `none()`
  * `zip()`
  * `asIterable()`
  * `asSequence()`
* ✨ New methods in `LeftProjection` and `RightProjection`:
  * `none()`
* ✨ New methods in `Try`:
  * `zip()`

## Version 1.0
🔖 `kotlin-util-1.0`

### New features

* ✨ New methods in `Option`:
  * `filterIsInstance()`
* ✨ New methods in `LeftProjection` and `RightProjection`:
  * `filterIsInstance()`
  * `filterToOption()`
  * `filterNotToOption()`
  * `filterNotNullToOption()`
* ✨ New methods in `Try`:
  * `filterIsInstance()`
* ✨ New `flatten()` methods for:
  * `Try<Option<T>>` 
  * `Option<Try<T>>`
  * `Iterable<Option<T>>`
  * `Option<Iterable<T>>`

### Bug fixes

* 🐛 Correct implementation of `None::toString()`

## Version 0.2

🔖 `kotlin-util-0.2`

### New features

* ✨ Supported type: `EmptyIterator`
* ✨ Supported type: `SingletonIterator`
* ✨ Supported type: `Option`
* ✨ New method: `Either::contains()`
* ✨ New methods in `LeftProjection` and `RightProjection`:
  * `filterNot()` 
  * `filterNotNull()` 
  * `toOption()`
* ✨ New methods in `Try`:
  * `filterNotNull()` 
  * `fold()`
  * `toOption()`

## Version 0.1

🔖 `kotlin-util-0.1`

### New features

* ✨ Supported type: `Either`
* ✨ Supported type: `Try`
