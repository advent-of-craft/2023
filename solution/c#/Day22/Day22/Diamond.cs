using LanguageExt;
using static System.Environment;
using static System.Linq.Enumerable;
using static System.String;

namespace Day22
{
    public static class Diamond
    {
        private const char Start = 'A';
        public static Option<string> ToDiamond(this char c) => Print(c);

        private static Option<string> Print(char c)
            => IsValidCharacter(c)
                ? FullDiamondSafely(c)
                : Option<string>.None;

        private static bool IsValidCharacter(char endCharacter)
            => endCharacter is >= Start and <= 'Z';

        private static string FullDiamondSafely(char endCharacter)
            => Join(NewLine, GenerateDiamond(endCharacter));

        private static Seq<string> GenerateDiamond(char endCharacter)
            => GenerateHalfDiamond(endCharacter)
                .Apply(halfDiamond => halfDiamond.Concat(
                    halfDiamond.Reverse().Tail()
                ));

        private static Seq<string> GenerateHalfDiamond(char endCharacter)
            => Range(Start, endCharacter - Start + 1).ToSeq()
                .Map(i => (char) i)
                .Map(c => ToLine(c, endCharacter));

        private static string ToLine(char character, char endCharacter)
            => Outer(character, endCharacter)
                .Apply(@out => @out + character + Inner(character) + @out);

        private static string Outer(char character, char endCharacter)
            => GenerateEmptyCharacters(endCharacter - character);

        private static string Inner(char character)
            => character != Start
                ? GenerateEmptyCharacters(NumberOfEmptyCharactersFor(character)) + character
                : "";

        private static int NumberOfEmptyCharactersFor(char character)
            => (character - Start) * 2 - 1;

        private static string GenerateEmptyCharacters(int count)
            => Range(0, count).ToSeq()
                .Fold("", (acc, _) => acc + " ");
    }

    public static class FunctionalExtensions
    {
        public static T Apply<T>(this T obj, Func<T, T> action) => action(obj);
    }
}