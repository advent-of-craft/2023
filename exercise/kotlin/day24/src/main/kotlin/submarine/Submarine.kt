package submarine

class Submarine(val position: Position) {
    fun move(instructions: List<Instruction>): Submarine = instructions
        .fold(this) { submarine, instruction -> submarine.move(instruction) }

    private fun move(instruction: Instruction): Submarine = Submarine(
        when (instruction.text) {
            "down" -> position.changeDepth(position.depth + instruction.x)
            "up" -> position.changeDepth(position.depth - instruction.x)
            else -> position.moveHorizontally(position.horizontal + instruction.x)
        }
    )
}