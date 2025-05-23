package software.bevel.file_system_domain

import java.nio.file.Files
import java.nio.file.Path

/**
 * Utility class for resolving paths to various Bevel-specific files and directories within a project.
 * All paths are created if they do not already exist.
 */
class BevelFilesPathResolver {
    /**
     * Companion object containing utility methods for path resolution.
     */
    companion object {
        /**
         * Creates a directory at the specified path if it does not already exist.
         *
         * @param path The path where the directory should be created.
         * @return The path to the (potentially newly created) directory.
         */
        fun createDirectoryIfNotExists(path: Path): Path {
            if (!Files.exists(path) || !Files.isDirectory(path)) {
                path.toFile().mkdirs()
            }
            return path
        }

        /**
         * Creates a file at the specified path if it does not already exist.
         *
         * @param path The path where the file should be created.
         * @return The path to the (potentially newly created) file.
         */
        fun createFileIfNotExists(path: Path): Path {
            if (!Files.exists(path)) {
                Files.createFile(path)
            }
            return path
        }

        /**
         * Returns the path to the base '.bevel' folder within the project.
         * Creates the directory if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the '.bevel' folder.
         */
        fun baseFolderPath(projectPath: String): Path = createDirectoryIfNotExists(Path.of(projectPath, ".bevel"))

        /**
         * Returns the path to the 'do_not_share' (private) folder within the '.bevel' directory.
         * Creates the directory if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the private folder.
         */
        fun privateFolderPath(projectPath: String): Path = createDirectoryIfNotExists(baseFolderPath(projectPath).resolve("do_not_share"))

        /**
         * Returns the path to the 'shareable' (public) folder within the '.bevel' directory.
         * Creates the directory if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the public folder.
         */
        fun publicFolderPath(projectPath: String): Path = createDirectoryIfNotExists(baseFolderPath(projectPath).resolve("shareable"))

        /**
         * Returns the path to the '.env' file within the private Bevel folder.
         * Creates the file if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the '.env' file.
         */
        fun bevelEnvFilePath(projectPath: String): Path = createFileIfNotExists(privateFolderPath(projectPath).resolve(".env"))

        /**
         * Returns the path to the 'port' file within the private Bevel folder.
         * This file typically stores the port number used by a Bevel service.
         * Creates the file if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'port' file.
         */
        fun bevelPortFilePath(projectPath: String): Path = createFileIfNotExists(privateFolderPath(projectPath).resolve("port"))

        /**
         * Returns the path to the 'config.json' file within the public Bevel folder.
         * Creates the file if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'config.json' file.
         */
        fun bevelConfigFilePath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve("config.json"))

        /**
         * Returns the path to the 'allowedFileExtensions.json' file within the public Bevel folder.
         * This file lists file extensions that Bevel is allowed to process.
         * Creates the file if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'allowedFileExtensions.json' file.
         */
        fun bevelExtensionConfigPath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve("allowedFileExtensions.json"))

        /**
         * Returns the path to the 'requirements.breq' file within the public Bevel folder.
         * This file lists project requirements for Bevel.
         * Creates the file if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'requirements.breq' file.
         */
        fun bevelRequirementsFilePath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve("requirements.breq"))

        /**
         * Returns the path to the '.bevelignore' file within the public Bevel folder.
         * This file specifies patterns for files and directories that Bevel should ignore.
         * Creates the file if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the '.bevelignore' file.
         */
        fun bevelIgnoreFilePath(projectPath: String): Path = createFileIfNotExists(publicFolderPath(projectPath).resolve(".bevelignore"))

        /**
         * Returns the path to the 'manualRequirements.json' file within the public Bevel folder.
         * This file is used for manually specified requirements.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'manualRequirements.json' file.
         */
        fun bevelManualRequirementsFilePath(projectPath: String): Path = publicFolderPath(projectPath).resolve("manualRequirements.json")

        /**
         * Returns the path to the 'manualConnections.json' file within the public Bevel folder.
         * This file is used for manually specified connections.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'manualConnections.json' file.
         */
        fun bevelManualConnectionsFilePath(projectPath: String): Path = publicFolderPath(projectPath).resolve("manualConnections.json")

        /**
         * Returns the path to the 'bevel.db' SQLite database file within the private Bevel folder.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'bevel.db' file.
         */
        fun bevelDatabasePath(projectPath: String): Path {
            return privateFolderPath(projectPath).resolve("bevel.db")
        }

        /**
         * Returns the path to the root 'graph' folder within the public Bevel directory.
         * Creates the directory if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @return The path to the root 'graph' folder.
         */
        fun bevelRootGraphFolderPath(projectPath: String): Path = createDirectoryIfNotExists(publicFolderPath(projectPath).resolve("graph"))

        /**
         * Returns the path to a branch-specific graph folder within the root graph folder.
         * The branch name is sanitized to be filesystem-friendly.
         * Creates the directory if it doesn't exist.
         *
         * @param projectPath The root path of the project.
         * @param branch The name of the branch.
         * @return The path to the branch-specific graph folder.
         */
        fun bevelBranchGraphFolderPath(projectPath: String, branch: String): Path {
            val rootGraphPath = bevelRootGraphFolderPath(projectPath)

            sanitizeBranchName(branch)
            return rootGraphPath.resolve("graph_$branch")
        }

        /**
         * Returns the path to the 'VERSION' file within the base Bevel folder.
         * This file typically stores data version information.
         *
         * @param projectPath The root path of the project.
         * @return The path to the 'VERSION' file.
         */
        fun bevelDataVersion(projectPath: String): Path = baseFolderPath(projectPath).resolve("VERSION")

        /* UTILITIES */
        /**
         * Sanitizes a branch name to make it safe for use in file or directory names.
         * Replaces various special characters and whitespace with underscores.
         *
         * @param branchName The original branch name.
         * @return The sanitized branch name.
         */
        private fun sanitizeBranchName(branchName: String): String {
            return branchName.replace(Regex("[\\\\/:*?!.^()\"@&\$#<>|\\s]+"), "_")
        }
    }
}