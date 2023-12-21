package domain.yahtzee.original;

import io.vavr.Function1;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;

import static io.vavr.collection.List.ofAll;

public class YahtzeeCalculator {
    private static final int ROLL_LENGTH = 5;
    private static final int MINIMUM_DIE = 1;
    private static final int MAXIMUM_DIE = 6;

    public static int number(int[] dice, int number) {
        return calculate(d -> d.filter(die -> die == number)
                        .sum()
                        .intValue()
                , dice);
    }

    public static int threeOfAKind(int[] dice) {
        return calculateNOfAKind(dice, 3);
    }

    public static int fourOfAKind(int[] dice) {
        return calculateNOfAKind(dice, 4);
    }

    public static int yahtzee(int[] dice) {
        return calculate(d -> hasNOfAKind(d, 5) ? Scores.YAHTZEE_SCORE : 0, dice);
    }

    private static int calculateNOfAKind(int[] dice, int n) {
        return calculate(d -> hasNOfAKind(d, n)
                        ? d.sum().intValue()
                        : 0,
                dice);
    }

    public static int fullHouse(int[] dice) {
        return calculate(d -> {
                    var dieFrequency = groupDieByFrequency(d);
                    return (dieFrequency.containsValue(3) && dieFrequency.containsValue(2)) ? Scores.HOUSE_SCORE : 0;
                }, dice
        );
    }

    public static int largeStraight(int[] dice) {
        return calculate(d -> d.sorted()
                .sliding(2)
                .forAll(pair -> pair.get(0) + 1 == pair.get(1)) ? Scores.LARGE_STRAIGHT_SCORE : 0, dice);
    }

    public static int smallStraight(int[] dice) {
        return calculate(d -> {
            var diceString = toSortedString(d);
            return isSmallStraight(diceString) ? 30 : 0;
        }, dice);
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

    public static int chance(int[] dice) {
        return calculate(d -> d.sum().intValue(), dice);
    }

    private static Map<Integer, Integer> groupDieByFrequency(Seq<Integer> dice) {
        return dice
                .groupBy(x -> x)
                .mapValues(Traversable::length);
    }

    private static int calculate(Function1<Seq<Integer>, Integer> compute, int[] dice) {
        validateRoll(dice);
        return compute.apply(ofAll(dice));
    }

    private static void validateRoll(int[] dice) {
        if (hasInvalidLength(dice)) {
            throw new IllegalArgumentException("Invalid dice... A roll should contain 6 dice.");
        }

        if (containsInvalidDie(dice)) {
            throw new IllegalArgumentException("Invalid die value. Each die must be between 1 and 6.");
        }
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