import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.forAll
import roman.numerals.RomanNumerals

class RomanProperties : StringSpec({
    "returns only valid symbols for valid input" {
        forAll(Arb.int(1..3999)) { input ->
            RomanNumerals.convert(input).isSome { roman -> roman.matches("[IVXLCDM]+".toRegex()) }
        }
    }

    "returns non for invalid input" {
        forAll(Arb.int().filter { i -> i < 1 || i > 3999 }) { invalidInput ->
            RomanNumerals.convert(invalidInput).isNone()
        }
    }
})