export class YahtzeeCalculator {
    private static readonly ROLL_LENGTH = 5;
    private static readonly MINIMUM_DIE = 1;
    private static readonly MAXIMUM_DIE = 6;

    static number = (dice: number[], number: number): number => this.calculate(
        d => d.filter(die => die === number)
            .reduce((sum, die) => sum + die, 0),
        dice
    );

    static threeOfAKind = (dice: number[]): number => this.calculateNOfAKind(dice, 3);

    static fourOfAKind = (dice: number[]): number => this.calculateNOfAKind(dice, 4);

    static yahtzee = (dice: number[]): number => this.calculate(
        d => d.every(die => die === dice[0])
            ? Scores.YAHTZEE_SCORE
            : 0,
        dice
    );

    static fullHouse = (dice: number[]): number => this.calculate(
        d => {
            const frequencies = this.getFrequencies(d);
            const hasThreeOfAKind = Object.values(frequencies).includes(3);
            const hasPair = Object.values(frequencies).includes(2);

            return hasThreeOfAKind && hasPair
                ? Scores.HOUSE_SCORE
                : 0;
        },
        dice
    );

    static smallStraight = (dice: number[]): number => this.calculate(
        d => {
            const sortedUniqueDice = Array.from(new Set(d)).sort();
            const diceString = sortedUniqueDice.join('');

            return this.isSmallStraight(diceString)
                ? 30
                : 0;
        },
        dice
    );

    static largeStraight = (dice: number[]): number => this.calculate(
        d => {
            const sortedDice = [...d].sort((a, b) => a - b);
            const isSequential = sortedDice.every((die, index, arr) => index === 0 || die === arr[index - 1] + 1);

            return isSequential
                ? Scores.LARGE_STRAIGHT_SCORE
                : 0;
        },
        dice
    );

    static chance = (dice: number[]): number => this.calculate(
        d => d.reduce((sum, die) => sum + die, 0),
        dice
    );

    private static calculateNOfAKind = (dice: number[], n: number): number => this.calculate(
        d => {
            const hasNOfAKind = Object.values(this.getFrequencies(d)).some(count => count >= n);
            return hasNOfAKind
                ? d.reduce((sum, die) => sum + die, 0)
                : 0;
        },
        dice
    );

    private static validateRoll(dice: number[]): void {
        if (dice.length !== this.ROLL_LENGTH) {
            throw new Error('Invalid dice... A roll should contain 5 dice.');
        }

        if (dice.some(die => die < this.MINIMUM_DIE || die > this.MAXIMUM_DIE)) {
            throw new Error('Invalid die value. Each die must be between 1 and 6.');
        }
    }

    private static getFrequencies = (dice: number[]): Record<number, number> =>
        dice.reduce((freq, die) => {
            freq[die] = (freq[die] || 0) + 1;
            return freq;
        }, {} as Record<number, number>);

    private static isSmallStraight = (diceString: string): boolean =>
        ['1234', '2345', '3456'].some(straight => diceString.includes(straight));

    private static calculate(compute: (dice: number[]) => number, dice: number[]): number {
        this.validateRoll(dice);
        return compute(dice);
    }
}

const Scores = {
    YAHTZEE_SCORE: 50,
    HOUSE_SCORE: 25,
    LARGE_STRAIGHT_SCORE: 40
};