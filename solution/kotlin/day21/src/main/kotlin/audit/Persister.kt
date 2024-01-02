package audit

import java.nio.file.Path
import kotlin.io.path.*

class Persister {
    fun readDirectory(directory: String): List<FileContent> =
        Path(directory)
            .listDirectoryEntries()
            .filter { it.isRegularFile() }
            .map { readFile(it) }

    fun applyUpdate(directory: String, update: FileUpdate) {
        Path(directory, update.fileName)
            .writeText(update.newContent)
    }

    private fun readFile(file: Path): FileContent =
        FileContent(file.name, file.readLines())
}

