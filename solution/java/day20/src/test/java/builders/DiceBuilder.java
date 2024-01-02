package builders;

import java.util.Arrays;

public class DiceBuilder {
    private final int[] dice;

    private DiceBuilder(int[] dice) {
        this.dice = dice;
    }

    public static DiceBuilder newRoll(int dice1, int dice2, int dice3, int dice4, int dice5) {
        return new DiceBuilder(new int[]{dice1, dice2, dice3, dice4, dice5});
    }

    public int[] build() {
        return dice;
    }

    @Override
    public String toString() {
        return Arrays.toString(dice);
    }
}