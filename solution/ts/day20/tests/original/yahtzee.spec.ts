import {YahtzeeCalculator} from '../../src/domain/yahtzee/original/yahtzeeCalculator';
import {DiceBuilder} from '../diceBuilder';

describe('yahtzee calculate', () => {
    test.each([
        [DiceBuilder.newRoll(1, 2, 1, 1, 3), 1, 3],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 1, 0],
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 1, 0],
        [DiceBuilder.newRoll(4, 1, 4, 4, 5), 4, 12]
    ])('sum of dice for number : %s fo number %s -> %s', (dice, number, expectedResult) =>
        expect(YahtzeeCalculator.number(dice.build(), number)).toBe(expectedResult));

    test.each([
        [DiceBuilder.newRoll(3, 3, 3, 4, 5), 18],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 0],
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 20],
        [DiceBuilder.newRoll(1, 1, 2, 1, 5), 10]
    ])('three of a kind : %s -> %s', (dice, expectedResult) =>
        expect(YahtzeeCalculator.threeOfAKind(dice.build())).toBe(expectedResult));

    test.each([
        [DiceBuilder.newRoll(3, 3, 3, 3, 5), 17],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 0],
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 20],
        [DiceBuilder.newRoll(1, 1, 1, 1, 5), 9]
    ])('four of a kind : %s -> %s', (dice, expectedResult) =>
        expect(YahtzeeCalculator.fourOfAKind(dice.build())).toBe(expectedResult));

    test.each([
        [DiceBuilder.newRoll(2, 2, 3, 3, 3), 25],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 0],
        [DiceBuilder.newRoll(4, 4, 1, 4, 1), 25]
    ])('25 for full house : %s -> %s', (dice, expectedResult) =>
        expect(YahtzeeCalculator.fullHouse(dice.build())).toBe(expectedResult));

    test.each([
        [DiceBuilder.newRoll(1, 2, 3, 4, 5), 30],
        [DiceBuilder.newRoll(5, 4, 3, 2, 1), 30],
        [DiceBuilder.newRoll(2, 3, 4, 5, 1), 30],
        [DiceBuilder.newRoll(1, 1, 1, 3, 2), 0]
    ])('30 for small straight : %s -> %s', (dice, expectedResult) =>
        expect(YahtzeeCalculator.smallStraight(dice.build())).toBe(expectedResult));

    test.each([
        [DiceBuilder.newRoll(1, 2, 3, 4, 5), 40],
        [DiceBuilder.newRoll(5, 4, 3, 2, 1), 40],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 40],
        [DiceBuilder.newRoll(1, 4, 1, 3, 2), 0]
    ])('30 for large straight : %s -> %s', (dice, expectedResult) =>
        expect(YahtzeeCalculator.largeStraight(dice.build())).toBe(expectedResult));

    test.each([
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 50],
        [DiceBuilder.newRoll(2, 2, 2, 2, 2), 50],
        [DiceBuilder.newRoll(1, 4, 1, 3, 2), 0]
    ])('50 for yahtzee : %s -> %s', (dice, expectedResult) =>
        expect(YahtzeeCalculator.yahtzee(dice.build())).toBe(expectedResult));

    test.each([
        [DiceBuilder.newRoll(3, 3, 3, 3, 3), 15],
        [DiceBuilder.newRoll(6, 5, 4, 3, 3), 21],
        [DiceBuilder.newRoll(1, 4, 1, 3, 2), 11]
    ])('sum of dice for chance : %s -> %s', (dice, expectedResult) =>
        expect(YahtzeeCalculator.chance(dice.build())).toBe(expectedResult));

    describe('fail for', () => {
        test.each([
            [[1]],
            [[1, 1]],
            [[1, 6, 2]],
            [[1, 6, 2, 5]],
            [[1, 6, 2, 5, 4, 1]],
            [[1, 6, 2, 5, 4, 1, 2]]
        ])('invalid roll lengths for %s', (dice) => {
            assertInvalidRollLength(() => YahtzeeCalculator.number(dice, 1));
            assertInvalidRollLength(() => YahtzeeCalculator.threeOfAKind(dice));
            assertInvalidRollLength(() => YahtzeeCalculator.fourOfAKind(dice));
            assertInvalidRollLength(() => YahtzeeCalculator.fullHouse(dice));
            assertInvalidRollLength(() => YahtzeeCalculator.smallStraight(dice));
            assertInvalidRollLength(() => YahtzeeCalculator.largeStraight(dice));
            assertInvalidRollLength(() => YahtzeeCalculator.yahtzee(dice));
            assertInvalidRollLength(() => YahtzeeCalculator.chance(dice));
        });

        const assertInvalidRollLength = (calculate: () => number) =>
            expect(() => calculate()).toThrow(new Error('Invalid dice... A roll should contain 5 dice.'));

        test.each([
            [[1, 1, 1, 1, 7]],
            [[0, 1, 1, 1, 2]],
            [[1, 1, -1, 1, 2]]
        ])('invalid die in rolls for %s', (dice) => {
            assertInvalidDieInARoll(() => YahtzeeCalculator.number(dice, 1));
            assertInvalidDieInARoll(() => YahtzeeCalculator.threeOfAKind(dice));
            assertInvalidDieInARoll(() => YahtzeeCalculator.fourOfAKind(dice));
            assertInvalidDieInARoll(() => YahtzeeCalculator.fullHouse(dice));
            assertInvalidDieInARoll(() => YahtzeeCalculator.smallStraight(dice));
            assertInvalidDieInARoll(() => YahtzeeCalculator.largeStraight(dice));
            assertInvalidDieInARoll(() => YahtzeeCalculator.yahtzee(dice));
            assertInvalidDieInARoll(() => YahtzeeCalculator.chance(dice));
        });

        const assertInvalidDieInARoll = (calculate: () => number) =>
            expect(() => calculate()).toThrow(new Error('Invalid die value. Each die must be between 1 and 6.'));
    });
});
