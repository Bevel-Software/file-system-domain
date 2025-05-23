package software.bevel.file_system_domain.web

/**
 * Defines a contract for a web client capable of sending HTTP requests.
 *
 * @param RESPONSE_TYPE The type of the response wrapper expected from non-blocking requests, must implement [WebResponse] of String.
 */
interface WebClient<RESPONSE_TYPE: WebResponse<String>> {

    /**
     * Sends an HTTP POST request to the specified URL.
     * This method is typically non-blocking or returns a future/deferred response.
     *
     * @param url The URL to send the POST request to.
     * @param body The body of the POST request.
     * @param headers A list of HTTP headers to include in the request (optional).
     * @param parameters A list of URL query parameters to include in the request (optional).
     * @return A [RESPONSE_TYPE] object wrapping the server's response.
     */
    fun sendPostRequest(url: String, body: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): RESPONSE_TYPE

    /**
     * Sends an HTTP POST request to the specified URL and blocks until the response is received.
     *
     * @param url The URL to send the POST request to.
     * @param body The body of the POST request.
     * @param headers A list of HTTP headers to include in the request (optional).
     * @param parameters A list of URL query parameters to include in the request (optional).
     * @return The raw string response body from the server.
     */
    fun sendPostRequestBlocking(url: String, body: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): String

    /**
     * Sends an HTTP GET request to the specified URL.
     * This method is typically non-blocking or returns a future/deferred response.
     *
     * @param url The URL to send the GET request to.
     * @param headers A list of HTTP headers to include in the request (optional).
     * @param parameters A list of URL query parameters to include in the request (optional).
     * @return A [RESPONSE_TYPE] object wrapping the server's response.
     */
    fun sendGetRequest(url: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): RESPONSE_TYPE

    /**
     * Sends an HTTP GET request to the specified URL and blocks until the response is received.
     *
     * @param url The URL to send the GET request to.
     * @param headers A list of HTTP headers to include in the request (optional).
     * @param parameters A list of URL query parameters to include in the request (optional).
     * @return The raw string response body from the server.
     */
    fun sendGetRequestBlocking(url: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): String
}