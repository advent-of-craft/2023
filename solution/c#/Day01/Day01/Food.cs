namespace Day01
{
    using DateProvider = Func<DateOnly>;

    public sealed record Food(
        DateOnly ExpirationDate,
        bool ApprovedForConsumption,
        Guid? InspectorId)
    {
        public bool IsEdible(DateProvider now)
            => IsFresh(now) &&
               CanBeConsumed() &&
               HasBeenInspected();

        private bool IsFresh(DateProvider now) => ExpirationDate.IsGreaterThan(now());
        private bool CanBeConsumed() => ApprovedForConsumption;
        private bool HasBeenInspected() => InspectorId != null;
    }
}