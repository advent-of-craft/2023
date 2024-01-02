using Day20.Domain.Yahtzee.Constrain.Input;
using LanguageExt.UnsafeValueAccess;

namespace Day20.Tests.Constrain.Input
{
    public class RollBuilder
    {
        private readonly int[] _dice;

        private RollBuilder(int[] dice) => _dice = dice;

        public static RollBuilder NewRoll(int dice1, int dice2, int dice3, int dice4, int dice5)
            => new([dice1, dice2, dice3, dice4, dice5]);

        public Roll Build() => _dice.ToRoll().ValueUnsafe();

        public override string ToString() => $"[{string.Join(", ", _dice)}]";
    }
}