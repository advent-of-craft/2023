package domain.yahtzee.constrain.input

import arrow.core.Either

class Roll private constructor(val dice: IntArray) {
    fun groupDieByFrequency(): Map<Int, Int> = dice.toList()
        .groupingBy { x -> x }
        .eachCount()

    fun toSortedString(): String =
        dice.sorted()
            .distinct()
            .joinToString("")

    companion object Parser {
        private const val ROLL_LENGTH = 5
        private const val MINIMUM_DIE = 1
        private const val MAXIMUM_DIE = 6

        fun from(dice: IntArray): Either<Error, Roll> =
            when {
                dice.hasInvalidLength() -> Either.Left(Error("Invalid dice... A roll should contain 6 dice."))
                dice.containsInvalidDie() -> Either.Left(Error("Invalid die value. Each die must be between 1 and 6."))
                else -> Either.Right(Roll(dice))
            }

        private fun IntArray.hasInvalidLength(): Boolean = size != ROLL_LENGTH
        private fun IntArray.containsInvalidDie(): Boolean = any { die -> die.isValid() }
        private fun Int.isValid(): Boolean = this < MINIMUM_DIE || this > MAXIMUM_DIE
    }
}