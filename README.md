# File System Domain (Kotlin Library for Bevel)

[![Maven Central](https://img.shields.io/maven-central/v/software.bevel/file-system-domain.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22software.bevel%22%20AND%20a:%22file-system-domain%22)
[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)
<!-- Add Build Status Badge once CI is set up -->
<!-- [![Build Status](https://github.com/Bevel-Software/file-system-domain/actions/workflows/your-ci-workflow.yml/badge.svg)](https://github.com/Bevel-Software/file-system-domain/actions/workflows/your-ci-workflow.yml) -->

The `file-system-domain` is a Kotlin/JVM utility library, part of the Bevel suite of developer tools. It provides a standardized and robust way to interact with the file system, especially concerning Bevel-specific project structures and configurations. This library offers abstractions for file operations, utilities for path resolution within Bevel projects, tools for handling text positions, and basic web communication interfaces.

## Project Overview

This library aims to simplify common file system tasks for developers working with or building tools for Bevel projects. Key components include:

*   **`BevelFilesPathResolver`**: A utility for easily locating standard Bevel directories (e.g., `.bevel`, `.bevel/private`, `.bevel/shareable`) and configuration files (e.g., `config.json`, `.env`) within a project. It handles the creation of these paths if they don't exist.
*   **`FileHandler` Interface**: An abstraction for file I/O operations (read, write, delete, check existence). This promotes testability and allows for different implementations (e.g., direct I/O, cached I/O).
*   **`LCPosition` & `LCRange`**: Data classes for representing line/column positions and ranges within text files, useful for source code analysis tools.
*   **Path Utilities**: Helper functions like `relativizePath` and `absolutizePath` for managing file paths relative to a project root.
*   **Web Communication Interfaces**: Basic interfaces (`LocalCommunicationInterface`, `WebClient`) for defining contracts for local and remote communication, potentially used by other Bevel components.

## Key Features

*   ✅ **Standardized Bevel Path Resolution**: Consistently locate Bevel-specific files and directories (e.g., `.bevel/shareable/config.json`).
*   🛡️ **Abstracted File I/O**: Use the `FileHandler` interface for flexible and testable file operations.
*   📄 **Text Position Utilities**: `LCPosition` and `LCRange` for precise referencing within files.
*   🌐 **Maven Central Availability**: Easily integrate into any JVM project (Kotlin, Java, Scala, etc.) using Gradle or Maven.
*   🧩 **Modular Design**: Core file system utilities that can be leveraged by various Bevel tools or other projects.

## Why use File System Domain?

*   **Consistency for Bevel Projects**: Ensures that all tools interacting with a Bevel project structure locate files and directories in the same way.
*   **Testability**: The `FileHandler` abstraction allows mocking file system interactions in unit tests.
*   **Simplified Development**: Reduces boilerplate code for common file and path manipulation tasks.

## Installation / Getting Started

### Prerequisites

*   Java Development Kit (JDK) 17 or higher.
*   A build tool like Gradle or Maven.

### Adding as a Dependency

The library is available on Maven Central.

**Gradle (Kotlin DSL - `build.gradle.kts`):**
```kotlin
dependencies {
    implementation("software.bevel:file-system-domain:1.0.0")
}
```

**Gradle (Groovy DSL - `build.gradle`):**
```groovy
dependencies {
    implementation 'software.bevel:file-system-domain:1.0.0'
}
```

**Maven (`pom.xml`):**
```xml
<dependency>
    <groupId>software.bevel</groupId>
    <artifactId>file-system-domain</artifactId>
    <version>1.0.0</version>
</dependency>
```
*(Replace `1.0.0` with the latest version if necessary)*

## Usage Instructions

Here are some examples of how to use the library:

### 1. Using `BevelFilesPathResolver`

This utility helps locate common Bevel-specific paths. It creates directories/files if they don't exist when a path-resolving method is called.

```kotlin
import software.bevel.file_system_domain.BevelFilesPathResolver
import java.nio.file.Path

fun main() {
    val projectRootPath = "/path/to/your/project" // Or "." for current directory

    // Get path to the .bevel directory
    val bevelBaseDir: Path = BevelFilesPathResolver.baseFolderPath(projectRootPath)
    println(".bevel directory: $bevelBaseDir")

    // Get path to the private .env file
    val envFile: Path = BevelFilesPathResolver.bevelEnvFilePath(projectRootPath)
    println("Bevel .env file: $envFile") // This will create the file if it doesn't exist

    // Get path to the public config.json
    val configFile: Path = BevelFilesPathResolver.bevelConfigFilePath(projectRootPath)
    println("Bevel config.json: $configFile") // Also creates if non-existent

    // Get path to a branch-specific graph folder
    val branchGraphDir: Path = BevelFilesPathResolver.bevelBranchGraphFolderPath(projectRootPath, "main")
    println("Graph folder for 'main' branch: $branchGraphDir")
}
```

### 2. Using `LCPosition` and `LCRange`

These are useful for representing locations in text files.

```kotlin
import software.bevel.file_system_domain.LCPosition
import software.bevel.file_system_domain.LCRange

fun main() {
    val startPos = LCPosition(line = 5, column = 10)
    val endPos = LCPosition(line = 5, column = 25)
    val range = LCRange(startPos, endPos)

    println("Position: $startPos") // Output: Position: 5:10
    println("Range: $range")     // Output: Range: 5:10-5:25

    val anotherPos = LCPosition(line = 5, column = 15)
    if (range.contains(LCRange(anotherPos, anotherPos))) {
        println("$anotherPos is within $range")
    }
}
```

### 3. Path Utilities

`relativizePath` and `absolutizePath` (Note: these operate on string paths and are simple utilities).

```kotlin
import software.bevel.file_system_domain.relativizePath
import software.bevel.file_system_domain.absolutizePath

fun main() {
    val projectPath = "/Users/dev/my-project"
    val absoluteFilePath = "/Users/dev/my-project/src/main/File.kt"
    val relativeFilePath = "src/main/File.kt"

    val relPath = relativizePath(absoluteFilePath, projectPath)
    println("Relativized: $relPath") // Output: Relativized: src/main/File.kt

    val absPath = absolutizePath(relativeFilePath, projectPath)
    println("Absolutized: $absPath") // Output: Absolutized: /Users/dev/my-project/src/main/File.kt
}
```

## API Highlights

*   **`software.bevel.file_system_domain.BevelFilesPathResolver`**:
    *   Provides static methods like `baseFolderPath()`, `privateFolderPath()`, `publicFolderPath()`, `bevelEnvFilePath()`, `bevelConfigFilePath()`, etc., to get `java.nio.file.Path` objects to standard Bevel locations.
*   **`software.bevel.file_system_domain.services.FileHandler`**:
    *   Interface defining methods like `readString()`, `readLines()`, `writeString()`, `exists()`, `isFile()`, `delete()`, `createFile()`, `createDirectory()`, `getExtensionFromPath()`.
*   **`software.bevel.file_system_domain.services.CachedFileHandler`**:
    *   Interface extending `FileHandler` with `clearCache()`.
*   **`software.bevel.file_system_domain.LCPosition`**:
    *   Data class representing a `(line, column)` pair.
*   **`software.bevel.file_system_domain.LCRange`**:
    *   Data class representing a range with `start` and `end` `LCPosition`.
*   **`software.bevel.file_system_domain.FileWalker`**:
    *   Interface for directory traversal logic. (Implementations to be provided by users or other modules).
*   **`software.bevel.file_system_domain.web` package**:
    *   `LocalCommunicationInterface`: Contract for local inter-process communication.
    *   `WebClient`: Contract for making HTTP requests.
    *   (These are primarily interfaces; implementations might reside in other Bevel modules or be provided by the user).


## Building from Source

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Bevel-Software/file-system-domain.git
    cd file-system-domain
    ```

2.  **Build the project using Gradle:**
    ```bash
    ./gradlew build
    ```
    This will compile the source code, run tests, and build the JAR file (typically found in `build/libs/`).

3.  **Run tests:**
    ```bash
    ./gradlew test
    ```

## Contributing

🎉 Contributions are welcome! We’re excited to collaborate with the community to improve File System Domain.

*   **Report Issues:** If you encounter a problem or have questions, please [open an issue](https://github.com/Bevel-Software/file-system-domain/issues) in this repository.
*   **Feature Requests:** Have an idea? Open an issue to discuss it.
*   **Pull Requests:**
    *   Fork the repository and create your branch from `main` (or the relevant development branch).
    *   Ensure tests pass (`./gradlew test`).
    *   If you add new functionality, please add tests for it.
    *   Follow Kotlin coding conventions.
    *   Make sure your code is formatted (e.g., using IntelliJ's default Kotlin formatter or Ktlint if configured).

Please be respectful in all interactions.

## License

This project is open source and available under the [Mozilla Public License Version 2.0](LICENSE). See the `LICENSE` file for the full license text.

The `NOTICE` file contains information about licenses of third-party dependencies used in this project, such as:
*   SLF4J API (MIT License)
*   JetBrains Annotations (Apache License, Version 2.0)
*   Kotlin Standard Library (Apache License, Version 2.0)

---

Happy coding! We hope File System Domain helps you manage files and project structures more effectively. If you have any questions, don’t hesitate to reach out via the issue tracker.
```
