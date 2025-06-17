plugins {
    kotlin("plugin.serialization") version "2.1.21"
    `kotlin-convention`
    `dokka-convention`
    `publish-convention`
    alias(libs.plugins.kover)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.assertions.core)
                implementation(libs.mockk)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        jvmMain {
            dependencies {
                // JVM-specific dependencies
                runtimeOnly(libs.slf4j.simple)
                runtimeOnly(libs.bytebuddy)
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.kotest.runner.junit5)
            }
        }
    }
}
