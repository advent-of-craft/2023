package domain.yahtzee.hollywood.principle

object YahtzeeCalculator {
    private const val ROLL_LENGTH = 5
    private const val MINIMUM_DIE = 1
    private const val MAXIMUM_DIE = 6

    fun number(
        dice: IntArray,
        number: Int,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculate(
        { d -> d.filter { die -> die == number }.sum() }, dice, onSuccess, onError
    )

    fun threeOfAKind(
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculateNOfAKind(dice, 3, onSuccess, onError)

    fun fourOfAKind(
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculateNOfAKind(dice, 4, onSuccess, onError)

    fun yahtzee(
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculate(
        { d -> if (hasNOfAKind(d, 5)) Scores.YAHTZEE_SCORE else 0 }, dice, onSuccess, onError
    )

    private fun calculateNOfAKind(
        dice: IntArray,
        n: Int,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculate(
        { d -> if (hasNOfAKind(d, n)) d.sum() else 0 }, dice, onSuccess, onError
    )

    fun fullHouse(
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculate(
        { d ->
            groupDieByFrequency(d)
                .let { dieFrequency ->
                    if (dieFrequency.containsValue(3) && dieFrequency.containsValue(2)) Scores.HOUSE_SCORE
                    else 0
                }
        }, dice, onSuccess, onError
    )

    fun largeStraight(
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculate(
        { d ->
            if (d.sorted()
                    .windowed(2)
                    .all { pair -> pair[0] + 1 == pair[1] }
            ) Scores.LARGE_STRAIGHT_SCORE else 0
        }, dice, onSuccess, onError
    )

    fun smallStraight(
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculate(
        { d ->
            toSortedString(d)
                .let { diceString -> if (isSmallStraight(diceString)) 30 else 0 }
        }, dice, onSuccess, onError
    )

    private fun isSmallStraight(diceString: String): Boolean =
        diceString.contains("1234") || diceString.contains("2345") || diceString.contains("3456")

    private fun hasNOfAKind(dice: IntArray, n: Int): Boolean = groupDieByFrequency(dice)
        .values
        .any { count -> count >= n }

    private fun toSortedString(dice: IntArray): String = dice.sorted()
        .distinct()
        .joinToString("")

    fun chance(
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ): Unit = calculate(
        { d -> d.sum() }, dice, onSuccess, onError
    )

    private fun groupDieByFrequency(dice: IntArray): Map<Int, Int> = dice.toList()
        .groupingBy { x -> x }
        .eachCount()

    private fun calculate(
        compute: (dice: IntArray) -> Int,
        dice: IntArray,
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit
    ) {
        if (dice.validateRoll(onError))
            onSuccess(compute(dice))
    }

    private fun IntArray.validateRoll(onError: (error: String) -> Unit): Boolean = when {
        hasInvalidLength() -> {
            onError("Invalid dice... A roll should contain 6 dice.")
            false
        }

        containsInvalidDie() -> {
            onError("Invalid die value. Each die must be between 1 and 6.")
            false
        }

        else -> true
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