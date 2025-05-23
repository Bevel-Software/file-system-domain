package software.bevel.file_system_domain.services

interface CachedFileHandler: FileHandler {
    fun clearCache()
}