import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import password.PasswordValidation;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordValidationTest {
    private static Stream<Arguments> validPasswords() {
        return Stream.of(
                Arguments.of("P@ssw0rd"),
                Arguments.of("Advent0fCraft&")
        );
    }

    @MethodSource("validPasswords")
    @ParameterizedTest
    void success_for_a_valid_password(String password) {
        assertThat(PasswordValidation.validate(password))
                .isTrue();
    }

    @Nested
    class FailWhen {
        private static Stream<Arguments> invalidPasswords() {
            return Stream.of(
                    Arguments.of("", "Too short"),
                    Arguments.of("aa", "Too short"),
                    Arguments.of("xxxxxxx", "Too short"),
                    Arguments.of("adventofcraft", "No capital letter"),
                    Arguments.of("p@ssw0rd", "No capital letter"),
                    Arguments.of("ADVENTOFCRAFT", "No lower letter"),
                    Arguments.of("P@SSW0RD", "No lower letter"),
                    Arguments.of("Adventofcraft", "No number"),
                    Arguments.of("P@sswOrd", "No number"),
                    Arguments.of("Adventof09craft", "No special character"),
                    Arguments.of("PAssw0rd", "No special character"),
                    Arguments.of("Advent@of9Craft¨", "Invalid character"),
                    Arguments.of("P@ssw^rd", "Invalid character")
            );
        }

        @MethodSource("invalidPasswords")
        @ParameterizedTest
        void invalid_passwords(String password, String reason) {
            assertThat(PasswordValidation.validate(password))
                    .as(reason)
                    .isFalse();
        }
    }
}