package games

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

const val MIN = 1
const val MAX = 100

object FizzBuzz {
    fun convert(input: Int): Option<String> = when {
        isOutOfRange(input) -> None
        else -> Some(convertSafely(input))
    }

    private fun convertSafely(input: Int): String = when {
        `is`(15, input) -> "FizzBuzz"
        `is`(3, input) -> "Fizz"
        `is`(5, input) -> "Buzz"
        else -> input.toString()
    }

    private fun `is`(divisor: Int, input: Int): Boolean = input % divisor == 0
    private fun isOutOfRange(input: Int) = input < MIN || input > MAX
}