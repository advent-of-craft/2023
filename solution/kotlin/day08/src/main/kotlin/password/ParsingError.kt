package password

data class ParsingError(val reason: String) {
    companion object {
        fun from(reason: String): ParsingError {
            return ParsingError(reason)
        }
    }
}