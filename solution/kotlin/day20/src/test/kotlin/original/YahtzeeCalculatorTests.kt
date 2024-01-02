package original

import builders.DiceBuilder.Companion.`🎲🎲🎲🎲🎲`
import domain.yahtzee.original.YahtzeeCalculator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import parameters.InvalidRoll
import parameters.NumberRollCase
import parameters.RollCase

class YahtzeeCalculatorTests : FunSpec({
    context("add only numbers for numbers") {
        withData(
            NumberRollCase(`🎲🎲🎲🎲🎲`(1, 2, 1, 1, 3), 1, 3),
            NumberRollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 1, 0),
            NumberRollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 1, 0),
            NumberRollCase(`🎲🎲🎲🎲🎲`(4, 1, 4, 4, 5), 4, 12)
        ) { (dice, number, expectedResult) ->
            YahtzeeCalculator.number(dice.build(), number) shouldBe expectedResult
        }
    }

    context("sum of all dice for 3 of a kind") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(3, 3, 3, 4, 5), 18),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 0),
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 20),
            RollCase(`🎲🎲🎲🎲🎲`(1, 1, 2, 1, 5), 10)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.threeOfAKind(dice.build()) shouldBe expectedResult
        }
    }

    context("sum of all dice for 4 of a kind") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(3, 3, 3, 3, 5), 17),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 0),
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 20),
            RollCase(`🎲🎲🎲🎲🎲`(1, 1, 1, 1, 5), 9)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.fourOfAKind(dice.build()) shouldBe expectedResult
        }
    }

    context("25 for full houses") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(2, 2, 3, 3, 3), 25),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 0),
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 1, 4, 1), 25)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.fullHouse(dice.build()) shouldBe expectedResult
        }
    }

    context("30 for small straights") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(1, 2, 3, 4, 5), 30),
            RollCase(`🎲🎲🎲🎲🎲`(5, 4, 3, 2, 1), 30),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 1), 30),
            RollCase(`🎲🎲🎲🎲🎲`(1, 1, 1, 3, 2), 0)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.smallStraight(dice.build()) shouldBe expectedResult
        }
    }

    context("40 for large straights") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(1, 2, 3, 4, 5), 40),
            RollCase(`🎲🎲🎲🎲🎲`(5, 4, 3, 2, 1), 40),
            RollCase(`🎲🎲🎲🎲🎲`(2, 3, 4, 5, 6), 40),
            RollCase(`🎲🎲🎲🎲🎲`(1, 4, 1, 3, 2), 0)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.largeStraight(dice.build()) shouldBe expectedResult
        }
    }

    context("50 for yahtzees") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(4, 4, 4, 4, 4), 50),
            RollCase(`🎲🎲🎲🎲🎲`(2, 2, 2, 2, 2), 50),
            RollCase(`🎲🎲🎲🎲🎲`(1, 4, 1, 3, 2), 0)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.yahtzee(dice.build()) shouldBe expectedResult
        }
    }

    context("sum of all dice for chance") {
        withData(
            RollCase(`🎲🎲🎲🎲🎲`(3, 3, 3, 3, 3), 15),
            RollCase(`🎲🎲🎲🎲🎲`(6, 5, 4, 3, 3), 21),
            RollCase(`🎲🎲🎲🎲🎲`(1, 4, 1, 3, 2), 11)
        ) { (dice, expectedResult) ->
            YahtzeeCalculator.chance(dice.build()) shouldBe expectedResult
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
                .let {
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.number(it, 1) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.threeOfAKind(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.fourOfAKind(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.fullHouse(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.smallStraight(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.largeStraight(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.yahtzee(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.chance(it) }
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
                .let {
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.number(it, 1) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.threeOfAKind(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.fourOfAKind(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.fullHouse(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.smallStraight(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.largeStraight(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.yahtzee(it) }
                    shouldThrow<IllegalArgumentException> { YahtzeeCalculator.chance(it) }
                }
        }
    }
})