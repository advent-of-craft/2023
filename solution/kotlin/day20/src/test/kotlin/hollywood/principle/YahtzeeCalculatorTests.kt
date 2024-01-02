package hollywood.principle

import builders.DiceBuilder.Companion.`🎲🎲🎲🎲🎲`
import domain.yahtzee.hollywood.principle.YahtzeeCalculator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import parameters.InvalidRoll
import parameters.NumberRollCase
import parameters.RollCase
import kotlin.test.fail

class YahtzeeCalculatorTests : FunSpec({
    context("add only numbers for numbers") {
        withData(
            NumberRollCase(`🎲🎲🎲🎲🎲`(1, 2, 1, 1, 3), 1, 3),
            NumberRollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 1, 0),
            NumberRollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 1, 0),
            NumberRollCase(`🎲🎲🎲🎲🎲`(4, 1, 4, 4, 5), 4, 12)
        ) { (dice, number, expectedResult) ->
            YahtzeeCalculator.number(dice.build(),
                number,
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("sum of all dice for 3 of a kind") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(3, 3, 3, 4, 5), 18),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 0),
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 20),
            RollCase(`🎲🎲🎲🎲🎲`(1, 1, 2, 1, 5), 10)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.threeOfAKind(
                dice.build(),
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("sum of all dice for 4 of a kind") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(3, 3, 3, 3, 5), 17),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 0),
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 20),
            RollCase(`🎲🎲🎲🎲🎲`(1, 1, 1, 1, 5), 9)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.fourOfAKind(
                dice.build(),
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("25 for full houses") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(2, 2, 3, 3, 3), 25),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 0),
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 1, 4, 1), 25)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.fullHouse(
                dice.build(),
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("30 for small straights") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(1, 2, 3, 4, 5), 30),
            RollCase(`🎲🎲🎲🎲🎲`(5, 4, 3, 2, 1), 30),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 1), 30),
            RollCase(`🎲🎲🎲🎲🎲`(1, 1, 1, 3, 2), 0)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.smallStraight(
                dice.build(),
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("40 for large straights") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(1, 2, 3, 4, 5), 40),
            RollCase(`🎲🎲🎲🎲🎲`(5, 4, 3, 2, 1), 40),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 40),
            RollCase(`🎲🎲🎲🎲🎲`(1, 4, 1, 3, 2), 0)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.largeStraight(
                dice.build(),
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("50 for yahtzees") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 50),
            RollCase(`🎲🎲🎲🎲🎲`(2, 2, 2, 2, 2), 50),
            RollCase(`🎲🎲🎲🎲🎲`(1, 4, 1, 3, 2), 0)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.yahtzee(
                dice.build(),
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("sum of all dice for chance") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(3, 3, 3, 3, 3), 15),
            RollCase(`🎲🎲🎲🎲🎲`(6, 5, 4, 3, 3), 21),
            RollCase(`🎲🎲🎲🎲🎲`(1, 4, 1, 3, 2), 11)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.chance(dice.build(),
                { score -> score shouldBe expectedResult },
                { fail() }
            )
        }
    }

    context("fail for invalid rolls") {
        withData(
            InvalidRoll(1),
            InvalidRoll(1, 6, 2),
            InvalidRoll(1, 6, 2, 5),
            InvalidRoll(1, 6, 2, 5, 4, 1),
            InvalidRoll(1, 6, 2, 5, 4, 1, 2)
        ) {
            it.dice
                .let { invalidRoll ->
                    YahtzeeCalculator.number(
                        invalidRoll,
                        1,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                    YahtzeeCalculator.threeOfAKind(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                    YahtzeeCalculator.fourOfAKind(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                    YahtzeeCalculator.fullHouse(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                    YahtzeeCalculator.smallStraight(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                    YahtzeeCalculator.largeStraight(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                    YahtzeeCalculator.yahtzee(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                    YahtzeeCalculator.chance(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid dice... A roll should contain 6 dice." })
                }
        }
    }

    context("fail for invalid die in roll") {
        withData(
            InvalidRoll(1, 1, 1, 1, 7),
            InvalidRoll(0, 1, 1, 1, 2),
            InvalidRoll(1, 1, -1, 1, 2)
        ) {
            it.dice
                .let { invalidRoll ->
                    YahtzeeCalculator.number(
                        invalidRoll,
                        1,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                    YahtzeeCalculator.threeOfAKind(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                    YahtzeeCalculator.fourOfAKind(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                    YahtzeeCalculator.fullHouse(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                    YahtzeeCalculator.smallStraight(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                    YahtzeeCalculator.largeStraight(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                    YahtzeeCalculator.yahtzee(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                    YahtzeeCalculator.chance(
                        invalidRoll,
                        { fail() },
                        { error -> error shouldBe "Invalid die value. Each die must be between 1 and 6." })
                }
        }
    }
})