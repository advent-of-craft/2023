using System.Collections.Immutable;
using LanguageExt;

namespace Day20.Domain.Yahtzee.Constrain.Input
{
    public sealed class Roll : IEquatable<Roll>
    {
        private const int RollLength = 5;
        private const int MinimumDie = 1;
        private const int MaximumDie = 6;
        public int[] Dice { get; }
        private Roll(int[] dice) => Dice = dice;

        public static Either<ParsingError, Roll> Parse(int[] dice)
        {
            if (HasInvalidLength(dice))
            {
                return new ParsingError("Invalid dice... A roll should contain 5 dice.");
            }

            return ContainsInvalidDie(dice)
                ? new ParsingError("Invalid die value. Each die must be between 1 and 6.")
                : new Roll(dice.ToImmutableArray().ToArray());
        }

        public Dictionary<int, int> GroupDieByFrequency()
            => Dice.GroupBy(x => x).ToDictionary(g => g.Key, g => g.Count());

        private static bool HasInvalidLength(int[] dice) => dice is not {Length: RollLength};
        private static bool ContainsInvalidDie(IEnumerable<int> dice) => !dice.All(IsValidDie);
        private static bool IsValidDie(int die) => die is >= MinimumDie and <= MaximumDie;
        public override string ToString() => Dice.ToString()!;

        #region IEquatable

        public override int GetHashCode() => Dice.GetHashCode();
        public static bool operator ==(Roll roll, Roll other) => roll.Equals(other);
        public static bool operator !=(Roll roll, Roll other) => !(roll == other);
        public bool Equals(Roll? other) => Dice.Equals(other!.Dice);
        public override bool Equals(object? obj) => obj is Roll && Equals(Dice);

        #endregion
    }

    public sealed record ParsingError(string Reason);

    public static class Extensions
    {
        public static Either<ParsingError, Roll> ToRoll(this int[] dice) => Roll.Parse(dice);
    }
}