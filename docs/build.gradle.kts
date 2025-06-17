plugins {
    kotlin("jvm") apply false
    `dokka-convention`
}

dependencies {
    dokka(project(":lib"))
    dokka(project(":examples"))
}

dokka {
    moduleName.set("Kotlin Multiplatform Library Template")
    dokkaPublications.html {
        outputDirectory = layout.projectDirectory.dir("public/apidocs")
    }
}
