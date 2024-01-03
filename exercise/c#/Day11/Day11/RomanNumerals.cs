using System.Text;
using LanguageExt;
using static LanguageExt.Option<string>;

namespace Day11
{
    public static class RomanNumerals
    {
        private const int MaxNumber = 3999;

        private static readonly IReadOnlyDictionary<int, string> IntToNumerals = new Dictionary<int, string>()
        {
            {1000, "M"},
            {900, "CM"},
            {500, "D"},
            {400, "CD"},
            {100, "C"},
            {90, "XC"},
            {50, "L"},
            {40, "XL"},
            {10, "X"},
            {9, "IX"},
            {5, "V"},
            {4, "IV"},
            {1, "I"},
        };

        public static Option<string> ToRoman(this int number) => Convert(number);

        private static Option<string> Convert(int number)
            => IsInRange(number)
                ? ConvertSafely(number)
                : None;

        private static string ConvertSafely(int number)
        {
            var roman = new StringBuilder();
            var remaining = number;

            foreach (var toRoman in IntToNumerals)
            {
                while (remaining >= toRoman.Key)
                {
                    roman.Append(toRoman.Value);
                    remaining -= toRoman.Key;
                }
            }

            return roman.ToString();
        }

        private static bool IsInRange(int number) => number is > 0 and <= MaxNumber;
    }
}