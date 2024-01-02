using FsCheck;
using FsCheck.Xunit;
using LanguageExt;
using LanguageExt.UnsafeValueAccess;
using static System.Environment;
using static System.Linq.Enumerable;

namespace Day22.Tests
{
    public class DiamondTests
    {
        private const char EmptyCharacter = ' ';

        private static Arbitrary<char> UpperLetterGenerator()
            => Arb.Default.Char().Filter(c => c is >= 'A' and <= 'Z');

        [Property]
        public void Be_Horizontally_Symmetric()
            => CheckProperty((diamond, _)
                => diamond.Rev().SequenceEqual(diamond)
            );

        [Property]
        public void Be_A_Square()
            => CheckProperty((diamond, _)
                => diamond.All(line => line.Length == diamond.Length)
            );

        [Property]
        public void Contains_2_Identical_Letters_Per_Line()
            => CheckProperty((diamond, _)
                => diamond.Skip(1).Rev().Skip(1)
                    .Map(line => line.Replace(" ", ""))
                    .All(line => line.Length == 2 && line[0] == line[1])
            );


        [Property]
        public void Decreasing_Number_Of_Left_White_Spaces()
            => CheckProperty((diamond, endCharacter) =>
                {
                    var halfDiamond = HalfDiamond(diamond, endCharacter);
                    var spaces = CountSpacesBeforeFirstLetterPerLine(halfDiamond);

                    return AreSpacesPerLineMatch(halfDiamond, spaces);
                }
            );

        [Property]
        public void Decreasing_Number_Of_Right_White_Spaces()
            => CheckProperty((diamond, endCharacter) =>
                {
                    var halfDiamond = HalfDiamond(diamond, endCharacter);
                    var spaces = CountSpacesAfterLastLetterPerLine(halfDiamond);

                    return AreSpacesPerLineMatch(halfDiamond, spaces);
                }
            );

        private static Seq<string> HalfDiamond(Seq<string> diamond, char endCharacter)
            => diamond.Take(endCharacter - 'A' + 1);

        private static Seq<int> CountSpacesBeforeFirstLetterPerLine(Seq<string> halfDiamond)
            => CountSpacesOnLine(halfDiamond, line => line);

        private static Seq<int> CountSpacesAfterLastLetterPerLine(Seq<string> halfDiamond)
            => CountSpacesOnLine(
                halfDiamond,
                line => new string(line.Reverse().ToArray())
            );

        private static Seq<int> CountSpacesOnLine(Seq<string> halfDiamond, Func<string, string> mapLine)
            => halfDiamond
                .Map(line => mapLine(line).ToSeq().TakeWhile(c => c == EmptyCharacter))
                .Map(line => line.Length);

        private static bool AreSpacesPerLineMatch(Seq<string> halfDiamond, Seq<int> spaces)
        {
            var expectedSpaceOnLine = halfDiamond.Length;
            return Range(0, halfDiamond.Length - 1)
                .All(index =>
                {
                    expectedSpaceOnLine--;
                    return spaces[index] == expectedSpaceOnLine;
                });
        }

        private static void CheckProperty(Func<Seq<string>, char, bool> property)
            => Prop.ForAll(
                UpperLetterGenerator(),
                endCharacter => endCharacter.ToDiamond()
                    .Map(diamond => diamond.Split(NewLine).ToSeq())
                    .Exists(diamond => property(diamond, endCharacter))
            ).QuickCheckThrowOnFailure();

        public class Fail
        {
            private static Arbitrary<char> NotALetterGenerator()
                => Arb.Default.Char()
                    .Filter(c => c is < 'A' or > 'Z');

            [Property]
            public void For_Invalid_End_Character()
            {
                Prop.ForAll(
                    NotALetterGenerator(),
                    endCharacter => endCharacter.ToDiamond().IsNone
                ).QuickCheckThrowOnFailure();
            }
        }

        [UsesVerify]
        public class Approval
        {
            [Fact]
            public Task Generate_A_Diamond()
                => Verify('K'.ToDiamond().ValueUnsafe());
        }
    }
}