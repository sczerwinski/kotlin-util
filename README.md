[![Build](https://github.com/sczerwinski/kotlin-util/workflows/Build/badge.svg)](https://github.com/sczerwinski/kotlin-util/actions)
![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-blueviolet)  
[![Release](https://github.com/sczerwinski/kotlin-util/workflows/Release/badge.svg)](https://github.com/sczerwinski/kotlin-util/actions)
[![Maven Central](https://img.shields.io/maven-central/v/it.czerwinski/kotlin-util.svg)](https://repo1.maven.org/maven2/it/czerwinski/kotlin-util/)
[![Sonatype Snapshot](https://img.shields.io/nexus/s/https/oss.sonatype.org/it.czerwinski/kotlin-util.svg)](https://oss.sonatype.org/content/repositories/snapshots/it/czerwinski/kotlin-util/)

# ![icon.svg](.idea/icon.svg) Kotlin Utilities

## Project Setup

### Gradle

#### Kotlin

```kotlin
implementation("it.czerwinski:kotlin-util:2.1.0")
```

#### Groovy

```groovy
implementation 'it.czerwinski:kotlin-util:2.1.0'
```

### Maven

```xml
<dependency>
  <groupId>it.czerwinski</groupId>
  <artifactId>kotlin-util</artifactId>
  <version>2.1.0</version>
</dependency>
```

### Kotlin Multiplatform Projects

In multiplatform projects, the library can be used as `commonMain` dependency.

### Java Modules (JPMS)

In projects using Java Modules (JPMS), add the following line
to your `module-info.java`:

```java
module your.module {
    requires it.czerwinski.kotlin.util;
}
```

## Supported Types

Package `it.czerwinski.kotlin.collections`:
* `EmptyIterator`
* `SingletonIterator`

Package `it.czerwinski.kotlin.util`:
* `Option`
* `Either`
* `Try`

For a more detailed documentation, visit
[project website](https://czerwinski.it/projects/kotlin-util/).
