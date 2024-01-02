namespace Day02
{
    public static class FizzBuzz
    {
        public static string Convert(int input)
        {
            if (input > 0)
            {
                if (input <= 100)
                {
                    if (input % 3 == 0 && input % 5 == 0)
                    {
                        return "FizzBuzz";
                    }

                    if (input % 3 == 0)
                    {
                        return "Fizz";
                    }

                    if (input % 5 == 0)
                    {
                        return "Buzz";
                    }

                    return input.ToString();
                }
                else
                {
                    throw new OutOfRangeException();
                }
            }
            else
            {
                throw new OutOfRangeException();
            }
        }
    }
}