import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractKotlinNativeTargetPreset
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("multiplatform") version "1.5.20"
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
    id("org.jetbrains.dokka") version "1.5.0"
    id("org.jetbrains.changelog") version "1.1.2"
    `maven-publish`
    signing
}

val baseVersion: String by project
val versionSuffix: String by project

group = "it.czerwinski"
version = if (versionSuffix.isBlank()) baseVersion else "$baseVersion-$versionSuffix"

val isWithSigning = hasProperty("signing.keyId")
val isSnapshot = versionSuffix == "SNAPSHOT"

repositories {
    mavenCentral()
}

val hostOs = System.getProperty("os.name")
val isLinux = hostOs == "Linux"
val isWindows = hostOs.startsWith("Windows")
val isMacOs = hostOs == "Mac OS X"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    when {

        isLinux -> {
            linuxMips32()
            linuxMipsel32()
        }

        isWindows -> {
            mingwX64()
            mingwX86()
        }

        isMacOs -> {
            js {
                browser {
                    testTask {
                        useKarma {
                            useChromeHeadless()
                            webpackConfig.cssSupport.enabled = true
                        }
                    }
                }
            }

            presets.withType<AbstractKotlinNativeTargetPreset<*>>().forEach {
                targetFromPreset(it)
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
        when {
            isLinux -> {
                val linuxMips32Main by getting
                val linuxMipsel32Main by getting
                val nativeMain by creating {
                    dependsOn(commonMain)
                    linuxMips32Main.dependsOn(this)
                    linuxMipsel32Main.dependsOn(this)
                }
            }
            isWindows -> {
                val mingwX64Main by getting
                val mingwX86Main by getting
                val nativeMain by creating {
                    dependsOn(commonMain)
                    mingwX64Main.dependsOn(this)
                    mingwX86Main.dependsOn(this)
                }
            }
            isMacOs -> {
                val jsMain by getting
                val jsTest by getting {
                    dependencies {
                        implementation(kotlin("test-js"))
                    }
                }
                val nativeSourceSetNames = presets.withType<AbstractKotlinNativeTargetPreset<*>>()
                    .map { preset -> "${preset.name}Main" }
                val nativeMain by creating {
                    dependsOn(commonMain)
                    nativeSourceSetNames.forEach { name ->
                        getByName(name).dependsOn(this)
                    }
                }
            }
        }
    }
}

detekt {
    input = files(kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories })
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt.html")
        }
    }
}

tasks {

    val dokkaJavadocCommon by creating(DokkaTask::class.java) {
        outputDirectory.set(buildDir.resolve("javadoc"))
        dokkaSourceSets {
            named("commonMain") {
                moduleName.set("Kotlin utilities")
                includes.from(files("packages.md"))
            }
        }
    }

    val dokkaJekyllCommon by creating(DokkaTask::class.java) {
        outputDirectory.set(buildDir.resolve("jekyll"))
        dokkaSourceSets {
            named("commonMain") {
                moduleName.set("Kotlin utilities")
                includes.from(files("packages.md"))
            }
        }
    }

    val javadocJar = create<Jar>("javadocJar") {
        dependsOn.add(dokkaJavadocCommon)
        archiveClassifier.set("javadoc")
        from(dokkaJavadocCommon)
    }

    artifacts {
        archives(javadocJar)
    }
}

configure(kotlin.targets) {
    mavenPublication {
        artifact(tasks["javadocJar"])
    }
}

changelog {
    version = "${project.version}"
}

publishing {
    publications.filterIsInstance<MavenPublication>()
        .forEach { publication ->
            publication.pom {
                name.set("Kotlin Utilities")
                description.set("Kotlin utility classes based on Scala")
                url.set("https://czerwinski.it/projects/kotlin-util/")
                scm {
                    connection.set("scm:git:https://github.com/sczerwinski/kotlin-util.git")
                    developerConnection.set("scm:git:https://github.com/sczerwinski/kotlin-util.git")
                    url.set("https://github.com/sczerwinski/kotlin-util")
                }
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("sczerwinski")
                        name.set("Slawomir Czerwinski")
                        email.set("slawomir@czerwinski.it")
                        url.set("https://czerwinski.it/")
                    }
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/sczerwinski/kotlin-util/issues")
                }
                ciManagement {
                    system.set("GitHub Actions")
                    url.set("https://github.com/sczerwinski/kotlin-util/actions")
                }
            }
        }
    publications {
        repositories {
            maven {
                url = uri(
                    if (isSnapshot) "https://oss.sonatype.org/content/repositories/snapshots/"
                    else "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                )
                when {
                    System.getenv("SONATYPE_USERNAME") != null -> {
                        credentials {
                            username = System.getenv("SONATYPE_USERNAME")
                            password = System.getenv("SONATYPE_PASSWORD")
                        }
                    }
                    else -> {
                        url = uri("$buildDir/repo")
                    }
                }
            }
        }
    }
}

if (isWithSigning) {
    signing {
        sign(
            *publishing.publications
                .filterIsInstance<MavenPublication>()
                .toTypedArray()
        )
    }
}
