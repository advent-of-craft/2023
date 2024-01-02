package domain.yahtzee.constrain.input;

public record Error(String message) {
    public static Error error(String message) {
        return new Error(message);
    }
}
