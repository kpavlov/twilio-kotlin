plugins {
    base
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
    alias(libs.plugins.nexusPublish) // https://github.com/gradle-nexus/publish-plugin
    `dokka-convention`
    signing
}

allprojects {
    repositories {
        mavenCentral()
    }
}

// Common configuration for subprojects
subprojects {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.jetbrains.dokka-javadoc")
    apply(plugin = "com.diffplug.spotless")
}

dependencies {
    kover(project(":lib"))
    kover(project(":examples"))
}

kover {
    reports {
        total {
            xml
            html
        }

        verify {
            rule {
                bound {
                    minValue = 1
                }
            }
        }
    }
}

