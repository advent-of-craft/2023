package roman.numerals

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

object RomanNumerals {
    private const val MAX_NUMBER = 3999
    private fun romanMapping() = mapOf(
        1000 to "M",
        900 to "CM",
        500 to "D",
        400 to "CD",
        100 to "C",
        90 to "XC",
        50 to "L",
        40 to "XL",
        10 to "X",
        9 to "IX",
        5 to "V",
        4 to "IV",
        1 to "I"
    )

    fun convert(number: Int): Option<String> = if (isInRange(number)) Some(convertSafely(number)) else None
    private fun isInRange(number: Int): Boolean = number in 1..MAX_NUMBER
    private fun convertSafely(number: Int): String = romanMapping().let {
        var roman = ""
        var remaining = number

        it.forEach { (key, value) ->
            while (remaining >= key) {
                roman += value
                remaining -= key
            }
        }
        return roman
    }
}