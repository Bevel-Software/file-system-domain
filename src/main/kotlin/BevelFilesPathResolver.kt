package software.bevel.file_system_domain

import java.nio.file.Files
import java.nio.file.Path

class BevelFilesPathResolver {
    companion object {
        fun createDirectoryIfNotExists(path: Path): Path {
            if (!Files.exists(path) || !Files.isDirectory(path)) {
                path.toFile().mkdirs()
            }
            return path
        }

        fun createFileIfNotExists(path: Path): Path {
            if (!Files.exists(path)) {
                Files.createFile(path)
            }
            return path
        }

        fun baseFolderPath(projectPath: String): Path = createDirectoryIfNotExists(Path.of(projectPath, ".bevel"))

        fun privateFolderPath(projectPath: String): Path = createDirectoryIfNotExists(baseFolderPath(projectPath).resolve("do_not_share"))

        fun publicFolderPath(projectPath: String): Path = createDirectoryIfNotExists(baseFolderPath(projectPath).resolve("shareable"))

        fun bevelEnvFilePath(projectPath: String): Path = createFileIfNotExists(privateFolderPath(projectPath).resolve(".env"))

        fun bevelPortFilePath(projectPath: String): Path = createFileIfNotExists(privateFolderPath(projectPath).resolve("port"))

        fun bevelConfigFilePath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve("config.json"))

        fun bevelExtensionConfigPath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve("allowedFileExtensions.json"))

        fun bevelRequirementsFilePath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve("requirements.breq"))

        fun bevelIgnoreFilePath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve(".bevelignore"))

        fun bevelManualRequirementsFilePath(projectPath: String): Path = publicFolderPath(projectPath).resolve("manualRequirements.json")

        fun bevelManualConnectionsFilePath(projectPath: String): Path = publicFolderPath(projectPath).resolve("manualConnections.json")

        fun bevelDatabasePath(projectPath: String): Path {
            return privateFolderPath(projectPath).resolve("bevel.db")
        }

        fun bevelRootGraphFolderPath(projectPath: String): Path = createDirectoryIfNotExists(publicFolderPath(projectPath).resolve("graph"))

        fun bevelBranchGraphFolderPath(projectPath: String, branch: String): Path {
            val rootGraphPath = bevelRootGraphFolderPath(projectPath)

            sanitizeBranchName(branch)
            return rootGraphPath.resolve("graph_$branch")
        }

        fun bevelDataVersion(projectPath: String): Path = baseFolderPath(projectPath).resolve("VERSION")

        /* UTILITIES */
        private fun sanitizeBranchName(branchName: String): String {
            return branchName.replace(Regex("[\\\\/:*?!.^()\"@&\$#<>|\\s]+"), "_")
        }
    }
}