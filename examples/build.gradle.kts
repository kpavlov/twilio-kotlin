plugins {
    kotlin("jvm")
    application
    alias(libs.plugins.kover)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    implementation(project(":lib"))
    implementation(libs.kotlinx.serialization.json)

    // Logging
    implementation(libs.slf4j.simple)

    // Testing
    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.bytebuddy)
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs = listOf("-Xms512m", "-Xmx1024m")
}
