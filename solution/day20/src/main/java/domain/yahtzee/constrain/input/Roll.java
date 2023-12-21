package domain.yahtzee.constrain.input;

import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import io.vavr.control.Either;

import static domain.yahtzee.constrain.input.Error.error;
import static io.vavr.collection.List.ofAll;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class Roll {
    private static final int ROLL_LENGTH = 5;
    private static final int MINIMUM_DIE = 1;
    private static final int MAXIMUM_DIE = 6;

    private final Seq<Integer> dice;

    private Roll(int[] dice) {
        this.dice = ofAll(dice);
    }

    public static Either<Error, Roll> from(int[] dice) {
        if (hasInvalidLength(dice)) {
            return left(error("Invalid dice... A roll should contain 6 dice."));
        }
        if (containsInvalidDie(dice)) {
            return left(error("Invalid die value. Each die must be between 1 and 6."));
        }
        return right(new Roll(dice));
    }

    private static boolean hasInvalidLength(int[] dice) {
        return dice == null || dice.length != ROLL_LENGTH;
    }

    private static boolean containsInvalidDie(int[] dice) {
        return ofAll(dice).exists(Roll::isValidDie);
    }

    private static boolean isValidDie(int die) {
        return die < MINIMUM_DIE || die > MAXIMUM_DIE;
    }

    public Map<Integer, Integer> groupDieByFrequency() {
        return dice.groupBy(x -> x)
                .mapValues(Traversable::length);
    }

    @Override
    public String toString() {
        return dice.sorted()
                .distinct()
                .mkString();
    }

    public Seq<Integer> dice() {
        return this.dice;
    }
}