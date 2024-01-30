export class Instruction {
    constructor(public readonly text: string, public readonly x: number) {
    }

    public static fromText(text: string): Instruction {
        const split = text.split(" ");
        return new Instruction(split[0], parseInt(split[1]));
    }
}