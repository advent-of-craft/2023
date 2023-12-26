package hollywood.principle;

import builders.DiceBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static domain.yahtzee.hollywood.principle.YahtzeeCalculator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

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
        number(
                dice.build(),
                number,
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
        threeOfAKind(
                dice.build(),
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
        fourOfAKind(
                dice.build(),
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
        fullHouse(
                dice.build(),
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
        smallStraight(
                dice.build(),
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
        largeStraight(
                dice.build(),
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
        yahtzee(
                dice.build(),
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
        chance(
                dice.build(),
                score -> assertThat(score).isEqualTo(expectedResult),
                error -> fail()
        );
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
            number(dice, 1, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
            threeOfAKind(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
            fourOfAKind(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
            fullHouse(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
            smallStraight(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
            largeStraight(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
            yahtzee(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
            chance(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid dice... A roll should contain 6 dice."));
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
            number(dice, 1, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
            threeOfAKind(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
            fourOfAKind(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
            fullHouse(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
            smallStraight(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
            largeStraight(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
            yahtzee(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
            chance(dice, score -> fail(), error -> assertThat(error).isEqualTo("Invalid die value. Each die must be between 1 and 6."));
        }
    }
}