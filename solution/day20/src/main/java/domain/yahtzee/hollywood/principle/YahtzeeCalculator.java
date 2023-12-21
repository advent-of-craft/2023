package domain.yahtzee.hollywood.principle;

import io.vavr.Function1;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static io.vavr.collection.List.ofAll;

public class YahtzeeCalculator {
    private static final int ROLL_LENGTH = 5;
    private static final int MINIMUM_DIE = 1;
    private static final int MAXIMUM_DIE = 6;

    public static void number(int[] dice,
                              int number,
                              IntConsumer onSuccess,
                              Consumer<String> onError) {
        calculate(d -> d.filter(die -> die == number)
                .sum()
                .intValue(), dice, onSuccess, onError);
    }

    private static void calculate(Function1<Seq<Integer>, Integer> compute,
                                  int[] dice,
                                  IntConsumer onSuccess,
                                  Consumer<String> onError) {
        if (validateRoll(dice, onError)) {
            onSuccess.accept(
                    compute.apply(ofAll(dice))
            );
        }
    }

    private static boolean validateRoll(int[] dice, Consumer<String> onError) {
        if (hasInvalidLength(dice)) {
            onError.accept("Invalid dice... A roll should contain 6 dice.");
            return false;
        }

        if (containsInvalidDie(dice)) {
            onError.accept("Invalid die value. Each die must be between 1 and 6.");
            return false;
        }
        return true;
    }

    public static void threeOfAKind(int[] dice,
                                    IntConsumer onSuccess,
                                    Consumer<String> onError) {
        calculateNOfAKind(dice, 3, onSuccess, onError);
    }

    private static void calculateNOfAKind(int[] dice,
                                          int n,
                                          IntConsumer onSuccess,
                                          Consumer<String> onError) {
        calculate(d -> hasNOfAKind(d, n)
                        ? d.sum().intValue()
                        : 0,
                dice, onSuccess, onError);
    }

    public static void fourOfAKind(int[] dice,
                                   IntConsumer onSuccess,
                                   Consumer<String> onError) {
        calculateNOfAKind(dice, 4, onSuccess, onError);
    }

    public static void yahtzee(int[] dice,
                               IntConsumer onSuccess,
                               Consumer<String> onError) {
        calculate(d -> hasNOfAKind(d, 5) ? Scores.YAHTZEE_SCORE : 0, dice, onSuccess, onError);
    }

    public static void fullHouse(int[] dice,
                                 IntConsumer onSuccess,
                                 Consumer<String> onError) {
        calculate(d -> {
            var dieFrequency = groupDieByFrequency(d);
            return (dieFrequency.containsValue(3) && dieFrequency.containsValue(2)) ? Scores.HOUSE_SCORE : 0;
        }, dice, onSuccess, onError);
    }

    public static void largeStraight(int[] dice,
                                     IntConsumer onSuccess,
                                     Consumer<String> onError) {
        calculate(d -> isLargeStraight(d)
                ? Scores.LARGE_STRAIGHT_SCORE
                : 0, dice, onSuccess, onError);
    }

    private static boolean isLargeStraight(Seq<Integer> roll) {
        return roll.sorted()
                .sliding(2)
                .forAll(pair -> pair.get(0) + 1 == pair.get(1));
    }

    public static void smallStraight(int[] dice,
                                     IntConsumer onSuccess,
                                     Consumer<String> onError) {
        calculate(d -> {
            var diceString = toSortedString(d);
            return isSmallStraight(diceString) ? 30 : 0;
        }, dice, onSuccess, onError);
    }

    private static boolean isSmallStraight(String diceString) {
        return diceString.contains("1234") || diceString.contains("2345") || diceString.contains("3456");
    }

    private static boolean hasNOfAKind(Seq<Integer> dice, int n) {
        return groupDieByFrequency(dice)
                .values()
                .exists(count -> count >= n);
    }

    private static String toSortedString(Seq<Integer> dice) {
        return dice.sorted()
                .distinct()
                .mkString();
    }

    public static void chance(int[] dice,
                              IntConsumer onSuccess,
                              Consumer<String> onError) {
        calculate(d -> d.sum().intValue(), dice, onSuccess, onError);
    }

    private static Map<Integer, Integer> groupDieByFrequency(Seq<Integer> dice) {
        return dice
                .groupBy(x -> x)
                .mapValues(Traversable::length);
    }

    private static boolean hasInvalidLength(int[] dice) {
        return dice == null || dice.length != ROLL_LENGTH;
    }

    private static boolean containsInvalidDie(int[] dice) {
        return ofAll(dice).exists(YahtzeeCalculator::isValidDie);
    }

    private static boolean isValidDie(int die) {
        return die < MINIMUM_DIE || die > MAXIMUM_DIE;
    }

    private static class Scores {
        private static final int YAHTZEE_SCORE = 50;
        private static final int HOUSE_SCORE = 25;
        private static final int LARGE_STRAIGHT_SCORE = 40;
    }

    private YahtzeeCalculator() {
    }
}