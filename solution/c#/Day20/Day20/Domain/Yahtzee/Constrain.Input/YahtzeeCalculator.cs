namespace Day20.Domain.Yahtzee.Constrain.Input
{
    public static class YahtzeeCalculator
    {
        public static int Number(Roll roll, int number)
            => Calculate(r => r.Dice.Where(die => die == number).Sum(), roll);

        public static int ThreeOfAKind(Roll roll) => CalculateNOfAKind(roll, 3);
        public static int FourOfAKind(Roll roll) => CalculateNOfAKind(roll, 4);

        public static int Yahtzee(Roll roll)
            => Calculate(r =>
                    HasNOfAKind(r, 5)
                        ? Scores.YahtzeeScore
                        : 0,
                roll);

        private static int CalculateNOfAKind(Roll roll, int n)
            => Calculate(r => HasNOfAKind(r, n) ? r.Dice.Sum() : 0, roll);

        public static int FullHouse(Roll roll)
        {
            return Calculate(r =>
            {
                var dieFrequency = r.GroupDieByFrequency();
                return dieFrequency.ContainsValue(3) && dieFrequency.ContainsValue(2) ? Scores.HouseScore : 0;
            }, roll);
        }

        public static int LargeStraight(Roll roll)
            => Calculate(r => r.Dice
                .OrderBy(x => x)
                .Zip(
                    r.Dice.OrderBy(x => x).Skip(1),
                    (a, b) => b - a
                ).All(diff => diff == 1)
                ? Scores.LargeStraightScore
                : 0, roll);

        public static int SmallStraight(Roll roll)
            => Calculate(r =>
            {
                var sortedDice = string.Concat(r.Dice.OrderBy(x => x).Distinct());
                return IsSmallStraight(sortedDice) ? 30 : 0;
            }, roll);

        private static bool IsSmallStraight(string diceString)
            => diceString.Contains("1234") || diceString.Contains("2345") || diceString.Contains("3456");

        private static bool HasNOfAKind(Roll roll, int n)
            => roll.GroupDieByFrequency().Values.Any(count => count >= n);

        public static int Chance(Roll roll) => Calculate(r => r.Dice.Sum(), roll);

        private static int Calculate(Func<Roll, int> compute, Roll roll) => compute(roll);

        private static class Scores
        {
            public const int YahtzeeScore = 50;
            public const int HouseScore = 25;
            public const int LargeStraightScore = 40;
        }
    }
}