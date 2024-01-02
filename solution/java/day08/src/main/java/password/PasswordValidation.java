package password;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class PasswordValidation {
    private static final int MINIMUM_LENGTH = 8;
    private static final List<Character> specialCharacters = asList('.', '*', '#', '@', '$', '%', '&');

    public static boolean validate(String password) {
        return greaterThanMinimumLength(password)
                &&
                atLeastOne(password,
                        Character::isUpperCase,
                        Character::isLowerCase,
                        Character::isDigit,
                        specialCharacters::contains
                )
                && noInvalidCharacter(password);
    }

    private static boolean greaterThanMinimumLength(String password) {
        return password.length() >= MINIMUM_LENGTH;
    }

    @SafeVarargs
    private static boolean atLeastOne(String password, Predicate<Character>... predicates) {
        return stream(predicates)
                .allMatch(predicate -> chars(password).anyMatch(predicate));
    }

    private static boolean noInvalidCharacter(String password) {
        return chars(password)
                .allMatch(PasswordValidation::isAValidCharacter);
    }

    private static boolean isAValidCharacter(Character c) {
        return isLetter(c)
                || isDigit(c)
                || specialCharacters.contains(c);
    }

    private static Stream<Character> chars(String password) {
        return password.chars().mapToObj(c -> (char) c);
    }

    private PasswordValidation() {
    }
}