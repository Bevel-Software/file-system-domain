package software.bevel.file_system_domain.web

import java.util.function.Consumer

interface WebResponse<CONTENT_TYPE>

interface Blockable<RESULT_TYPE> {
    fun block(): RESULT_TYPE?
}

interface Subscribable<RESULT_TYPE> {
    fun subscribe(consumer: Consumer<RESULT_TYPE>)

    fun subscribe()
}