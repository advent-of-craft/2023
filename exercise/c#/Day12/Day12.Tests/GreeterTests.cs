using FluentAssertions;
using Xunit;

namespace Day12.Tests
{
    public class GreeterTests
    {
        [Fact]
        public void SaysHello()
        {
            var greeter = new Greeter();
            greeter.Greet().Should().Be("Hello.");
        }

        [Fact]
        public void SaysHelloFormally()
        {
            var greeter = new Greeter
            {
                Formality = "formal"
            };

            greeter.Greet().Should().Be("Good evening, sir.");
        }

        [Fact]
        public void SaysHelloCasually()
        {
            var greeter = new Greeter
            {
                Formality = "casual"
            };

            greeter.Greet().Should().Be("Sup bro?");
        }

        [Fact]
        public void SaysHelloIntimately()
        {
            var greeter = new Greeter
            {
                Formality = "intimate"
            };

            greeter.Greet().Should().Be("Hello Darling!");
        }
    }
}