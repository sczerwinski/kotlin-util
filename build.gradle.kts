plugins {
    kotlin("multiplatform") version "1.4.0"
    id("io.gitlab.arturbosch.detekt") version "1.11.2"
    id("org.jetbrains.dokka") version "1.4.0-rc"
    `maven-publish`
    signing
}
group = "it.czerwinski"
version = "1.4.10-SNAPSHOT"

val isWithSigning = hasProperty("signing.keyId")
val isSnapshot = version.toString().endsWith("SNAPSHOT")

tasks {

    dokkaJavadoc {
        outputDirectory = "$buildDir/javadoc"
        dokkaSourceSets {
            val commonMain by creating {
                displayName = "Kotlin utilities"
                includes = listOf("packages.md")
            }
        }
    }

    dokkaJekyll {
        outputDirectory = "$buildDir/jekyll"
        dokkaSourceSets {
            val commonMain by creating {
                displayName = "Kotlin utilities"
                includes = listOf("packages.md")
            }
        }
    }

    val javadocJar = create<Jar>("javadocJar") {
        dependsOn.add(dokkaJavadoc)
        archiveClassifier.set("javadoc")
        from(dokkaJavadoc)
    }

    artifacts {
        archives(javadocJar)
    }
}

repositories {
    mavenCentral()
    jcenter()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
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
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
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
        val nativeMain by getting
        val nativeTest by getting
    }

    configure(targets) {
        mavenPublication {
            artifact(tasks["javadocJar"])
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
                    }
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
                    project.hasProperty("ossrhUsername") -> {
                        credentials {
                            val ossrhUsername: String? by project
                            val ossrhPassword: String? by project
                            username = ossrhUsername
                            password = ossrhPassword
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
