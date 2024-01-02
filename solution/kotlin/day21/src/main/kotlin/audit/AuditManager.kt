package audit

import java.lang.System.lineSeparator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern


class AuditManager(private val maxEntriesPerFile: Int) {
    fun addRecord(files: List<FileContent>, visitorName: String, timeOfVisit: LocalDateTime): FileUpdate {
        val sortedFiles: List<FileContent> = sortByIndex(files)
        val newRecord = newRecord(visitorName, timeOfVisit)

        return if (sortedFiles.isEmpty()) createANewFile(sortedFiles, newRecord)
        else createNewFileOrUpdate(
            sortedFiles,
            newRecord
        )
    }

    private fun createNewFileOrUpdate(sortedFiles: List<FileContent>, newRecord: String): FileUpdate =
        sortedFiles
            .last()
            .let { currentFile ->
                return if (canAppendToExistingFile(currentFile))
                    appendToExistingFile(
                        newRecord,
                        currentFile
                    )
                else createANewFile(sortedFiles, newRecord)
            }

    private fun sortByIndex(files: List<FileContent>): List<FileContent> =
        files.sortedBy { it.index() }

    private fun canAppendToExistingFile(currentFile: FileContent): Boolean {
        return currentFile.lines.size < maxEntriesPerFile
    }

    companion object {
        private val DATE_TIME_FORMATTER = ofPattern("yyyy-MM-dd HH:mm:ss")
        private const val AUDIT = "audit_"

        private fun createANewFile(sortedFiles: List<FileContent>, newRecord: String): FileUpdate =
            FileUpdate(
                auditFileName(sortedFiles.size + 1),
                newRecord
            )

        private fun newRecord(visitorName: String, timeOfVisit: LocalDateTime): String =
            "$visitorName;${timeOfVisit.format(DATE_TIME_FORMATTER)}"

        private fun auditFileName(index: Int): String = "$AUDIT$index.txt"

        private fun appendToExistingFile(newRecord: String, currentFile: FileContent): FileUpdate =
            FileUpdate(
                currentFile.fileName,
                (currentFile.lines + newRecord).joinToString(lineSeparator())
            )
    }
}