import M.A
import M.B
import M.K991_P
import M.f
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.File

// Original specifications available here : https://adventofcode.com/2021/day/2
class SubmarineTests : StringSpec({
    "should move on given instructions" {
        loadInstructions()
            .let {
                K991_P(B())
                    .toString(it)
                    .calculateResult() shouldBe 1_690_020
            }
    }
})

private fun K991_P.calculateResult(): Int = b.b * b.a

private fun loadInstructions(): List<A> =
    File(ClassLoader.getSystemResource("submarine.txt").file)
        .readLines()
        .map { it.f() }