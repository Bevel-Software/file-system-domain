package software.bevel.file_system_domain.services

/**
 * An extension of the [FileHandler] interface that adds caching capabilities.
 * Implementations of this interface should provide a way to manage cached file content.
 */
interface CachedFileHandler: FileHandler {
    /**
     * Clears any cached file content.
     * After calling this method, subsequent requests for file content should fetch fresh data from the source.
     */
    fun clearCache()
}