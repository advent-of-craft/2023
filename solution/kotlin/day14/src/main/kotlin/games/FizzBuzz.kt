package games

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

private const val MIN = 0
private const val MAX = 100
private const val FIZZBUZZ = 15
private const val FIZZ = 3
private const val BUZZ = 5

object FizzBuzz {
    fun convert(input: Int): Option<String> = when {
        isOutOfRange(input) -> None
        else -> Some(convertSafely(input))
    }

    private fun convertSafely(input: Int): String = when {
        `is`(FIZZBUZZ, input) -> "FizzBuzz"
        `is`(FIZZ, input) -> "Fizz"
        `is`(BUZZ, input) -> "Buzz"
        else -> input.toString()
    }

    private fun `is`(divisor: Int, input: Int): Boolean = input % divisor == 0
    private fun isOutOfRange(input: Int) = input <= MIN || input > MAX
}