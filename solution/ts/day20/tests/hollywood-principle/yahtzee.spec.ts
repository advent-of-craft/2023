import {YahtzeeCalculator} from '../../src/domain/yahtzee/hollywood-principle/yahtzeeCalculator';
import {DiceBuilder} from '../diceBuilder';

describe('yahtzee calculate', () => {
    test.each([
        [DiceBuilder.newRoll(1, 2, 1, 1, 3), 1, 3],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 1, 0],
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 1, 0],
        [DiceBuilder.newRoll(4, 1, 4, 4, 5), 4, 12]
    ])('sum of dice for number : %s fo number %s -> %s', (dice, number, expectedResult) =>
        YahtzeeCalculator.number(
            dice.build(),
            number,
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    test.each([
        [DiceBuilder.newRoll(3, 3, 3, 4, 5), 18],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 0],
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 20],
        [DiceBuilder.newRoll(1, 1, 2, 1, 5), 10]
    ])('three of a kind : %s -> %s', (dice, expectedResult) =>
        YahtzeeCalculator.threeOfAKind(
            dice.build(),
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    test.each([
        [DiceBuilder.newRoll(3, 3, 3, 3, 5), 17],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 0],
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 20],
        [DiceBuilder.newRoll(1, 1, 1, 1, 5), 9]
    ])('four of a kind : %s -> %s', (dice, expectedResult) =>
        YahtzeeCalculator.fourOfAKind(
            dice.build(),
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    test.each([
        [DiceBuilder.newRoll(2, 2, 3, 3, 3), 25],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 0],
        [DiceBuilder.newRoll(4, 4, 1, 4, 1), 25]
    ])('25 for full house : %s -> %s', (dice, expectedResult) =>
        YahtzeeCalculator.fullHouse(
            dice.build(),
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    test.each([
        [DiceBuilder.newRoll(1, 2, 3, 4, 5), 30],
        [DiceBuilder.newRoll(5, 4, 3, 2, 1), 30],
        [DiceBuilder.newRoll(2, 3, 4, 5, 1), 30],
        [DiceBuilder.newRoll(1, 1, 1, 3, 2), 0]
    ])('30 for small straight : %s -> %s', (dice, expectedResult) =>
        YahtzeeCalculator.smallStraight(
            dice.build(),
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    test.each([
        [DiceBuilder.newRoll(1, 2, 3, 4, 5), 40],
        [DiceBuilder.newRoll(5, 4, 3, 2, 1), 40],
        [DiceBuilder.newRoll(2, 3, 4, 5, 6), 40],
        [DiceBuilder.newRoll(1, 4, 1, 3, 2), 0]
    ])('30 for large straight : %s -> %s', (dice, expectedResult) =>
        YahtzeeCalculator.largeStraight(
            dice.build(),
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    test.each([
        [DiceBuilder.newRoll(4, 4, 4, 4, 4), 50],
        [DiceBuilder.newRoll(2, 2, 2, 2, 2), 50],
        [DiceBuilder.newRoll(1, 4, 1, 3, 2), 0]
    ])('50 for yahtzee : %s -> %s', (dice, expectedResult) =>
        YahtzeeCalculator.yahtzee(
            dice.build(),
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    test.each([
        [DiceBuilder.newRoll(3, 3, 3, 3, 3), 15],
        [DiceBuilder.newRoll(6, 5, 4, 3, 3), 21],
        [DiceBuilder.newRoll(1, 4, 1, 3, 2), 11]
    ])('sum of dice for chance : %s -> %s', (dice, expectedResult) =>
        YahtzeeCalculator.chance(
            dice.build(),
            score => expect(score).toBe(expectedResult),
            () => fail('should not call onError')
        ));

    describe('fail for', () => {
        test.each([
            [[1]],
            [[1, 1]],
            [[1, 6, 2]],
            [[1, 6, 2, 5]],
            [[1, 6, 2, 5, 4, 1]],
            [[1, 6, 2, 5, 4, 1, 2]]
        ])('invalid roll lengths for %s', (dice) => {
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.number(dice, 1, onSuccess, onError));
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.threeOfAKind(dice, onSuccess, onError));
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.fourOfAKind(dice, onSuccess, onError));
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.fullHouse(dice, onSuccess, onError));
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.smallStraight(dice, onSuccess, onError));
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.largeStraight(dice, onSuccess, onError));
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.yahtzee(dice, onSuccess, onError));
            assertInvalidRollLength((onSuccess, onError) => YahtzeeCalculator.chance(dice, onSuccess, onError));
        });

        const assertInvalidRollLength = (calculate: (onSuccess: (score: number) => void,
                                                     onError: (error: string) => void) => void) =>
            calculate(
                score => fail('onSuccess should not be called on error'),
                error => expect(error).toBe('Invalid dice... A roll should contain 5 dice.')
            );

        test.each([
            [[1, 1, 1, 1, 7]],
            [[0, 1, 1, 1, 2]],
            [[1, 1, -1, 1, 2]]
        ])('invalid die in rolls for %s', (dice) => {
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.number(dice, 1, onSuccess, onError));
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.threeOfAKind(dice, onSuccess, onError));
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.fourOfAKind(dice, onSuccess, onError));
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.fullHouse(dice, onSuccess, onError));
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.smallStraight(dice, onSuccess, onError));
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.largeStraight(dice, onSuccess, onError));
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.yahtzee(dice, onSuccess, onError));
            assertInvalidDieInARoll((onSuccess, onError) => YahtzeeCalculator.chance(dice, onSuccess, onError));
        });

        const assertInvalidDieInARoll = (calculate: (onSuccess: (score: number) => void,
                                                     onError: (error: string) => void) => void) =>
            calculate(
                score => fail('onSuccess should not be called on error'),
                error => expect(error).toBe('Invalid die value. Each die must be between 1 and 6.')
            );
    });
});
