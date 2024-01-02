package audit

data class FileContent(val fileName: String, val lines: List<String>) {
    fun index(): Int = fileName.split("_")[1].split(".")[0].toInt()
}