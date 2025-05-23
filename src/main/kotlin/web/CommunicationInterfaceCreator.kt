package software.bevel.file_system_domain.web

/**
 * A generic factory interface for creating instances of [LocalCommunicationInterface].
 *
 * @param T The type of [LocalCommunicationInterface] that this creator produces.
 */
interface CommunicationInterfaceCreator<T: LocalCommunicationInterface> {
    /**
     * Creates and returns an instance of [T].
     *
     * @return A new instance of [T].
     */
    fun create(): T
}