using Day08.Functional;
using FluentAssertions;
using FluentAssertions.LanguageExt;
using LanguageExt.UnsafeValueAccess;
using Xunit;

namespace Day08.Tests.Functional
{
    public class PasswordTests
    {
        [Theory]
        [InlineData("P@ssw0rd")]
        [InlineData("Advent0fCraft&")]
        public void Success_For_A_Valid_Password(string password)
            => Password.Parse(password)
                .Should()
                .BeRight(p => p.ToString().Should().Be(password));

        [Fact]
        public void Value_Equality()
        {
            const string input = "P@ssw0rd";
            var password = Password.Parse(input).ValueUnsafe();
            var other = Password.Parse(input).ValueUnsafe();

            password.Equals(other).Should().BeTrue();
            (password == other).Should().BeTrue();
            (password != other).Should().BeFalse();
        }

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
            [InlineData("Advent@of9Craft/", "Invalid character")]
            [InlineData("P@ssw^rd1", "Invalid character")]
            public void Password_Is_Not_Valid(string password, string reason)
                => Password.Parse(password)
                    .Should()
                    .BeLeft(error => error.Reason.Should().Be(reason));
        }
    }
}