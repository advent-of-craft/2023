package M;

public record Position(int horizontal, int depth) {
    public Position changeDepth(int newDepth) {
        return new Position(horizontal, newDepth);
    }

    public Position moveHorizontally(int newHorizontal) {
        return new Position(newHorizontal, depth);
    }
}