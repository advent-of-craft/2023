using FluentAssertions;
using Xunit;

namespace Day06.Tests
{
    public class FizzBuzzTests
    {
        #region "Normal" numbers

        [Fact]
        public void Returns_The_Given_Number_For_1() => FizzBuzz.Convert(1).Should().Be("1");

        [Fact]
        public void Returns_The_Given_Number_For_67() => FizzBuzz.Convert(67).Should().Be("67");

        [Fact]
        public void Returns_The_Given_Number_For_82() => FizzBuzz.Convert(82).Should().Be("82");

        #endregion

        #region Fizz

        [Fact]
        public void Returns_Fizz_For_3() => FizzBuzz.Convert(3).Should().Be("Fizz");

        [Fact]
        public void Returns_Fizz_For_66() => FizzBuzz.Convert(66).Should().Be("Fizz");

        [Fact]
        public void Returns_Fizz_For_99() => FizzBuzz.Convert(99).Should().Be("Fizz");

        #endregion

        #region Buzz

        [Fact]
        public void Returns_Buzz_For_5() => FizzBuzz.Convert(5).Should().Be("Buzz");

        [Fact]
        public void Returns_Buzz_For_50() => FizzBuzz.Convert(50).Should().Be("Buzz");

        [Fact]
        public void Returns_Buzz_For_85() => FizzBuzz.Convert(85).Should().Be("Buzz");

        #endregion

        #region FizzBuzz

        [Fact]
        public void Returns_FizzBuzz_For_15() => FizzBuzz.Convert(15).Should().Be("FizzBuzz");

        [Fact]
        public void Returns_FizzBuzz_For_30() => FizzBuzz.Convert(30).Should().Be("FizzBuzz");

        [Fact]
        public void Returns_FizzBuzz_For_45() => FizzBuzz.Convert(45).Should().Be("FizzBuzz");

        #endregion

        #region Failures

        [Fact]
        public void Throws_An_Exception_For_0()
        {
            var act = () => FizzBuzz.Convert(0);
            act.Should().Throw<OutOfRangeException>();
        }

        [Fact]
        public void Throws_An_Exception_For_101()
        {
            var act = () => FizzBuzz.Convert(101);
            act.Should().Throw<OutOfRangeException>();
        }

        [Fact]
        public void Throws_An_Exception_For_Minus_1()
        {
            var act = () => FizzBuzz.Convert(-1);
            act.Should().Throw<OutOfRangeException>();
        }

        #endregion
    }
}