using FluentAssertions.LanguageExt;
using Xunit;

namespace Day14.Tests.LangExt
{
    public class FizzBuzzTests
    {
        [Theory]
        [InlineData(1, "1")]
        [InlineData(67, "67")]
        [InlineData(82, "82")]
        [InlineData(3, "Fizz")]
        [InlineData(66, "Fizz")]
        [InlineData(99, "Fizz")]
        [InlineData(5, "Buzz")]
        [InlineData(50, "Buzz")]
        [InlineData(85, "Buzz")]
        [InlineData(15, "FizzBuzz")]
        [InlineData(30, "FizzBuzz")]
        [InlineData(45, "FizzBuzz")]
        public void Returns_Number_Representation(int input, string expectedResult)
            => Day14.LangExt.FizzBuzz
                .Convert(input)
                .Should()
                .BeSome(expectedResult);

        [Theory]
        [InlineData(0)]
        [InlineData(101)]
        [InlineData(-1)]
        public void Fails_For_Numbers_Out_Of_Range(int input)
            => Day14.LangExt.FizzBuzz
                .Convert(input)
                .Should()
                .BeNone();
    }
}