using System.Text.RegularExpressions;
using FluentAssertions;
using FluentAssertions.LanguageExt;
using FsCheck;
using FsCheck.Xunit;
using Xunit;

namespace Day11.Tests
{
    public class RomanNumeralsTests
    {
        [Theory]
        [InlineData(1, "I")]
        [InlineData(3, "III")]
        [InlineData(4, "IV")]
        [InlineData(5, "V")]
        [InlineData(10, "X")]
        [InlineData(13, "XIII")]
        [InlineData(50, "L")]
        [InlineData(100, "C")]
        [InlineData(500, "D")]
        [InlineData(1000, "M")]
        [InlineData(2499, "MMCDXCIX")]
        public void Generate_Roman_For_Numbers(int number, String expectedRoman)
            => number.ToRoman()
                .Should()
                .BeSome(roman => roman.Should().Be(expectedRoman));

        private readonly Arbitrary<int> _invalidNumbers = Arb.Default.Int32().Filter(x => x is <= 0 or > 3999);

        [Property]
        public void Returns_None_For_Any_Number_Out_Of_Range()
            => Prop.ForAll(
                _invalidNumbers,
                x => x.ToRoman().IsNone
            ).QuickCheckThrowOnFailure();

        [Property]
        public void Returns_Only_Valid_Symbols_For_Valid_Numbers()
            => Prop.ForAll(
                Arb.From(Gen.Choose(1, 3999)),
                x => x.ToRoman().Exists(RomanCharactersAreValid)
            ).QuickCheckThrowOnFailure();

        private static bool RomanCharactersAreValid(string input) => Regex.Match(input, "[IVXLCDM]+").Success;
    }
}