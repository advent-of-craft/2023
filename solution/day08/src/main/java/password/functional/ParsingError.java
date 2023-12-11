package password.functional;

public record ParsingError(String reason) {
    public static ParsingError from(String reason) {
        return new ParsingError(reason);
    }
}