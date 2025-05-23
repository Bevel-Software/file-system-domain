package software.bevel.file_system_domain

import java.io.File
import java.nio.file.Path
import kotlin.io.path.pathString
import kotlin.io.path.relativeTo
import kotlin.io.path.relativeToOrSelf

/**
 * Relativizes the given file path against the project path if the file path is contained within the project path.
 * Otherwise, returns the original file path.
 *
 * @param filePath The absolute or relative path to the file.
 * @param projectPath The absolute path to the project directory.
 * @return The relativized path if `filePath` is inside `projectPath`, otherwise `filePath` itself.
 */
fun relativizePath(filePath: String, projectPath: String): String {
    val pathObj = File(filePath)
    if(pathObj.path.contains(File(projectPath).path))
        return pathObj.relativeToOrSelf(File(projectPath)).toString()
    return filePath
}

/**
 * Absolutizes the given file path against the project path if the file path is not already an absolute path
 * that is contained within the project path. If `filePath` is already an absolute path within `projectPath`,
 * it returns `filePath`.
 *
 * @param filePath The relative or absolute path to the file.
 * @param projectPath The absolute path to the project directory, used as a base for resolving relative paths.
 * @return The absolutized and normalized path if `filePath` was relative or outside `projectPath`, otherwise `filePath` itself.
 */
fun absolutizePath(filePath: String, projectPath: String): String {
    val pathObj = File(filePath)
    if(!pathObj.path.contains(File(projectPath).path))
        return File(projectPath).resolve(pathObj).normalize().toString()
    return filePath
}