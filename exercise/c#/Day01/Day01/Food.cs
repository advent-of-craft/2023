namespace Day01
{
    public record Food(
        DateOnly ExpirationDate,
        bool ApprovedForConsumption,
        Guid? InspectorId)
    {
        public bool IsEdible(Func<DateOnly> now)
        {
            if (ExpirationDate.CompareTo(now()) > 0 &&
                ApprovedForConsumption &&
                InspectorId != null)
                return true;
            return false;
        }
    }
}