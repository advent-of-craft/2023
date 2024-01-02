package original;

import builders.DiceBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static domain.yahtzee.original.YahtzeeCalculator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YahtzeeCalculatorTests {
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
        assertThat(number(dice.build(), number))
                .isEqualTo(expectedResult);
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
        assertThat(threeOfAKind(dice.build()))
                .isEqualTo(expectedResult);
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
        assertThat(fourOfAKind(dice.build()))
                .isEqualTo(expectedResult);
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
        assertThat(fullHouse(dice.build()))
                .isEqualTo(expectedResult);
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
        assertThat(smallStraight(dice.build()))
                .isEqualTo(expectedResult);
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
        assertThat(largeStraight(dice.build()))
                .isEqualTo(expectedResult);
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
        assertThat(yahtzee(dice.build()))
                .isEqualTo(expectedResult);
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
        assertThat(chance(dice.build()))
                .isEqualTo(expectedResult);
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
            assertThatThrownBy(() -> number(dice, 1)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> threeOfAKind(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> fourOfAKind(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> fullHouse(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> smallStraight(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> largeStraight(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> yahtzee(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> chance(dice)).isInstanceOf(IllegalArgumentException.class);
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
            assertThatThrownBy(() -> number(dice, 1)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> threeOfAKind(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> fourOfAKind(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> fullHouse(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> smallStraight(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> largeStraight(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> yahtzee(dice)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> chance(dice)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}