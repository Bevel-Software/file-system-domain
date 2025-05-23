package software.bevel.file_system_domain.web

import java.io.Closeable

interface LocalCommunicationInterface: Closeable {
    fun send(message: String): String

    fun<T> send(message: T): String

    fun sendWithoutResponse(message: String)

    fun isConnected(): Boolean
}