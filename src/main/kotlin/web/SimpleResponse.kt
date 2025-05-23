package software.bevel.file_system_domain.web

class SimpleResponse<T>(
    val response: T
): WebResponse<T>