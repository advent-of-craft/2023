import {YahtzeeCalculator} from '../../src/domain/yahtzee/constrain-input/yahtzeeCalculator';
import {Roll} from '../../src/domain/yahtzee/constrain-input/roll';
import * as E from 'fp-ts/Either';

describe('yahtzee calculate', () => {
    test.each([
        [[1, 2, 1, 1, 3], 1, 3],
        [[2, 3, 4, 5, 6], 1, 0],
        [[4, 4, 4, 4, 4], 1, 0],
        [[4, 1, 4, 4, 5], 4, 12]
    ])('sum of dice for number : %s fo number %s -> %s', (dice, number, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.number(d, number), expectedResult));

    test.each([
        [[3, 3, 3, 4, 5], 18],
        [[2, 3, 4, 5, 6], 0],
        [[4, 4, 4, 4, 4], 20],
        [[1, 1, 2, 1, 5], 10]
    ])('three of a kind : %s -> %s', (dice, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.threeOfAKind(d), expectedResult));

    test.each([
        [[3, 3, 3, 3, 5], 17],
        [[2, 3, 4, 5, 6], 0],
        [[4, 4, 4, 4, 4], 20],
        [[1, 1, 1, 1, 5], 9]
    ])('four of a kind : %s -> %s', (dice, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.fourOfAKind(d), expectedResult));

    test.each([
        [[2, 2, 3, 3, 3], 25],
        [[2, 3, 4, 5, 6], 0],
        [[4, 4, 1, 4, 1], 25]
    ])('25 for full house : %s -> %s', (dice, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.fullHouse(d), expectedResult));

    test.each([
        [[1, 2, 3, 4, 5], 30],
        [[5, 4, 3, 2, 1], 30],
        [[2, 3, 4, 5, 1], 30],
        [[1, 1, 1, 3, 2], 0]
    ])('30 for small straight : %s -> %s', (dice, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.smallStraight(d), expectedResult));

    test.each([
        [[1, 2, 3, 4, 5], 40],
        [[5, 4, 3, 2, 1], 40],
        [[2, 3, 4, 5, 6], 40],
        [[1, 4, 1, 3, 2], 0]
    ])('30 for large straight : %s -> %s', (dice, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.largeStraight(d), expectedResult));

    test.each([
        [[4, 4, 4, 4, 4], 50],
        [[2, 2, 2, 2, 2], 50],
        [[1, 4, 1, 3, 2], 0]
    ])('50 for yahtzee : %s -> %s', (dice, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.yahtzee(d), expectedResult));

    test.each([
        [[3, 3, 3, 3, 3], 15],
        [[6, 5, 4, 3, 3], 21],
        [[1, 4, 1, 3, 2], 11]
    ])('sum of dice for chance : %s -> %s', (dice, expectedResult) =>
        assertScore(dice, d => YahtzeeCalculator.chance(d), expectedResult));

    const assertScore = (dice: number[], calculate: (roll: Roll) => number, expectedResult: number): void => {
        const roll = Roll.from(dice);
        expect(E.isRight(roll)).toBeTruthy();

        if (E.isRight(roll)) {
            expect(calculate(roll.right)).toBe(expectedResult);
        }
    }

    describe('fail for', () => {
        test.each([
            [[1]],
            [[1, 1]],
            [[1, 6, 2]],
            [[1, 6, 2, 5]],
            [[1, 6, 2, 5, 4, 1]],
            [[1, 6, 2, 5, 4, 1, 2]]
        ])('invalid roll lengths for %s', (dice) =>
            assertInvalidRollFor(dice, 'Invalid dice... A roll should contain 5 dice.'));

        test.each([
            [[1, 1, 1, 1, 7]],
            [[0, 1, 1, 1, 2]],
            [[1, 1, -1, 1, 2]]
        ])('invalid die in rolls for %s', (dice) =>
            assertInvalidRollFor(dice, 'Invalid die value. Each die must be between 1 and 6.'));

        const assertInvalidRollFor = (dice: number[], expectedError: string) => {
            const result = Roll.from(dice);
            expect(E.isLeft(result)).toBeTruthy();

            if (E.isLeft(result)) {
                expect(result.left).toStrictEqual({message: expectedError});
            }
        }
    });
});
