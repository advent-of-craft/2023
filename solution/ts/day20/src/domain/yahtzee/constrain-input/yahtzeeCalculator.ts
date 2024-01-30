import {Roll} from './roll';

export class YahtzeeCalculator {
    static number = (roll: Roll, number: number): number => this.calculate(
        r => r.dice.filter(die => die === number)
            .reduce((sum, die) => sum + die, 0),
        roll
    );

    static threeOfAKind = (roll: Roll): number => this.calculateNOfAKind(roll, 3);

    static fourOfAKind = (roll: Roll): number => this.calculateNOfAKind(roll, 4);

    static yahtzee = (roll: Roll): number => this.calculate(
        r => r.dice.every(die => die === roll.dice[0])
            ? Scores.YAHTZEE_SCORE
            : 0,
        roll
    );

    static fullHouse = (roll: Roll): number => this.calculate(
        r => {
            const frequencies = Object.values(r.groupDieByFrequency());
            const hasThreeOfAKind = frequencies.includes(3);
            const hasPair = frequencies.includes(2);

            return hasThreeOfAKind && hasPair
                ? Scores.HOUSE_SCORE
                : 0;
        },
        roll
    );

    static smallStraight = (roll: Roll): number => this.calculate(
        r =>
            this.isSmallStraight(Array.from(new Set(r.dice)).sort().join(''))
                ? 30
                : 0,
        roll
    );

    static largeStraight = (roll: Roll): number => this.calculate(
        r => {
            const sortedDice = [...r.dice].sort((a, b) => a - b);
            const isSequential = sortedDice.every((die, index, arr) => index === 0 || die === arr[index - 1] + 1);

            return isSequential
                ? Scores.LARGE_STRAIGHT_SCORE
                : 0;
        },
        roll
    );

    static chance = (roll: Roll): number => this.calculate(
        r => r.dice.reduce((sum, die) => sum + die, 0),
        roll
    );

    private static calculateNOfAKind = (roll: Roll, n: number): number => this.calculate(
        r =>
            Object.values(r.groupDieByFrequency()).some(count => count >= n)
                ? r.dice.reduce((sum, die) => sum + die, 0)
                : 0,
        roll
    );
    private static isSmallStraight = (diceString: string): boolean =>
        ['1234', '2345', '3456'].some(straight => diceString.includes(straight));

    private static calculate = (compute: (roll: Roll) => number, roll: Roll): number => compute(roll);
}

const Scores = {
    YAHTZEE_SCORE: 50,
    HOUSE_SCORE: 25,
    LARGE_STRAIGHT_SCORE: 40
};