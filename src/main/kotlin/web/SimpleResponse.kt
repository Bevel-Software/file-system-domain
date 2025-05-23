package software.bevel.file_system_domain.web

/**
 * A simple generic wrapper class for a web response.
 * It holds a response of type [T] and implements the [WebResponse] interface.
 *
 * @param T The type of the actual response data being wrapped.
 * @property response The actual response data of type [T].
 */
class SimpleResponse<T>(
    val response: T
): WebResponse<T>