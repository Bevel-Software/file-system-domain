package software.bevel.file_system_domain.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.bevel.file_system_domain.LCPosition
import java.io.File
import kotlin.math.max
import kotlin.math.min

open class IoFileHandler(
    private val logger: Logger = LoggerFactory.getLogger(IoFileHandler::class.java)
): FileHandler {
    override val separator: String
        get() = File.separator

    override fun readString(filePath: String): String {
        val file = File(filePath)
        if(!file.exists() || !file.isFile) {
            logger.error("Could not readString from file: $filePath")
            return ""
        }
        return file.readText()
    }

    override fun readString(filePath: String, startCharacter: Int, endCharacter: Int): String {
        try {
            val fileContent = readString(filePath)
            return fileContent.substring(max(startCharacter, 0), min(endCharacter, fileContent.length))
        } catch (ex: Exception) {
            logger.error(filePath, ex)
            return ""
        }
    }

    override fun readString(filePath: String, start: LCPosition, end: LCPosition): String {
        val lines = readLines(filePath, start.line, end.line)
        val actualEnd = end.copy(line = min(end.line, lines.size - 1))
        if (lines.isEmpty()) return ""
        try {
            if(start.line == actualEnd.line) {
                if(start.column > min(actualEnd.column, lines[0].length)) {
                    logger.error("Attempted to read starting after line end, line ${start.line}, $filePath")
                    return lines[0]
                }
                return lines[0].substring(start.column, min(actualEnd.column, lines[0].length))
            }
            if(filePath.contains("condition-block-state-property")) {
                println("Test")
            }
            return start.column.let {
                if(it > lines.first().length) return@let lines.first()
                lines.first().substring(it)
            } + "\n" +
                    (if(lines.size > 2) lines.subList(1, lines.size - 1).joinToString("") { it + "\n" } else "") +
                    lines.last().substring(0, min(actualEnd.column, lines.last().length))
        } catch (ex: Exception) {
            logger.error("Error reading string from $filePath", ex)
            return ""
        }
    }

    override fun readLines(filePath: String): List<String> {
        val file = File(filePath)
        if(!file.exists() || !file.isFile) {
            logger.error("Could not readString from file: $filePath")
            return listOf()
        }
        return file.readLines()
    }

    override fun readLines(filePath: String, startLine: Int, endLine: Int): List<String> {
        val allLines = readLines(filePath)
        if(allLines.size < startLine) {
            logger.error("Could not readLines $startLine-$endLine from file: $filePath")
            return listOf()
        }
        return allLines.subList(startLine, min(allLines.size, endLine + 1))
    }

    override fun readLines(filePath: String, start: LCPosition, end: LCPosition): List<String> {
        val lines = readLines(filePath, start.line, end.line)
        val actualEnd = end.copy(line = min(end.line, lines.size - 1))
        if (lines.isEmpty()) return listOf()
        try {
            if(start.line == actualEnd.line) {
                if(start.column > min(actualEnd.column, lines[0].length)) {
                    logger.error("Attempted to read starting after line end, line ${start.line}, $filePath")
                    return listOf(lines[0])
                }
                return listOf(lines[0].substring(start.column, min(actualEnd.column, lines[0].length)))
            }
            return listOf(start.column.let {
                if(it > lines.first().length) return@let lines.first()
                lines.first().substring(it)
            }) +
                    (if(lines.size > 2) lines.subList(1, lines.size - 1) else listOf()) +
                    listOf(lines.last().substring(0, min(actualEnd.column, lines.last().length)))
        } catch (ex: Exception) {
            logger.error("Error reading lines from $filePath", ex)
            return listOf()
        }
    }

    override fun exists(filePath: String): Boolean {
        return File(filePath).exists()
    }

    override fun isFile(filePath: String): Boolean {
        return File(filePath).isFile
    }

    override fun delete(filePath: String) {
        File(filePath).delete()
    }

    override fun createFile(filePath: String) {
        File(filePath).createNewFile()
    }

    override fun createDirectory(filePath: String) {
        File(filePath).mkdirs()
    }

    override fun writeString(filePath: String, content: String) {
        File(filePath).writeText(content)
    }

    override fun getExtensionFromPath(filePath: String): String {
        var lastSeparator = -1
        var lastDot = -1

        for (i in filePath.indices.reversed()) {
            when (filePath[i]) {
                '/' , '\\' -> {
                    lastSeparator = i
                    break
                }
                '.' -> if (lastDot == -1) lastDot = i
            }
        }

        return if (lastDot != -1 && lastDot > lastSeparator) {
            filePath.substring(lastDot)
        } else {
            ""
        }
    }
}