export class YahtzeeCalculator {
    private static readonly ROLL_LENGTH = 5;
    private static readonly MINIMUM_DIE = 1;
    private static readonly MAXIMUM_DIE = 6;

    static number = (dice: number[],
                     number: number,
                     onSuccess: (score: number) => void,
                     onError: (error: string) => void): void => this.calculate(
        d => d.filter(die => die === number)
            .reduce((sum, die) => sum + die, 0),
        dice, onSuccess, onError
    );

    static threeOfAKind = (dice: number[],
                           onSuccess: (score: number) => void,
                           onError: (error: string) => void): void =>
        this.calculateNOfAKind(dice, 3, onSuccess, onError);

    static fourOfAKind = (dice: number[],
                          onSuccess: (score: number) => void,
                          onError: (error: string) => void): void =>
        this.calculateNOfAKind(dice, 4, onSuccess, onError);

    static yahtzee = (dice: number[],
                      onSuccess: (score: number) => void,
                      onError: (error: string) => void): void => this.calculate(
        d => d.every(die => die === dice[0])
            ? Scores.YAHTZEE_SCORE
            : 0,
        dice, onSuccess, onError
    );

    static fullHouse = (dice: number[],
                        onSuccess: (score: number) => void,
                        onError: (error: string) => void): void => this.calculate(
        d => {
            const frequencies = this.getFrequencies(d);
            const hasThreeOfAKind = Object.values(frequencies).includes(3);
            const hasPair = Object.values(frequencies).includes(2);

            return hasThreeOfAKind && hasPair
                ? Scores.HOUSE_SCORE
                : 0;
        },
        dice, onSuccess, onError
    );

    static smallStraight = (dice: number[],
                            onSuccess: (score: number) => void,
                            onError: (error: string) => void): void => this.calculate(
        d => {
            const sortedUniqueDice = Array.from(new Set(d)).sort();
            const diceString = sortedUniqueDice.join('');

            return this.isSmallStraight(diceString)
                ? 30
                : 0;
        },
        dice, onSuccess, onError
    );

    static largeStraight = (dice: number[],
                            onSuccess: (score: number) => void,
                            onError: (error: string) => void): void => this.calculate(
        d => {
            const sortedDice = [...d].sort((a, b) => a - b);
            const isSequential = sortedDice.every((die, index, arr) => index === 0 || die === arr[index - 1] + 1);

            return isSequential
                ? Scores.LARGE_STRAIGHT_SCORE
                : 0;
        },
        dice, onSuccess, onError
    );

    static chance = (dice: number[],
                     onSuccess: (score: number) => void,
                     onError: (error: string) => void): void => this.calculate(
        d => d.reduce((sum, die) => sum + die, 0),
        dice, onSuccess, onError
    );

    private static calculateNOfAKind = (dice: number[],
                                        n: number,
                                        onSuccess: (score: number) => void,
                                        onError: (error: string) => void): void => this.calculate(
        d => {
            const hasNOfAKind = Object.values(this.getFrequencies(d)).some(count => count >= n);
            return hasNOfAKind
                ? d.reduce((sum, die) => sum + die, 0)
                : 0;
        },
        dice, onSuccess, onError
    );

    private static validateRoll(dice: number[], onError: (error: string) => void): boolean {
        if (dice.length !== this.ROLL_LENGTH) {
            onError('Invalid dice... A roll should contain 5 dice.');
            return false;
        }

        if (dice.some(die => die < this.MINIMUM_DIE || die > this.MAXIMUM_DIE)) {
            onError('Invalid die value. Each die must be between 1 and 6.');
            return false;
        }
        return true;
    }

    private static getFrequencies = (dice: number[]): Record<number, number> =>
        dice.reduce((freq, die) => {
            freq[die] = (freq[die] || 0) + 1;
            return freq;
        }, {} as Record<number, number>);

    private static isSmallStraight = (diceString: string): boolean =>
        ['1234', '2345', '3456'].some(straight => diceString.includes(straight));

    private static calculate(
        compute: (dice: number[]) => number,
        dice: number[],
        onSuccess: (score: number) => void,
        onError: (error: string) => void): void {
        if (this.validateRoll(dice, onError)) {
            onSuccess(compute(dice));
        }
    }
}

const Scores = {
    YAHTZEE_SCORE: 50,
    HOUSE_SCORE: 25,
    LARGE_STRAIGHT_SCORE: 40
};