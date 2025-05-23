package software.bevel.file_system_domain.web

interface CommunicationInterfaceCreator<T: LocalCommunicationInterface> {
    fun create(): T
}