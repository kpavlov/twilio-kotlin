# Twilio Kotlin SDK

[![Build](https://github.com/kpavlov/twilio-kotlin/actions/workflows/build.yml/badge.svg)](https://github.com/kpavlov/twilio-kotlin/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Target JVM](https://img.shields.io/badge/Target%20JDK-17-green.svg)](https://jdk.java.net/17/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.2-green.svg)](https://gradle.org)
[![Documentation](https://img.shields.io/badge/Documentation-KDoc-blue)](https://kpavlov.github.io/twilio-kotlin/)

A Kotlin Multiplatform SDK for Twilio APIs with Ktor HTTP client and comprehensive OpenAPI-generated clients.

[![Buy me a Coffee](https://cdn.buymeacoffee.com/buttons/default-orange.png)](https://buymeacoffee.com/mailsk)

## Features

- **Kotlin Multiplatform** support for JVM, with extensibility for other platforms
- **Twilio Conversations API** client generated from OpenAPI specification
- **Ktor HTTP client** with basic authentication support for Twilio Account SID and Auth Token
- **Type-safe API** with Kotlin serialization for all Twilio API models
- **Comprehensive testing** setup with:
  - Kotlin tests using [Kotest](https://kotest.io/)
  - Mock HTTP client testing with Ktor MockEngine
  - Mocking with [MockK](https://mockk.io/)
- **Code quality** tools:
  - [Detekt](https://detekt.github.io/detekt/) for static code analysis
  - [Spotless](https://github.com/diffplug/spotless) for code formatting
  - [Kover](https://github.com/Kotlin/kotlinx-kover) for code coverage
- **Documentation** generation with [Dokka](https://github.com/Kotlin/dokka)
- **Maven publication** setup for easy distribution
- **OpenAPI code generation** automatically generates up-to-date client code from Twilio's OpenAPI specifications

## Project Structure

- **lib**: The main library module containing the Kotlin Multiplatform code
  - `src/commonMain`: Common Kotlin code and generated Twilio API clients
  - `src/jvmMain`: JVM-specific code (Ktor CIO engine)
  - `src/commonTest`: Common tests including Twilio client tests
  - `src/jvmTest`: JVM-specific tests
  - `build/generated/openapi`: Auto-generated Twilio API client code from OpenAPI specs
- **examples**: Module demonstrating usage of the Twilio SDK
  - Contains both Kotlin and Java examples and tests

## Requirements

- JDK 17 or higher
- Gradle 8.7 or higher

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/kpavlov/twilio-kotlin.git
cd twilio-kotlin
```

### Build the Project

```bash
./gradlew build
```

Or using the Makefile:

```bash
make build
```

## Usage


**[TBD]**

See the `examples` module for more detailed usage examples.

## Development

### Available Make Commands

- `make build`: Build the project
- `make test`: Run tests
- `make format`: Format the code using Spotless
- `make lint`: Run linting checks (Spotless, Detekt)
- `make clean`: Clean the project
- `make publish`: Publish to Maven Local
- `make doc`: Generate KDoc documentation for the lib project
- `make help`: Show help message

### Running Tests

```bash
./gradlew test
```

Or using the Makefile:

```bash
make test
```

### Generating Documentation

```bash
./gradlew :lib:dokkaGeneratePublicationHtml
```

Or using the Makefile:

```bash
make doc
```

The documentation is automatically generated and published to GitHub Pages when changes are pushed to the main branch. You can access the latest documentation at:

https://yourusername.github.io/kotlin-kmp-library/

### Publishing to Maven Local

```bash
./gradlew publishToMavenLocal
```

Or using the Makefile:

```bash
make publish
```

## API Coverage

Currently, this SDK includes:

- **Twilio Conversations API** - Complete client generated from OpenAPI specifications
  - Conversations management
  - Messages and participants
  - Webhooks and notifications
  - Service configurations

### Adding More Twilio APIs

To add support for additional Twilio APIs:

1. Add the OpenAPI spec URL to the `openApiGenerate` configuration in `lib/build.gradle.kts`
2. Run `./gradlew :lib:openApiGenerate` to generate client code
3. Update the source directories to include the new generated code

## Authentication

The SDK uses HTTP Basic Authentication with your Twilio Account SID as the username and Auth Token as the password:

```kotlin
conversationApi.setUsername("YOUR_ACCOUNT_SID")  // Starts with "AC"
conversationApi.setPassword("YOUR_AUTH_TOKEN")   // Your auth token
```

Find your credentials in the [Twilio Console](https://console.twilio.com/).

## License

This project is licensed under the MIT License - see the LICENSE file for details.
