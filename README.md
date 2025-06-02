# File System Domain (Kotlin Library for Bevel)

[![Maven Central](https://img.shields.io/maven-central/v/software.bevel/file-system-domain.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22software.bevel%22%20AND%20a:%22file-system-domain%22)
[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)
<!-- [![Build Status](https://github.com/Bevel-Software/file-system-domain/actions/workflows/your-ci-workflow.yml/badge.svg)](https://github.com/Bevel-Software/file-system-domain/actions/workflows/your-ci-workflow.yml) -->

Welcome to `file-system-domain`, a Kotlin/JVM utility library designed as a foundational component for the [Bevel](https://bevel.software) suite of developer tools! This library provides a standardized, robust, and testable way to interact with the file system, particularly tailored for Bevel-specific project structures and configurations. It offers elegant abstractions for file operations, intelligent path resolution within Bevel projects, precise tools for handling text positions, and foundational interfaces for web communication.

## 🚀 Project Overview

The `file-system-domain` library aims to streamline common file system tasks, making life easier for developers working with or building tools for Bevel projects. It ensures consistency and reduces boilerplate, allowing you to focus on higher-level logic.

**Core Components:**

*   **`BevelFilesPathResolver`**: Your go-to utility for effortlessly locating standard Bevel directories (like `.bevel`, `.bevel/do_not_share`, `.bevel/shareable`) and crucial configuration files (e.g., `config.json`, `.env`). It smartly creates these paths if they don't already exist, ensuring your Bevel environment is always correctly structured.
*   **`FileHandler` Interface**: A powerful abstraction for all file I/O operations (read, write, delete, check existence, etc.). This design promotes testability by allowing mock implementations and offers flexibility for various I/O strategies (e.g., direct disk access, cached operations via `CachedFileHandler`).
*   **`LCPosition` & `LCRange`**: Immutable data classes for representing precise line/column positions and ranges within text files. Indispensable for source code analysis, linting, or any tool that needs to reference specific text segments.
*   **Path Utilities**: Handy functions like `relativizePath` and `absolutizePath` for seamless management of file paths relative to a project root.
*   **Web Communication Interfaces**: Basic yet versatile interfaces (`LocalCommunicationInterface`, `WebClient`) defining contracts for local inter-process communication and remote HTTP requests. These serve as building blocks for more complex communication patterns in other Bevel components.

## ✨ Key Features

*   🗺️ **Standardized Bevel Path Resolution**: Consistently and reliably locate Bevel-specific files and directories (e.g., `.bevel/shareable/config.json`, `.bevel/do_not_share/.env`).
*   🛡️ **Abstracted & Testable File I/O**: Utilize the `FileHandler` interface for clean, flexible, and easily mockable file operations.
*   📄 **Precise Text Position Utilities**: `LCPosition` and `LCRange` allow for accurate referencing and manipulation of locations within text files.
*   🌐 **Maven Central Availability**: Easily integrate into any JVM project (Kotlin, Java, Scala, etc.) using Gradle or Maven.
*   🧩 **Modular & Reusable Design**: Core file system utilities that can be independently leveraged by various Bevel tools or any other JVM project.
*   🛠️ **Auto-Creation of Bevel Structure**: `BevelFilesPathResolver` automatically creates necessary Bevel directories and files on first access, simplifying setup.

## 🤔 Why use File System Domain?

*   **Consistency for Bevel Projects**: Ensures all tools interacting with a Bevel project structure locate files and directories uniformly. No more "it works on my machine" for file paths!
*   **Enhanced Testability**: The `FileHandler` abstraction is a game-changer for unit testing, allowing you to mock file system interactions without touching the actual disk.
*   **Simplified Development**: Drastically reduces boilerplate code for common file, path manipulation, and Bevel-specific setup tasks.
*   **Robustness**: Handles path creation and provides a clear contract for file operations, leading to more resilient applications.

## ⚙️ Installation / Getting Started

### Prerequisites

*   Java Development Kit (JDK) 17 or higher.
*   A build tool like Gradle or Maven.

### Adding as a Dependency

The library is conveniently available on Maven Central.

**Gradle (Kotlin DSL - `build.gradle.kts`):**
```kotlin
dependencies {
    implementation("software.bevel:file-system-domain:1.1.0") // Use the latest version
}
```

**Gradle (Groovy DSL - `build.gradle`):**
```groovy
dependencies {
    implementation 'software.bevel:file-system-domain:1.1.0' // Use the latest version
}
```

**Maven (`pom.xml`):**
```xml
<dependency>
    <groupId>software.bevel</groupId>
    <artifactId>file-system-domain</artifactId>
    <version>1.1.0</version> <!-- Use the latest version -->
</dependency>
```
*Note: Always check [Maven Central](https://search.maven.org/search?q=g:%22software.bevel%22%20AND%20a:%22file-system-domain%22) for the latest version number.*

## 💡 Usage Instructions

Here are some examples demonstrating how to leverage the power of `file-system-domain`:

### 1. Using `BevelFilesPathResolver`

This utility is your best friend for finding Bevel-specific paths. It automatically creates directories and files if they don't exist when a path-resolving method is called.

```kotlin
import software.bevel.file_system_domain.BevelFilesPathResolver
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    val projectRootPathString = Paths.get(".").toAbsolutePath().normalize().toString() // Or "/path/to/your/project"

    // Get path to the .bevel directory
    val bevelBaseDir: Path = BevelFilesPathResolver.baseFolderPath(projectRootPathString)
    println(".bevel directory: $bevelBaseDir") // Created if it didn't exist

    // Get path to the private .env file
    // This will create .bevel/do_not_share/.env if it doesn't exist
    val envFile: Path = BevelFilesPathResolver.bevelEnvFilePath(projectRootPathString)
    println("Bevel .env file: $envFile")

    // Get path to the public config.json
    // This will create .bevel/shareable/config.json if it doesn't exist
    val configFile: Path = BevelFilesPathResolver.bevelConfigFilePath(projectRootPathString)
    println("Bevel config.json: $configFile")

    // Get path to a branch-specific graph folder (e.g., for "feature/new-ui")
    // Branch names are sanitized for filesystem compatibility.
    val branchGraphDir: Path = BevelFilesPathResolver.bevelBranchGraphFolderPath(projectRootPathString, "feature/new-ui")
    println("Graph folder for 'feature/new-ui' branch: $branchGraphDir") // e.g., .../.bevel/shareable/graph/graph_feature_new-ui
}
```

### 2. Using `LCPosition` and `LCRange`

These data classes are perfect for representing locations and selections in text files.

```kotlin
import software.bevel.file_system_domain.LCPosition
import software.bevel.file_system_domain.LCRange

fun main() {
    val startPos = LCPosition(line = 4, column = 9) // 0-indexed: 5th line, 10th char
    val endPos = LCPosition(line = 4, column = 24)   // 0-indexed: 5th line, 25th char
    val highlightRange = LCRange(startPos, endPos)

    println("Start Position: $startPos") // Output: Start Position: 4:9
    println("Highlight Range: $highlightRange") // Output: Highlight Range: 4:9-4:24

    val cursorPosition = LCPosition(line = 4, column = 15)
    val cursorRange = LCRange(cursorPosition, cursorPosition) // A range representing a single point

    if (highlightRange.contains(cursorRange)) {
        println("$cursorPosition is within $highlightRange")
    }

    // LCPosition arithmetic
    val nextCharPos = startPos + 1 // Adds to column: 4:10
    val nextLinePos = startPos + LCPosition(1, 0) // Adds line, column becomes 0: 5:0
    println("Next char: $nextCharPos, Next line start: $nextLinePos")
}
```

### 3. Path Utilities (`relativizePath` and `absolutizePath`)

Simple yet effective string-based path manipulation helpers.

```kotlin
import software.bevel.file_system_domain.relativizePath
import software.bevel.file_system_domain.absolutizePath

fun main() {
    val projectBasePath = "/Users/dev/my-bevel-project"
    val absoluteFilePath = "/Users/dev/my-bevel-project/src/main/kotlin/App.kt"
    val relativeFilePath = "src/main/kotlin/App.kt"

    val relPath = relativizePath(absoluteFilePath, projectBasePath)
    println("Relativized: $relPath") // Output: Relativized: src/main/kotlin/App.kt

    val absPath = absolutizePath(relativeFilePath, projectBasePath)
    println("Absolutized: $absPath") // Output: Absolutized: /Users/dev/my-bevel-project/src/main/kotlin/App.kt
}
```

### 4. Using `FileHandler` (Conceptual)

While you'd typically get an implementation of `FileHandler` (e.g., injected or from another Bevel service), here's how you might use it:

```kotlin
// Assume 'fileHandler' is an instance of FileHandler
// import software.bevel.file_system_domain.services.FileHandler
// val fileHandler: FileHandler = ...

// val content = fileHandler.readString("path/to/your/file.txt")
// println(content)

// fileHandler.writeString("path/to/output.txt", "Hello from FileHandler!")

// if (fileHandler.exists("path/to/another.txt")) {
//     println("File exists!")
// }
```

## 📖 API Highlights

*   **`software.bevel.file_system_domain.BevelFilesPathResolver`**:
    *   Provides static methods like `baseFolderPath()`, `privateFolderPath()`, `publicFolderPath()`, `bevelEnvFilePath()`, `bevelConfigFilePath()`, `bevelBranchGraphFolderPath()`, etc.
    *   All methods return `java.nio.file.Path` objects.
    *   Crucially, these methods ensure the resolved paths (directories or files) are **created if they do not already exist**.
    *   Includes `sanitizeBranchName()` for filesystem-safe branch names.
*   **`software.bevel.file_system_domain.services.FileHandler`**:
    *   Interface defining core file operations: `readString()`, `readLines()`, `writeString()`, `exists()`, `isFile()`, `delete()`, `createFile()`, `createDirectory()`, `getExtensionFromPath()`.
    *   Supports reading specific ranges via character offsets or `LCPosition`/`LCRange`.
*   **`software.bevel.file_system_domain.services.CachedFileHandler`**:
    *   Extends `FileHandler` with a `clearCache()` method for cache invalidation.
*   **`software.bevel.file_system_domain.LCPosition`**:
    *   Data class: `(line: Int, column: Int)`. 0-indexed.
    *   Supports `compareTo`, `toString()`, and arithmetic operators (`+`, `-`).
*   **`software.bevel.file_system_domain.LCRange`**:
    *   Data class: `(start: LCPosition, end: LCPosition)`.
    *   Supports `compareTo`, `toString()`, and `contains(other: LCRange)`.
*   **`software.bevel.file_system_domain.PathHandling.kt`**:
    *   Top-level functions `relativizePath(filePath: String, projectPath: String): String` and `absolutizePath(filePath: String, projectPath: String): String`.
*   **`software.bevel.file_system_domain.FileWalker`**:
    *   Interface for directory traversal logic: `walk(directory: String): List<String>`. Implementations are expected from users or other modules.
*   **`software.bevel.file_system_domain.web` package**:
    *   `LocalCommunicationInterface`: Contract for local inter-process communication (send messages, check connection). `Closeable`.
    *   `WebClient`: Contract for making HTTP GET/POST requests (blocking and non-blocking variants).
    *   `WebResponse`, `Blockable`, `Subscribable`: Helper interfaces for async operations and responses.
    *   `SimpleResponse`: Basic implementation of `WebResponse`.
    *   `CommunicationInterfaceCreator`: Factory for `LocalCommunicationInterface`.
    *   (These are primarily interfaces; concrete implementations might reside in other Bevel modules or be provided by the user's application).

## 🛠️ Building from Source

Want to tinker with the code or build it locally?

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Bevel-Software/file-system-domain.git
    cd file-system-domain
    ```

2.  **Build the project using Gradle:**
    The included Gradle wrapper (`gradlew`) takes care of downloading the correct Gradle version.
    ```bash
    ./gradlew build
    ```
    This will compile the source code, run tests, and build the JAR file (typically found in `build/libs/file-system-domain-1.1.0.jar`).

3.  **Run tests:**
    ```bash
    ./gradlew test
    ```

## 💖 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**!

*   **Found a Bug?** [Open an issue](https://github.com/Bevel-Software/file-system-domain/issues) with details and steps to reproduce.
*   **Have a Feature Idea?** [Open an issue](https://github.com/Bevel-Software/file-system-domain/issues) to discuss its feasibility and design.
*   **Ready to Submit a Pull Request?**
    1.  Fork the Project.
    2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
    3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
    4.  Push to the Branch (`git push origin feature/AmazingFeature`).
    5.  Open a Pull Request against the `main` branch.

Please ensure your code adheres to Kotlin coding conventions, is well-tested, and that all existing tests pass (`./gradlew test`).

## 📜 License

This project is open source and distributed under the **Mozilla Public License Version 2.0**. See the [`LICENSE`](LICENSE) file for the full license text.
