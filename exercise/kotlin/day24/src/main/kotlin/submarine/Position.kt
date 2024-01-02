package submarine

data class Position(val horizontal: Int = 0, val depth: Int = 0) {
    fun changeDepth(newDepth: Int): Position = copy(depth = newDepth)
    fun moveHorizontally(newHorizontal: Int): Position = copy(horizontal = newHorizontal)
}