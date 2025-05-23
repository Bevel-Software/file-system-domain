package software.bevel.file_system_domain

import java.io.File
import java.nio.file.Path
import kotlin.io.path.pathString
import kotlin.io.path.relativeTo
import kotlin.io.path.relativeToOrSelf

fun relativizePath(filePath: String, projectPath: String): String {
    val pathObj = File(filePath)
    if(pathObj.path.contains(File(projectPath).path))
        return pathObj.relativeToOrSelf(File(projectPath)).toString()
    return filePath
}

fun absolutizePath(filePath: String, projectPath: String): String {
    val pathObj = File(filePath)
    if(!pathObj.path.contains(File(projectPath).path))
        return File(projectPath).resolve(pathObj).normalize().toString()
    return filePath
}