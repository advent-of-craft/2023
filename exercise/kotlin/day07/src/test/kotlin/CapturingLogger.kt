import ci.Logger


data class CapturingLogger(val lines: MutableList<String> = mutableListOf()) : Logger {
    override fun info(message: String) {
        lines.add("INFO: $message")
    }

    override fun error(message: String) {
        lines.add("ERROR: $message")
    }
}