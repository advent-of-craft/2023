package domain.yahtzee.constrain.input

object YahtzeeCalculator {
    fun number(roll: Roll, number: Int): Int = calculate(
        { r -> r.dice.filter { die -> die == number }.sum() }, roll
    )

    fun threeOfAKind(roll: Roll): Int = calculateNOfAKind(roll, 3)
    fun fourOfAKind(roll: Roll): Int = calculateNOfAKind(roll, 4)
    fun yahtzee(roll: Roll): Int = calculate(
        { r -> if (hasNOfAKind(r, 5)) Scores.YAHTZEE_SCORE else 0 }, roll
    )

    private fun calculateNOfAKind(roll: Roll, n: Int): Int = calculate(
        { r -> if (hasNOfAKind(r, n)) r.dice.sum() else 0 }, roll
    )

    fun fullHouse(roll: Roll): Int = calculate(
        { r ->
            r.groupDieByFrequency()
                .let { dieFrequency ->
                    if (dieFrequency.containsValue(3) && dieFrequency.containsValue(2)) Scores.HOUSE_SCORE
                    else 0
                }
        }, roll
    )

    fun largeStraight(roll: Roll): Int = calculate(
        { r ->
            if (r.dice.sorted()
                    .windowed(2)
                    .all { pair -> pair[0] + 1 == pair[1] }
            ) Scores.LARGE_STRAIGHT_SCORE else 0
        }, roll
    )

    fun smallStraight(roll: Roll): Int = calculate({ r ->
        r.toSortedString()
            .let { diceString -> if (isSmallStraight(diceString)) 30 else 0 }
    }, roll)

    private fun isSmallStraight(diceString: String): Boolean =
        diceString.contains("1234") || diceString.contains("2345") || diceString.contains("3456")

    private fun hasNOfAKind(roll: Roll, n: Int): Boolean =
        roll.groupDieByFrequency()
            .values
            .any { count -> count >= n }

    fun chance(roll: Roll): Int = calculate(
        { r -> r.dice.sum() }, roll
    )

    private fun calculate(compute: (roll: Roll) -> Int, roll: Roll): Int = compute(roll)

    private object Scores {
        const val YAHTZEE_SCORE = 50
        const val HOUSE_SCORE = 25
        const val LARGE_STRAIGHT_SCORE = 40
    }
}