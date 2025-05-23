package software.bevel.file_system_domain.services

import software.bevel.file_system_domain.LCPosition
import software.bevel.file_system_domain.LCRange

interface FileHandler {
    val separator: String

    fun readString(filePath: String): String

    fun readString(filePath: String, startCharacter: Int, endCharacter: Int): String

    fun readString(filePath: String, start: LCPosition, end: LCPosition): String

    fun readString(filePath: String, range: LCRange): String = readString(filePath, range.start, range.end)

    fun readLines(filePath: String): List<String>

    fun readLines(filePath: String, startLine: Int, endLine: Int): List<String>

    fun readLines(filePath: String, start: LCPosition, end: LCPosition): List<String>

    fun readLines(filePath: String, range: LCRange): List<String> = readLines(filePath, range.start.line, range.end.line)

    fun writeString(filePath: String, content: String)

    fun getExtensionFromPath(filePath: String): String

    fun exists(filePath: String): Boolean

    fun isFile(filePath: String): Boolean

    fun delete(filePath: String)

    fun createFile(filePath: String)

    fun createDirectory(filePath: String)
}