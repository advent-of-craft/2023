using FluentAssertions;
using Xunit;
using static System.IO.File;

namespace Day24.Tests
{
    // Original specifications available here : https://adventofcode.com/2021/day/2
    public class SubmarineTests
    {
        [Fact]
        public void Should_Move_On_Given_Instructions()
            => new K991_P()
                .ToString(LoadInstructions())
                .CalculateResult()
                .Should()
                .Be(1_690_020);

        private static IEnumerable<A> LoadInstructions()
            => ReadAllLines("submarine.txt")
                .Select(A.f);
    }

    internal static class SubmarineExtensions
    {
        public static int CalculateResult(this K991_P k991P)
            => k991P.B().b * k991P.B().a;
    }
}