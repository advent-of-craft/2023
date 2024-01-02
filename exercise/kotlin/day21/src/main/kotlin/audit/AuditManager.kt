package audit

import java.lang.System.lineSeparator
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

class AuditManager(
    private val maxEntriesPerFile: Int,
    private val directoryName: String,
    private val fileSystem: FileSystem
) {
    fun addRecord(visitorName: String, timeOfVisit: LocalDateTime) {
        val filePaths = fileSystem.getFiles(directoryName)
        val sorted = sortByIndex(filePaths)
        val dateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss")
        val newRecord = visitorName + ";" + timeOfVisit.format(dateTimeFormatter)

        if (sorted.isEmpty()) {
            val newFile = Paths.get(directoryName, "audit_1.txt").toString()
            fileSystem.writeAllText(newFile, newRecord)
            return
        }

        val currentFileIndex = sorted.size - 1
        val currentFilePath = sorted[currentFileIndex]
        val lines: List<String> = fileSystem.readAllLines(currentFilePath)

        if (lines.size < maxEntriesPerFile) {
            val newContent = (lines + newRecord).joinToString(lineSeparator())
            fileSystem.writeAllText(currentFilePath, newContent)
        } else {
            val newName = "audit_${currentFileIndex + 2}.txt"
            val newFile = Paths.get(directoryName, newName).toString()
            fileSystem.writeAllText(newFile, newRecord)
        }
    }

    private fun sortByIndex(filePaths: List<String>): List<String> = filePaths.sorted()
}
