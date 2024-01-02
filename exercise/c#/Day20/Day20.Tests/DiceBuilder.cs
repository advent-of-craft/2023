namespace Day20.Tests
{
    public class DiceBuilder
    {
        private readonly int[] _dice;

        private DiceBuilder(int[] dice) => _dice = dice;

        public static DiceBuilder NewRoll(int dice1, int dice2, int dice3, int dice4, int dice5)
            => new([dice1, dice2, dice3, dice4, dice5]);

        public int[] Build() => _dice;

        public override string ToString() => $"[{string.Join(", ", _dice)}]";
    }
}