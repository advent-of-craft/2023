namespace Day20.Domain.Yahtzee.Hollywood.Principle
{
    public static class YahtzeeCalculator
    {
        private const int RollLength = 5;
        private const int MinimumDie = 1;
        private const int MaximumDie = 6;

        public static void Number(
            int[] dice,
            int number,
            Action<int> onSuccess,
            Action<string> onError)
            => Calculate(d => d.Where(die => die == number).Sum(), dice, onSuccess, onError);

        public static void ThreeOfAKind(
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError) => CalculateNOfAKind(dice, 3, onSuccess, onError);

        public static void FourOfAKind(
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError) => CalculateNOfAKind(dice, 4, onSuccess, onError);

        public static void Yahtzee(
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError)
            => Calculate(d =>
                    HasNOfAKind(d, 5)
                        ? Scores.YahtzeeScore
                        : 0,
                dice, onSuccess, onError);

        private static void CalculateNOfAKind(
            int[] dice,
            int n,
            Action<int> onSuccess,
            Action<string> onError)
            => Calculate(d => HasNOfAKind(d, n) ? d.Sum() : 0, dice, onSuccess, onError);

        public static void FullHouse(
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError)
            => Calculate(d =>
            {
                var dieFrequency = GroupDieByFrequency(d);
                return dieFrequency.ContainsValue(3) && dieFrequency.ContainsValue(2) ? Scores.HouseScore : 0;
            }, dice, onSuccess, onError);

        public static void LargeStraight(
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError)
            => Calculate(d => d
                .OrderBy(x => x)
                .Zip(
                    d.OrderBy(x => x).Skip(1),
                    (a, b) => b - a
                ).All(diff => diff == 1)
                ? Scores.LargeStraightScore
                : 0, dice, onSuccess, onError);

        public static void SmallStraight(
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError)
            => Calculate(d =>
            {
                var sortedDice = string.Concat(d.OrderBy(x => x).Distinct());
                return IsSmallStraight(sortedDice) ? 30 : 0;
            }, dice, onSuccess, onError);

        private static bool IsSmallStraight(string diceString)
            => diceString.Contains("1234") || diceString.Contains("2345") || diceString.Contains("3456");

        private static bool HasNOfAKind(IEnumerable<int> dice, int n)
            => GroupDieByFrequency(dice).Values.Any(count => count >= n);

        public static void Chance(
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError) => Calculate(d => d.Sum(), dice, onSuccess, onError);

        private static Dictionary<int, int> GroupDieByFrequency(IEnumerable<int> dice)
            => dice.GroupBy(x => x).ToDictionary(g => g.Key, g => g.Count());

        private static void Calculate(
            Func<List<int>, int> compute,
            int[] dice,
            Action<int> onSuccess,
            Action<string> onError)
        {
            if (ValidateRoll(dice, onError))
                onSuccess(compute(dice.ToList()));
        }

        #region Validation

        private static bool ValidateRoll(int[] dice, Action<string> onError)
        {
            if (HasInvalidLength(dice))
            {
                onError("Invalid dice... A roll should contain 5 dice.");
                return false;
            }

            if (ContainsInvalidDie(dice))
            {
                onError("Invalid die value. Each die must be between 1 and 6.");
                return false;
            }

            return true;
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