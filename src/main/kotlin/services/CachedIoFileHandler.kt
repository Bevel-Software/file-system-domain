package software.bevel.file_system_domain.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.util.LinkedList

class CachedIoFileHandler(
    private val logger: Logger = LoggerFactory.getLogger(CachedIoFileHandler::class.java)
): CachedFileHandler, IoFileHandler() {
    companion object {
        // Maximum number of files to cache
        private const val MAX_CACHE_SIZE = 10
    }

    // Cache structures to store file content
    private val fileContentCache = mutableMapOf<String, Pair<String, Long>>() // filePath -> (content, lastModified)
    private val fileLinesCache = mutableMapOf<String, Pair<List<String>, Long>>() // filePath -> (lines, lastModified)
    private val extensionCache = mutableMapOf<String, String>() // Simple cache for file extensions
    
    // FIFO queues to track insertion order
    private val contentCacheQueue = LinkedList<String>() // Queue of file paths in order of insertion
    private val linesCacheQueue = LinkedList<String>() // Queue of file paths in order of insertion

    override fun clearCache() {
        fileContentCache.clear()
        fileLinesCache.clear()
        extensionCache.clear()
        contentCacheQueue.clear()
        linesCacheQueue.clear()
        logger.debug("File cache cleared")
    }

    override fun readString(filePath: String): String {
        val file = File(filePath)
        if(!file.exists() || !file.isFile) {
            logger.error("Could not readString from file: $filePath")
            return ""
        }
        
        val lastModified = file.lastModified()
        val cached = fileContentCache[filePath]
        
        return if (cached != null && cached.second == lastModified) {
            // Cache hit
            cached.first
        } else {
            // Cache miss or outdated
            val content = super<IoFileHandler>.readString(filePath)
            
            // If the file is already in the cache, remove it from the queue to add it again later (refresh position)
            if (fileContentCache.containsKey(filePath)) {
                contentCacheQueue.remove(filePath)
            }
            
            // Check if cache limit is reached and evict oldest entry if needed
            if (contentCacheQueue.size >= MAX_CACHE_SIZE && !fileContentCache.containsKey(filePath)) {
                val oldestFilePath = contentCacheQueue.poll()
                oldestFilePath?.let {
                    fileContentCache.remove(it)
                    logger.debug("Evicted oldest content cache entry: $it")
                }
            }
            
            // Add to cache and queue
            fileContentCache[filePath] = Pair(content, lastModified)
            contentCacheQueue.add(filePath)
            
            content
        }
    }

    override fun readLines(filePath: String): List<String> {
        val file = File(filePath)
        if(!file.exists() || !file.isFile) {
            logger.error("Could not readLines from file: $filePath")
            return listOf()
        }
        
        val lastModified = file.lastModified()
        val cached = fileLinesCache[filePath]
        
        return if (cached != null && cached.second == lastModified) {
            // Cache hit
            cached.first
        } else {
            // Cache miss or outdated
            val lines = super<IoFileHandler>.readLines(filePath)
            
            // If the file is already in the cache, remove it from the queue to add it again later (refresh position)
            if (fileLinesCache.containsKey(filePath)) {
                linesCacheQueue.remove(filePath)
            }
            
            // Check if cache limit is reached and evict oldest entry if needed
            if (linesCacheQueue.size >= MAX_CACHE_SIZE && !fileLinesCache.containsKey(filePath)) {
                val oldestFilePath = linesCacheQueue.poll()
                oldestFilePath?.let {
                    fileLinesCache.remove(it)
                    logger.debug("Evicted oldest lines cache entry: $it")
                }
            }
            
            // Add to cache and queue
            fileLinesCache[filePath] = Pair(lines, lastModified)
            linesCacheQueue.add(filePath)
            
            lines
        }
    }
    
    override fun getExtensionFromPath(filePath: String): String {
        // Simple cache lookup
        extensionCache[filePath]?.let { return it }
        
        // Cache miss - compute and cache the result
        val extension = super.getExtensionFromPath(filePath)
        extensionCache[filePath] = extension
        return extension
    }
    
    override fun writeString(filePath: String, content: String) {
        super.writeString(filePath, content)
        
        // Invalidate cache for this file
        fileContentCache.remove(filePath)
        contentCacheQueue.remove(filePath)
        fileLinesCache.remove(filePath)
        linesCacheQueue.remove(filePath)
        extensionCache.remove(filePath)
    }
    
    override fun delete(filePath: String) {
        super.delete(filePath)
        
        // Invalidate cache
        fileContentCache.remove(filePath)
        contentCacheQueue.remove(filePath)
        fileLinesCache.remove(filePath)
        linesCacheQueue.remove(filePath)
        extensionCache.remove(filePath)
    }
}