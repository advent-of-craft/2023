import {Either, left, right} from "fp-ts/Either";

export type ParsingError = { message: string }

export class Roll {
    private static readonly ROLL_LENGTH: number = 5;
    private static readonly MINIMUM_DIE: number = 1;
    private static readonly MAXIMUM_DIE: number = 6;

    private constructor(readonly dice: number[]) {
    }

    public static from(dice: number[]): Either<ParsingError, Roll> {
        if (this.hasInvalidLength(dice)) return left({message: "Invalid dice... A roll should contain 5 dice."});
        return this.containsInvalidDie(dice)
            ? left({message: "Invalid die value. Each die must be between 1 and 6."})
            : right(new Roll(dice));
    }

    private static hasInvalidLength = (dice: number[]): boolean =>
        !dice || dice.length !== this.ROLL_LENGTH;

    private static containsInvalidDie = (dice: number[]): boolean =>
        dice.some(die => this.isInvalidDie(die));

    private static isInvalidDie = (die: number): boolean =>
        die < this.MINIMUM_DIE || die > this.MAXIMUM_DIE;

    public groupDieByFrequency = (): Record<number, number> =>
        this.dice.reduce((freq, die) => {
            freq[die] = (freq[die] || 0) + 1;
            return freq;
        }, {} as Record<number, number>);

    public toString = (): string => `[${this.dice.sort().join(", ")}]`;
}