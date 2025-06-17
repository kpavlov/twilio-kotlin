plugins {
    kotlin("jvm") apply false
    `dokka-convention`
}

dependencies {
    "dokka"(project(":twilio-kotlin"))
    dokka(project(":examples"))
}

dokka {
    moduleName.set("Kotlin Multiplatform Library Template")
    dokkaPublications.html {
        outputDirectory = layout.projectDirectory.dir("public/apidocs")
    }
}
