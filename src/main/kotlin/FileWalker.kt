package software.bevel.file_system_domain

/**
 * Interface for file walking operations.
 * Implementations of this interface are responsible for traversing a directory structure.
 */
interface FileWalker {
    /**
     * Walks the specified directory and returns a list of file paths.
     *
     * @param directory The path to the directory to walk.
     * @return A list of strings, where each string is a path to a file found within the directory.
     */
    fun walk(directory: String): List<String>
}