export class Position {
    constructor(public readonly horizontal: number, public readonly depth: number) {
    }

    public changeDepth(newDepth: number): Position {
        return new Position(this.horizontal, newDepth);
    }

    public moveHorizontally(newHorizontal: number): Position {
        return new Position(newHorizontal, this.depth);
    }
}