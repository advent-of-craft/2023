namespace Day20.Domain.Yahtzee
{
    public static class YahtzeeCalculator
    {
        private const int RollLength = 5;
        private const int MinimumDie = 1;
        private const int MaximumDie = 6;

        public static int Number(int[] dice, int number)
            => Calculate(d => d.Where(die => die == number).Sum(), dice);

        public static int ThreeOfAKind(int[] dice) => CalculateNOfAKind(dice, 3);

        public static int FourOfAKind(int[] dice) => CalculateNOfAKind(dice, 4);

        public static int Yahtzee(int[] dice)
            => Calculate(d =>
                    HasNOfAKind(d, 5)
                        ? Scores.YahtzeeScore
                        : 0,
                dice);

        private static int CalculateNOfAKind(int[] dice, int n)
            => Calculate(d => HasNOfAKind(d, n) ? d.Sum() : 0, dice);

        public static int FullHouse(int[] dice)
            => Calculate(d =>
            {
                var dieFrequency = GroupDieByFrequency(d);
                return dieFrequency.ContainsValue(3) && dieFrequency.ContainsValue(2) ? Scores.HouseScore : 0;
            }, dice);

        public static int LargeStraight(int[] dice)
            => Calculate(d => d
                .OrderBy(x => x)
                .Zip(
                    d.OrderBy(x => x).Skip(1),
                    (a, b) => b - a
                ).All(diff => diff == 1)
                ? Scores.LargeStraightScore
                : 0, dice);

        public static int SmallStraight(int[] dice)
            => Calculate(d =>
            {
                var sortedDice = string.Concat(d.OrderBy(x => x).Distinct());
                return IsSmallStraight(sortedDice) ? 30 : 0;
            }, dice);

        private static bool IsSmallStraight(string diceString)
            => diceString.Contains("1234") || diceString.Contains("2345") || diceString.Contains("3456");

        private static bool HasNOfAKind(IEnumerable<int> dice, int n)
            => GroupDieByFrequency(dice).Values.Any(count => count >= n);

        public static int Chance(int[] dice) => Calculate(d => d.Sum(), dice);

        private static Dictionary<int, int> GroupDieByFrequency(IEnumerable<int> dice)
            => dice.GroupBy(x => x).ToDictionary(g => g.Key, g => g.Count());

        private static int Calculate(Func<List<int>, int> compute, int[] dice)
        {
            ValidateRoll(dice);
            return compute(dice.ToList());
        }

        #region Validation

        private static void ValidateRoll(int[] dice)
        {
            if (HasInvalidLength(dice))
            {
                throw new ArgumentException("Invalid dice... A roll should contain 5 dice.");
            }

            if (ContainsInvalidDie(dice))
            {
                throw new ArgumentException("Invalid die value. Each die must be between 1 and 6.");
            }
        }

        private static bool HasInvalidLength(int[] dice) => dice is not {Length: RollLength};

        private static bool ContainsInvalidDie(IEnumerable<int> dice) => !dice.All(IsValidDie);

        private static bool IsValidDie(int die) => die is >= MinimumDie and <= MaximumDie;

        #endregion

        private static class Scores
        {
            public const int YahtzeeScore = 50;
            public const int HouseScore = 25;
            public const int LargeStraightScore = 40;
        }
    }
}