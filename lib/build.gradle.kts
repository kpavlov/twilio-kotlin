import java.io.InputStream
import java.net.URI

plugins {
    kotlin("plugin.serialization") version "2.1.21"
    `kotlin-convention`
    `dokka-convention`
    `publish-convention`
    alias(libs.plugins.kover)
    alias(libs.plugins.openapi.generator)
}

kotlin {
    explicitApi()
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.auth)
                implementation(libs.kotlinx.datetime)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.assertions.core)
                implementation(libs.mockk)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.ktor.client.mock)
            }
        }

        jvmMain {
            dependencies {
                // JVM-specific dependencies
                implementation(libs.ktor.client.cio)
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

val downloadSpec = tasks.register("downloadOpenApiSpec") {
    doLast {
        val specFile = file("${layout.buildDirectory.get()}/twilio_conversations_v1.yaml")
        specFile.parentFile.mkdirs()
        URI("https://raw.githubusercontent.com/twilio/twilio-oai/main/spec/yaml/twilio_conversations_v1.yaml").toURL()
            .openStream()
            .use { input: InputStream ->
                specFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
    }
}

tasks.named("openApiGenerate") {
    dependsOn(downloadSpec)
}

openApiGenerate {
    generatorName.set("kotlin")
    inputSpec.set("${layout.buildDirectory.get()}/twilio_conversations_v1.yaml")
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    apiPackage.set("me.kpavlov.twilio.kotlin.conversations")
    modelPackage.set("me.kpavlov.twilio.kotlin.conversations.model")
    packageName.set("me.kpavlov.twilio.kotlin.conversations")
    library.set("multiplatform")

    configOptions.set(
        mapOf(
            "dateLibrary" to "kotlinx-datetime",
            "serializationLibrary" to "kotlinx_serialization",
            "useCoroutines" to "true",
            "enumPropertyNaming" to "UPPERCASE",
            "explicitApi" to "true",
            "omitGradleWrapper" to "true",
            "omitGradlePluginVersions" to "true",
            "sortModelPropertiesByRequiredFlag" to "true",
            "sortParamsByRequiredFlag" to "true",
        )
    )

    generateApiTests.set(false)
    generateModelTests.set(false)
    generateApiDocumentation.set(false)
    generateModelDocumentation.set(false)
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("${layout.buildDirectory.get()}/generated/openapi/src/commonMain/kotlin")
}


val fixGeneratedSources = tasks.register("fixGeneratedSources") {
    dependsOn("openApiGenerate")
    doLast {
        val generatedDir =
            file("${layout.buildDirectory.get()}/generated/openapi/src/commonMain/kotlin")
        if (generatedDir.exists()) {
            generatedDir.walkTopDown().filter { it.isFile && it.extension == "kt" }
                .forEach { file ->
                    val content = file.readText()
                    val fixedContent =
                        content.replace("@Serializable@Serializable", "@Serializable")
                    if (content != fixedContent) {
                        file.writeText(fixedContent)
                        println("Fixed duplicate @Serializable annotation in: ${file.name}")
                    }
                }
        }
    }
}


tasks.named("compileKotlinMetadata") {
    dependsOn("fixGeneratedSources")
}

tasks.named("compileKotlinJvm") {
    dependsOn("fixGeneratedSources")
}

tasks.named("dokkaGenerateModuleHtml") {
    dependsOn("fixGeneratedSources")
}

tasks.named("dokkaGenerateModuleJavadoc") {
    dependsOn("fixGeneratedSources")
}

tasks.named("fixGeneratedSources") {
    mustRunAfter("openApiGenerate")
}

tasks.named("jvmSourcesJar") {
    dependsOn("openApiGenerate")
}

kotlin {
    explicitApi = null
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    if (name.contains("generated")) {
        compilerOptions {
            freeCompilerArgs.addAll("-Xno-explicit-api-warning")
        }
    }
}
