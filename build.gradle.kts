import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractKotlinNativeTargetPreset
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("multiplatform") version "1.9.10"
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    id("org.jetbrains.dokka") version "1.9.10"
    id("org.jetbrains.changelog") version "2.2.0"
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

val hostOs: String = System.getProperty("os.name")
val isLinux = hostOs == "Linux"
val isWindows = hostOs.startsWith("Windows")
val isMacOs = hostOs == "Mac OS X"

kotlin {
    jvm {
        withJava()
        jvmToolchain(jdkVersion = 11)
    }

    js(IR) {
        browser {
            testTask(
                Action {
                    useKarma {
                        useChromeHeadless()
                        webpackConfig.cssSupport {
                            enabled.set(true)
                        }
                    }
                }
            )
        }
    }

    presets.withType<AbstractKotlinNativeTargetPreset<*>>().forEach {
        targetFromPreset(it)
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

java {
    modularity.inferModulePath.set(true)
}

tasks.named("compileJava", JavaCompile::class.java) {
    options.compilerArgumentProviders.add(CommandLineArgumentProvider {
        listOf("--patch-module", "it.czerwinski.kotlin.util=${sourceSets["main"].output.asPath}")
    })
}

detekt {
    source.setFrom(files(kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories }))
    config.setFrom("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

tasks {

    withType<Detekt>().configureEach {
        reports {
            xml {
                required.set(true)
                outputLocation.set(file("$buildDir/reports/detekt.xml"))
            }
            html {
                required.set(true)
                outputLocation.set(file("$buildDir/reports/detekt.html"))
            }
        }
    }

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
    version.set("${project.version}")
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
    val mavenPublications = publishing.publications.filterIsInstance<MavenPublication>()
    val signTasks = signing.sign(*mavenPublications.toTypedArray())
    tasks.withType(PublishToMavenRepository::class) {
        mustRunAfter(*signTasks.toTypedArray())
    }
}
