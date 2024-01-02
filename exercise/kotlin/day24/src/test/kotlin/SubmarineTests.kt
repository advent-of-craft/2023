import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import submarine.Instruction
import submarine.Position
import submarine.Submarine
import submarine.toInstruction
import java.io.File

// Original specifications available here : https://adventofcode.com/2021/day/2
class SubmarineTests : StringSpec({
    "should move on given instructions" {
        loadInstructions()
            .let {
                Submarine(Position())
                    .move(it)
                    .calculateResult() shouldBe 1_690_020
            }
    }
})

private fun Submarine.calculateResult(): Int = position.depth * position.horizontal

private fun loadInstructions(): List<Instruction> =
    File(ClassLoader.getSystemResource("submarine.txt").file)
        .readLines()
        .map { it.toInstruction() }