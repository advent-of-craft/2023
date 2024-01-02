namespace Day08
{
    public static class PasswordValidation
    {
        private const int MinimumLength = 8;
        private static readonly List<char> SpecialCharacters = ['.', '*', '#', '@', '$', '%', '&'];

        public static bool IsValidPassword(this string input) => Validate(input);

        private static bool Validate(string password)
            => GreaterThanMinimumLength(password) &&
               AtLeastOne(password,
                   char.IsUpper,
                   char.IsLower,
                   char.IsDigit,
                   SpecialCharacters.Contains
               )
               && NoInvalidCharacter(password);

        private static bool GreaterThanMinimumLength(string password)
            => password.Length >= MinimumLength;

        private static bool AtLeastOne(string password, params Func<char, bool>[] predicates)
            => predicates.All(password.Any);

        private static bool NoInvalidCharacter(string password)
            => password.All(IsAValidCharacter);

        private static bool IsAValidCharacter(char c) =>
            char.IsLetter(c)
            || char.IsDigit(c)
            || SpecialCharacters.Contains(c);
    }
}