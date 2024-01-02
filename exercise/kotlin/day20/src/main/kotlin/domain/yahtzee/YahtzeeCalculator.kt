package domain.yahtzee

object YahtzeeCalculator {
    private const val ROLL_LENGTH = 5
    private const val MINIMUM_DIE = 1
    private const val MAXIMUM_DIE = 6

    fun number(dice: IntArray, number: Int): Int = calculate(
        { d -> d.filter { die -> die == number }.sum() }, dice
    )

    fun threeOfAKind(dice: IntArray): Int = calculateNOfAKind(dice, 3)
    fun fourOfAKind(dice: IntArray): Int = calculateNOfAKind(dice, 4)
    fun yahtzee(dice: IntArray): Int = calculate(
        { d -> if (hasNOfAKind(d, 5)) Scores.YAHTZEE_SCORE else 0 }, dice
    )

    private fun calculateNOfAKind(dice: IntArray, n: Int): Int = calculate(
        { d -> if (hasNOfAKind(d, n)) d.sum() else 0 }, dice
    )

    fun fullHouse(dice: IntArray): Int = calculate(
        { d ->
            groupDieByFrequency(d)
                .let { dieFrequency ->
                    if (dieFrequency.containsValue(3) && dieFrequency.containsValue(2)) Scores.HOUSE_SCORE
                    else 0
                }
        }, dice
    )

    fun largeStraight(dice: IntArray): Int = calculate({ d ->
        if (d.sorted()
                .windowed(2)
                .all { pair -> pair[0] + 1 == pair[1] }
        ) Scores.LARGE_STRAIGHT_SCORE else 0
    }, dice)

    fun smallStraight(dice: IntArray): Int = calculate({ d ->
        toSortedString(d)
            .let { diceString -> if (isSmallStraight(diceString)) 30 else 0 }
    }, dice)

    private fun isSmallStraight(diceString: String): Boolean =
        diceString.contains("1234") || diceString.contains("2345") || diceString.contains("3456")

    private fun hasNOfAKind(dice: IntArray, n: Int): Boolean = groupDieByFrequency(dice)
        .values
        .any { count -> count >= n }

    private fun toSortedString(dice: IntArray): String = dice.sorted()
        .distinct()
        .joinToString("")

    fun chance(dice: IntArray): Int = calculate(
        { d -> d.sum() }, dice
    )

    private fun groupDieByFrequency(dice: IntArray): Map<Int, Int> = dice.toList()
        .groupingBy { x -> x }
        .eachCount()

    private fun calculate(compute: (dice: IntArray) -> Int, dice: IntArray): Int {
        dice.validateRoll()
        return compute(dice)
    }

    private fun IntArray.validateRoll() {
        require(!hasInvalidLength()) { "Invalid dice... A roll should contain 6 dice." }
        require(!containsInvalidDie()) { "Invalid die value. Each die must be between 1 and 6." }
    }

    private fun IntArray.hasInvalidLength(): Boolean = size != ROLL_LENGTH
    private fun IntArray.containsInvalidDie(): Boolean = any { die -> die.isValid() }
    private fun Int.isValid(): Boolean = this < MINIMUM_DIE || this > MAXIMUM_DIE

    private object Scores {
        const val YAHTZEE_SCORE = 50
        const val HOUSE_SCORE = 25
        const val LARGE_STRAIGHT_SCORE = 40
    }
}