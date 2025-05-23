package software.bevel.file_system_domain.web

import java.io.Closeable

/**
 * Defines a contract for a local communication channel.
 * This interface allows sending messages and checking the connection status.
 * It extends [Closeable] to ensure resources can be released when the interface is no longer needed.
 */
interface LocalCommunicationInterface: Closeable {
    /**
     * Sends a string message and expects a string response.
     *
     * @param message The string message to send.
     * @return The string response received.
     */
    fun send(message: String): String

    /**
     * Sends a generic message of type [T] and expects a string response.
     * The message object will typically be serialized (e.g., to JSON) before sending.
     *
     * @param T The type of the message to send.
     * @param message The message object of type [T] to send.
     * @return The string response received.
     */
    fun<T> send(message: T): String

    /**
     * Sends a string message without expecting or waiting for a response.
     * This is useful for fire-and-forget type messages.
     *
     * @param message The string message to send.
     */
    fun sendWithoutResponse(message: String)

    /**
     * Checks if the communication interface is currently connected and active.
     *
     * @return `true` if connected, `false` otherwise.
     */
    fun isConnected(): Boolean
}