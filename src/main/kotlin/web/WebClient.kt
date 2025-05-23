package software.bevel.file_system_domain.web

interface WebClient<RESPONSE_TYPE: WebResponse<String>> {

    fun sendPostRequest(url: String, body: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): RESPONSE_TYPE

    fun sendPostRequestBlocking(url: String, body: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): String

    fun sendGetRequest(url: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): RESPONSE_TYPE

    fun sendGetRequestBlocking(url: String, headers: List<Pair<String, String>> = listOf(), parameters: List<Pair<String, String>> = listOf()): String
}