namespace Day02
{
    public static class FizzBuzz
    {
        private const int Min = 0;
        private const int Max = 100;
        private const int Fizz = 3;
        private const int Buzz = 5;
        private const int Fizz_Buzz = 15;

        public static string Convert(int input)
            => IsOutOfRange(input)
                ? throw new OutOfRangeException()
                : ConvertSafely(input);

        private static string ConvertSafely(int input)
        {
            if (Is(Fizz_Buzz, input))
                return "FizzBuzz";
            if (Is(Fizz, input))
                return "Fizz";
            if (Is(Buzz, input))
                return "Buzz";

            return input.ToString();
        }

        private static bool Is(int divisor, int input) => input % divisor == 0;

        private static bool IsOutOfRange(int input) => input is <= Min or > Max;
    }
}