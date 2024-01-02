package submarine

data class Instruction(val text: String, val x: Int)

fun String.toInstruction(): Instruction = split(" ".toRegex()).dropLastWhile { it.isEmpty() }
    .let { split ->
        return Instruction(split[0], split[1].toInt())
    }