package software.bevel.file_system_domain.web

import java.util.function.Consumer

/**
 * A marker interface for web responses.
 * Implementations of this interface represent the response received from a web request.
 *
 * @param CONTENT_TYPE The type of the content held by the response.
 */
interface WebResponse<CONTENT_TYPE>

/**
 * Represents an operation that can be blocked on to retrieve a result.
 *
 * @param RESULT_TYPE The type of the result returned by the blocking operation.
 */
interface Blockable<RESULT_TYPE> {
    /**
     * Blocks the current thread until the result of the operation is available, then returns it.
     *
     * @return The result of type [RESULT_TYPE], or `null` if the operation does not produce a result or times out (behavior may vary by implementation).
     */
    fun block(): RESULT_TYPE?
}

/**
 * Represents an operation or data source that can be subscribed to for updates.
 *
 * @param RESULT_TYPE The type of the data/events that subscribers will receive.
 */
interface Subscribable<RESULT_TYPE> {
    /**
     * Subscribes a consumer to receive updates or events.
     *
     * @param consumer The [Consumer] that will process items of type [RESULT_TYPE].
     */
    fun subscribe(consumer: Consumer<RESULT_TYPE>)

    /**
     * Subscribes to the operation or data source without a specific consumer.
     * This might be used to trigger the operation or for side effects, depending on the implementation.
     */
    fun subscribe()
}