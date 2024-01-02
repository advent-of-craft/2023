import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import roman.numerals.RomanNumerals

data class TestCase(val input: Int, val expectedResult: String)

class RomanNumeralsTests : FunSpec({
    context("returns its numbers representation") {
        withData(
            TestCase(1, "I"),
            TestCase(3, "III"),
            TestCase(4, "IV"),
            TestCase(5, "V"),
            TestCase(10, "X"),
            TestCase(13, "XIII"),
            TestCase(50, "L"),
            TestCase(100, "C"),
            TestCase(500, "D"),
            TestCase(1000, "M"),
            TestCase(2499, "MMCDXCIX")
        ) { (input, expectedResult) ->
            RomanNumerals.convert(input) shouldBeSome expectedResult
        }
    }
})