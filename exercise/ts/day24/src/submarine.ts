import {Position} from "./position";
import {Instruction} from "./instruction";

export class Submarine {
    private readonly position: Position;

    constructor(horizontal: number, depth: number) {
        this.position = new Position(horizontal, depth);
    }

    public move = (instructions: Instruction[]): Submarine =>
        instructions.reduce((submarine, instruction) => this.applyInstruction(instruction), this);

    public getPosition = (): Position => this.position;

    private applyInstruction(instruction: Instruction): Submarine {
        let newPosition: Position;

        if (instruction.text === "down") {
            newPosition = this.position.changeDepth(this.position.depth + instruction.x);
        } else if (instruction.text === "up") {
            newPosition = this.position.changeDepth(this.position.depth - instruction.x);
        } else {
            newPosition = this.position.moveHorizontally(this.position.horizontal + instruction.x);
        }

        return new Submarine(newPosition.horizontal, newPosition.depth);
    }
}