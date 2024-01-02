package domain.yahtzee.constrain.input;

import io.vavr.Function1;

public class YahtzeeCalculator {
    public static int number(Roll roll, int number) {
        return calculate(r -> r.dice()
                        .filter(die -> die == number)
                        .sum()
                        .intValue()
                , roll);
    }

    private static int calculate(Function1<Roll, Integer> compute, Roll roll) {
        return compute.apply(roll);
    }

    private static int calculateNOfAKind(Roll roll, int n) {
        return calculate(r -> hasNOfAKind(r, n)
                        ? r.dice().sum().intValue()
                        : 0,
                roll);
    }

    public static int threeOfAKind(Roll roll) {
        return calculateNOfAKind(roll, 3);
    }

    public static int fourOfAKind(Roll roll) {
        return calculateNOfAKind(roll, 4);
    }

    public static int yahtzee(Roll roll) {
        return calculate(r -> hasNOfAKind(r, 5) ? Scores.YAHTZEE_SCORE : 0, roll);
    }

    public static int fullHouse(Roll roll) {
        return calculate(r -> {
            var dieFrequency = roll.groupDieByFrequency();
            return (dieFrequency.containsValue(3) && dieFrequency.containsValue(2)) ? Scores.HOUSE_SCORE : 0;
        }, roll);
    }

    public static int largeStraight(Roll roll) {
        return calculate(r -> r.dice()
                .sorted()
                .sliding(2)
                .forAll(pair -> pair.get(0) + 1 == pair.get(1)) ? Scores.LARGE_STRAIGHT_SCORE : 0, roll);
    }

    public static int smallStraight(Roll roll) {
        return calculate(r -> isSmallStraight(roll.toString()) ? 30 : 0, roll);
    }

    private static boolean isSmallStraight(String diceString) {
        return diceString.contains("1234") || diceString.contains("2345") || diceString.contains("3456");
    }

    private static boolean hasNOfAKind(Roll roll, int n) {
        return roll.groupDieByFrequency()
                .values()
                .exists(count -> count >= n);
    }

    public static int chance(Roll roll) {
        return calculate(r -> r.dice().sum().intValue(), roll);
    }

    private static class Scores {
        private static final int YAHTZEE_SCORE = 50;
        private static final int HOUSE_SCORE = 25;
        private static final int LARGE_STRAIGHT_SCORE = 40;
    }

    private YahtzeeCalculator() {
    }
}