export class DiceBuilder {
    private readonly dice: number[];

    constructor(dice: number[]) {
        this.dice = dice;
    }

    static newRoll = (dice1: number, dice2: number, dice3: number, dice4: number, dice5: number): DiceBuilder =>
        new DiceBuilder([dice1, dice2, dice3, dice4, dice5]);

    build = (): number[] => this.dice;

    toString = () => this.dice.toString();
}