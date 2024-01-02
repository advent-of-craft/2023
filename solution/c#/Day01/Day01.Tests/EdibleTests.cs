using FluentAssertions;
using Xunit;

namespace Day01.Tests
{
    public class EdibleTests
    {
        private static readonly DateOnly ExpirationDate = new(2023, 12, 1);
        private static readonly Guid Inspector = Guid.NewGuid();
        private static readonly DateOnly NotFreshDate = ExpirationDate.AddDays(7);
        private static readonly DateOnly FreshDate = ExpirationDate.AddDays(-7);

        public static IEnumerable<object?[]> NotEdibleFood()
        {
            return new List<object?[]>
            {
                new object[] {true, Inspector, NotFreshDate},
                new object[] {false, Inspector, FreshDate},
                new object?[] {true, null, FreshDate},
                new object?[] {false, null, NotFreshDate},
                new object?[] {false, null, FreshDate}
            };
        }

        [Theory]
        [MemberData(nameof(NotEdibleFood))]
        public void Not_Edible_If_Not_Fresh(bool approvedForConsumption, Guid? inspectorId, DateOnly now)
            => new Food(ExpirationDate, approvedForConsumption, inspectorId)
                .IsEdible(() => now)
                .Should()
                .BeFalse();

        [Fact]
        public void Edible_Food()
            => new Food(ExpirationDate, true, Inspector)
                .IsEdible(() => FreshDate)
                .Should()
                .BeTrue();
    }
}