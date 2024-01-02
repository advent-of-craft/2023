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
            => new Submarine()
                .Move(LoadInstructions())
                .CalculateResult()
                .Should()
                .Be(1_690_020);

        private static IEnumerable<Instruction> LoadInstructions()
            => ReadAllLines("submarine.txt")
                .Select(Instruction.FromText);
    }

    internal static class SubmarineExtensions
    {
        public static int CalculateResult(this Submarine submarine)
            => submarine.Position().Depth * submarine.Position().Horizontal;
    }
}