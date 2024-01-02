package audit

class AddRecordUseCase(
    private val directory: String,
    private val auditManager: AuditManager,
    private val persister: Persister
) {
    fun handle(addNewVisitor: AddNewVisitor) {
        val files = persister.readDirectory(directory)
        val update = auditManager.addRecord(files, addNewVisitor.visitor, addNewVisitor.time)

        persister.applyUpdate(directory, update)
    }
}