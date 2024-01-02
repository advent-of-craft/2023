package games.vavr;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.vavr.API.Some;
import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzTests {
    public static Stream<Arguments> validInputs() {
        return Stream.of(
                Arguments.of(1, "1"),
                Arguments.of(67, "67"),
                Arguments.of(82, "82"),
                Arguments.of(3, "Fizz"),
                Arguments.of(66, "Fizz"),
                Arguments.of(99, "Fizz"),
                Arguments.of(5, "Buzz"),
                Arguments.of(50, "Buzz"),
                Arguments.of(85, "Buzz"),
                Arguments.of(15, "FizzBuzz"),
                Arguments.of(30, "FizzBuzz"),
                Arguments.of(45, "FizzBuzz")
        );
    }

    public static Stream<Arguments> invalidInputs() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of(101)
        );
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void parse_successfully_numbers_between_1_and_100(int input, String expectedResult) {
        assertThat(FizzBuzz.convert(input))
                .isEqualTo(Some(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void parse_fail_for_numbers_out_of_range(int input) {
        assertThat(FizzBuzz.convert(input).isEmpty())
                .isTrue();
    }
}