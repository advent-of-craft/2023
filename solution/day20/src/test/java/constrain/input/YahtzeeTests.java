package constrain.input;

import builders.DiceBuilder;
import domain.yahtzee.constrain.input.Error;
import domain.yahtzee.constrain.input.Roll;
import domain.yahtzee.constrain.input.YahtzeeCalculator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static domain.yahtzee.constrain.input.YahtzeeCalculator.number;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class YahtzeeTests {
    public static Stream<Arguments> numbers() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(1, 2, 1, 1, 3), 1, 3),
                Arguments.of(DiceBuilder.newRoll(2, 3, 4, 5, 6), 1, 0),
                Arguments.of(DiceBuilder.newRoll(4, 4, 4, 4, 4), 1, 0),
                Arguments.of(DiceBuilder.newRoll(4, 1, 4, 4, 5), 4, 12)
        );
    }

    @MethodSource("numbers")
    @ParameterizedTest
    void sum_for_numbers_for(DiceBuilder dice, int number, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(r -> number(r, number))
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    public static Stream<Arguments> threeOfAKinds() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(3, 3, 3, 4, 5), 18),
                Arguments.of(DiceBuilder.newRoll(2, 3, 4, 5, 6), 0),
                Arguments.of(DiceBuilder.newRoll(4, 4, 4, 4, 4), 20),
                Arguments.of(DiceBuilder.newRoll(1, 1, 2, 1, 5), 10)
        );
    }

    @MethodSource("threeOfAKinds")
    @ParameterizedTest
    void three_of_a_kind_for(DiceBuilder dice, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(YahtzeeCalculator::threeOfAKind)
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    public static Stream<Arguments> fourOfAKinds() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(3, 3, 3, 3, 5), 17),
                Arguments.of(DiceBuilder.newRoll(2, 3, 4, 5, 6), 0),
                Arguments.of(DiceBuilder.newRoll(4, 4, 4, 4, 4), 20),
                Arguments.of(DiceBuilder.newRoll(1, 1, 1, 1, 5), 9)
        );
    }

    @MethodSource("fourOfAKinds")
    @ParameterizedTest
    void four_of_a_kind_for(DiceBuilder dice, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(YahtzeeCalculator::fourOfAKind)
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    public static Stream<Arguments> fullHouses() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(2, 2, 3, 3, 3), 25),
                Arguments.of(DiceBuilder.newRoll(2, 3, 4, 5, 6), 0),
                Arguments.of(DiceBuilder.newRoll(4, 4, 1, 4, 1), 25)
        );
    }

    @MethodSource("fullHouses")
    @ParameterizedTest
    void full_house_for(DiceBuilder dice, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(YahtzeeCalculator::fullHouse)
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    public static Stream<Arguments> smallStraights() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(1, 2, 3, 4, 5), 30),
                Arguments.of(DiceBuilder.newRoll(5, 4, 3, 2, 1), 30),
                Arguments.of(DiceBuilder.newRoll(2, 3, 4, 5, 1), 30),
                Arguments.of(DiceBuilder.newRoll(1, 1, 1, 3, 2), 0)
        );
    }

    @MethodSource("smallStraights")
    @ParameterizedTest
    void small_straight_for(DiceBuilder dice, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(YahtzeeCalculator::smallStraight)
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    public static Stream<Arguments> largeStraights() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(1, 2, 3, 4, 5), 40),
                Arguments.of(DiceBuilder.newRoll(5, 4, 3, 2, 1), 40),
                Arguments.of(DiceBuilder.newRoll(2, 3, 4, 5, 6), 40),
                Arguments.of(DiceBuilder.newRoll(1, 4, 1, 3, 2), 0)
        );
    }

    @MethodSource("largeStraights")
    @ParameterizedTest
    void large_straight_for(DiceBuilder dice, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(YahtzeeCalculator::largeStraight)
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    public static Stream<Arguments> yahtzees() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(4, 4, 4, 4, 4), 50),
                Arguments.of(DiceBuilder.newRoll(2, 2, 2, 2, 2), 50),
                Arguments.of(DiceBuilder.newRoll(1, 4, 1, 3, 2), 0)
        );
    }

    @MethodSource("yahtzees")
    @ParameterizedTest
    void yahtzeeFor(DiceBuilder dice, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(YahtzeeCalculator::yahtzee)
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    public static Stream<Arguments> chances() {
        return Stream.of(
                Arguments.of(DiceBuilder.newRoll(3, 3, 3, 3, 3), 15),
                Arguments.of(DiceBuilder.newRoll(6, 5, 4, 3, 3), 21),
                Arguments.of(DiceBuilder.newRoll(1, 4, 1, 3, 2), 11)
        );
    }

    @MethodSource("chances")
    @ParameterizedTest
    void chanceFor(DiceBuilder dice, int expectedResult) {
        assertThat(
                Roll.from(dice.build()).map(YahtzeeCalculator::chance)
        ).hasRightValueSatisfying(score -> assertThat(score).isEqualTo(expectedResult));
    }

    @Nested
    class FailFor {
        public static Stream<Arguments> invalidRollLengths() {
            return Stream.of(
                    Arguments.of(new int[]{1}),
                    Arguments.of(new int[]{1, 1}),
                    Arguments.of(new int[]{1, 6, 2}),
                    Arguments.of(new int[]{1, 6, 2, 5}),
                    Arguments.of(new int[]{1, 6, 2, 5, 4, 1}),
                    Arguments.of(new int[]{1, 6, 2, 5, 4, 1, 2})
            );
        }

        @MethodSource("invalidRollLengths")
        @ParameterizedTest
        void invalid_roll_lengths(int... dice) {
            assertFailure(dice, "Invalid dice... A roll should contain 6 dice.");
        }

        public static Stream<Arguments> invalidDieInRolls() {
            return Stream.of(
                    Arguments.of(new int[]{1, 1, 1, 1, 7}),
                    Arguments.of(new int[]{0, 1, 1, 1, 2}),
                    Arguments.of(new int[]{1, 1, -1, 1, 2})
            );
        }

        @MethodSource("invalidDieInRolls")
        @ParameterizedTest
        void invalid_die_in_rolls(int[] dice) {
            assertFailure(dice, "Invalid die value. Each die must be between 1 and 6.");
        }

        private static void assertFailure(int[] dice, String reason) {
            assertThat(Roll.from(dice))
                    .hasLeftValueSatisfying(error -> assertThat(error).isEqualTo(Error.error(reason)));
        }
    }
}