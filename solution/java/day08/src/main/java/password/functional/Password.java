package password.functional;

import io.vavr.Function1;
import io.vavr.collection.Vector;
import io.vavr.control.Either;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class Password {
    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    private static final Vector<Function1<String, Either<ParsingError, Password>>> rules = Vector.of(
            input -> matches(input, ".{8,}", "Too short"),
            input -> matches(input, ".*[A-Z].*", "No capital letter"),
            input -> matches(input, ".*[a-z].*", "No lower letter"),
            input -> matches(input, ".*[0-9].*", "No number"),
            input -> matches(input, ".*[.*#@$%&].*", "No special character"),
            input -> matches(input, "[a-zA-Z0-9.*#@$%&]+", "Invalid character")
    );

    public static Either<ParsingError, Password> from(String input) {
        return rules.map(f -> f.apply(input))
                .find(Either::isLeft)
                .getOrElse(right(new Password(input)));
    }

    private static Either<ParsingError, Password> matches(String input,
                                                          String regex,
                                                          String reason) {
        return input.matches(regex)
                ? right(new Password(input))
                : left(ParsingError.from(reason));
    }
}