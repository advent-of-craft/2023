namespace Day01
{
    public static class DateExtensions
    {
        public static bool IsGreaterThan(this DateOnly date, DateOnly other) => date.CompareTo(other) > 0;
    }
}