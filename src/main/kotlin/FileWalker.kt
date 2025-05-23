package software.bevel.file_system_domain

interface FileWalker {
    fun walk(directory: String): List<String>
}