package M;

import java.util.List;

public class Submarine {
    private final Position position;

    private Submarine(Position position) {
        this.position = position;
    }

    public Submarine(int horizontal, int depth) {
        this(new Position(horizontal, depth));
    }

    public Submarine move(List<Instruction> instructions) {
        return instructions
                .stream()
                .reduce(this, Submarine::move, (p, n) -> p);
    }

    private Submarine move(Instruction instruction) {
        return new Submarine(switch (instruction.text()) {
            case "down" -> position.changeDepth(position.depth() + instruction.x());
            case "up" -> position.changeDepth(position.depth() - instruction.x());
            default -> position.moveHorizontally(position.horizontal() + instruction.x());
        });
    }

    public Position getPosition() {
        return position;
    }
}