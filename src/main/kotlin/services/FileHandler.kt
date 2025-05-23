package software.bevel.file_system_domain.services

import software.bevel.file_system_domain.LCPosition
import software.bevel.file_system_domain.LCRange

/**
 * Defines a contract for handling file operations.
 * Implementations can provide different strategies and libraries for reading, writing, and managing files (e.g., direct I/O, cached I/O).
 */
interface FileHandler {
    /** The system-dependent file separator character (e.g., "/" or "\\"). */
    val separator: String

    /**
     * Reads the entire content of the specified file as a single String.
     *
     * @param filePath The path to the file to read.
     * @return The content of the file as a String.
     */
    fun readString(filePath: String): String

    /**
     * Reads a portion of the specified file as a String, defined by character offsets.
     *
     * @param filePath The path to the file to read.
     * @param startCharacter The starting character offset (inclusive).
     * @param endCharacter The ending character offset (exclusive).
     * @return The specified portion of the file content as a String.
     */
    fun readString(filePath: String, startCharacter: Int, endCharacter: Int): String

    /**
     * Reads a portion of the specified file as a String, defined by start and end [LCPosition] objects.
     *
     * @param filePath The path to the file to read.
     * @param start The starting position ([LCPosition]).
     * @param end The ending position ([LCPosition]).
     * @return The specified portion of the file content as a String.
     */
    fun readString(filePath: String, start: LCPosition, end: LCPosition): String

    /**
     * Reads a portion of the specified file as a String, defined by an [LCRange].
     * This is a convenience method that delegates to `readString(filePath, range.start, range.end)`.
     *
     * @param filePath The path to the file to read.
     * @param range The [LCRange] specifying the portion to read.
     * @return The specified portion of the file content as a String.
     */
    fun readString(filePath: String, range: LCRange): String = readString(filePath, range.start, range.end)

    /**
     * Reads all lines from the specified file.
     *
     * @param filePath The path to the file to read.
     * @return A list of strings, where each string is a line from the file.
     */
    fun readLines(filePath: String): List<String>

    /**
     * Reads a specific range of lines from the specified file.
     *
     * @param filePath The path to the file to read.
     * @param startLine The starting line number (inclusive, 0-indexed).
     * @param endLine The ending line number (inclusive, 0-indexed).
     * @return A list of strings representing the specified lines from the file.
     */
    fun readLines(filePath: String, startLine: Int, endLine: Int): List<String>

    /**
     * Reads lines from the specified file that fall within the given start and end [LCPosition] objects.
     * Note: This typically means lines fully or partially covered by the character range from start to end position.
     * The exact behavior might depend on the implementation.
     *
     * @param filePath The path to the file to read.
     * @param start The starting position ([LCPosition]).
     * @param end The ending position ([LCPosition]).
     * @return A list of strings representing the lines within the specified positions.
     */
    fun readLines(filePath: String, start: LCPosition, end: LCPosition): List<String>

    /**
     * Reads lines from the specified file that fall within the given [LCRange].
     * This is a convenience method that delegates to `readLines(filePath, range.start.line, range.end.line)`.
     *
     * @param filePath The path to the file to read.
     * @param range The [LCRange] specifying the lines to read (based on line numbers of start and end positions).
     * @return A list of strings representing the lines within the specified range.
     */
    fun readLines(filePath: String, range: LCRange): List<String> = readLines(filePath, range.start.line, range.end.line)

    /**
     * Writes the given content to the specified file, overwriting the file if it already exists.
     *
     * @param filePath The path to the file to write to.
     * @param content The string content to write to the file.
     */
    fun writeString(filePath: String, content: String)

    /**
     * Gets the extension of the file from its path.
     *
     * @param filePath The path to the file.
     * @return The file extension (e.g., "txt", "kt"), or an empty string if no extension is found.
     */
    fun getExtensionFromPath(filePath: String): String

    /**
     * Checks if a file or directory exists at the specified path.
     *
     * @param filePath The path to check.
     * @return `true` if a file or directory exists at the path, `false` otherwise.
     */
    fun exists(filePath: String): Boolean

    /**
     * Checks if the path points to an existing regular file.
     *
     * @param filePath The path to check.
     * @return `true` if the path points to an existing file, `false` otherwise (e.g., it's a directory or does not exist).
     */
    fun isFile(filePath: String): Boolean

    /**
     * Deletes the file or directory at the specified path.
     * If the path is a directory, it might need to be empty depending on the implementation.
     *
     * @param filePath The path to the file or directory to delete.
     */
    fun delete(filePath: String)

    /**
     * Creates a new, empty file at the specified path if it does not already exist.
     * If parent directories do not exist, they might be created depending on the implementation.
     *
     * @param filePath The path where the file should be created.
     */
    fun createFile(filePath: String)

    /**
     * Creates a directory at the specified path if it does not already exist.
     * This may also create parent directories if they do not exist.
     *
     * @param filePath The path where the directory should be created.
     */
    fun createDirectory(filePath: String)
}