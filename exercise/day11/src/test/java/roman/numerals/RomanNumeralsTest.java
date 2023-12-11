package roman.numerals;

import io.vavr.test.Gen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.vavr.test.Property.def;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import static roman.numerals.RomanNumerals.convert;

class RomanNumeralsTest {
    private static Stream<Arguments> passingExamples() {
        return Stream.of(
                of(1, "I"),
                of(3, "III"),
                of(4, "IV"),
                of(5, "V"),
                of(10, "X"),
                of(13, "XIII"),
                of(50, "L"),
                of(100, "C"),
                of(500, "D"),
                of(1000, "M"),
                of(2499, "MMCDXCIX")
        );
    }

    @ParameterizedTest()
    @MethodSource("passingExamples")
    void generate_roman_for_numbers(int number, String expectedRoman) {
        assertThat(convert(number))
                .isPresent()
                .contains(expectedRoman);
    }

    @Test
    void returns_empty_for_any_number_out_of_range() {
        var invalidNumbers = Gen.choose(-10_000, 10_000)
                .arbitrary()
                .filter(x -> x <= 0 || x > 3999);

        def("empty for numbers <= 0 or > 3999")
                .forAll(invalidNumbers)
                .suchThat(decimal -> convert(decimal).isEmpty())
                .check()
                .assertIsSatisfied();
    }

    @Test
    void returns_only_valid_symbols_for_valid_numbers() {
        var validNumbers = Gen.choose(1, 3999).arbitrary();

        def("valid symbols for decimal in [1; 3999]")
                .forAll(validNumbers)
                .suchThat(decimal ->
                        convert(decimal)
                                .filter(this::romanCharactersAreValid)
                                .isPresent()
                )
                .check()
                .assertIsSatisfied();
    }

    private boolean romanCharactersAreValid(String roman) {
        return roman.matches("[IVXLCDM]+");
    }
}