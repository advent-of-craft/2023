package audit

interface FileSystem {
    fun getFiles(directoryName: String): List<String>
    fun writeAllText(filePath: String, content: String)
    fun readAllLines(filePath: String): List<String>
}