using FluentAssertions;
using Xunit;

namespace Day08.Tests
{
    public class PasswordValidationTests
    {
        [Theory]
        [InlineData("P@ssw0rd")]
        [InlineData("Advent0fCraft&")]
        public void Success_For_A_Valid_Password(string password)
            => password
                .IsValidPassword()
                .Should()
                .BeTrue();

        public class FailWhen
        {
            [Theory]
            [InlineData("", "Too short")]
            [InlineData("aa", "Too short")]
            [InlineData("xxxxxxx", "Too short")]
            [InlineData("adventofcraft", "No capital letter")]
            [InlineData("p@ssw0rd", "No capital letter")]
            [InlineData("ADVENTOFCRAFT", "No lower letter")]
            [InlineData("P@SSW0RD", "No lower letter")]
            [InlineData("Adventofcraft", "No number")]
            [InlineData("P@sswOrd", "No number")]
            [InlineData("Adventof09craft", "No special character")]
            [InlineData("PAssw0rd", "No special character")]
            [InlineData("Advent@of9Craft¨", "Invalid character")]
            [InlineData("P@ssw^rd", "Invalid character")]
            public void Password_Is_Not_Valid(string password, string reason)
                => password
                    .IsValidPassword()
                    .Should()
                    .BeFalse(reason);
        }
    }
}