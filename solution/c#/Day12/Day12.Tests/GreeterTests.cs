using FluentAssertions;
using Xunit;
using static Day12.GreeterFactory;

namespace Day12.Tests
{
    public class GreeterTests
    {
        [Theory]
        [InlineData(GreeterFactory.Casual, "Sup bro?")]
        [InlineData(GreeterFactory.Formal, "Good evening, sir.")]
        [InlineData(GreeterFactory.Intimate, "Hello Darling!")]
        [InlineData("Random string", "Hello.")]
        public void Greet(string greeter, string expectedGreeter)
            => Create(greeter)
                .Greet()
                .Should().Be(expectedGreeter);
    }
}