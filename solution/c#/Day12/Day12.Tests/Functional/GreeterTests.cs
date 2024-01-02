using FluentAssertions;
using Xunit;
using static Day12.Functional.GreeterFactory;

namespace Day12.Tests.Functional
{
    public class GreeterTests
    {
        [Theory]
        [InlineData(Day12.Functional.GreeterFactory.Casual, "Sup bro?")]
        [InlineData(Day12.Functional.GreeterFactory.Formal, "Good evening, sir.")]
        [InlineData(Day12.Functional.GreeterFactory.Intimate, "Hello Darling!")]
        [InlineData("Random string", "Hello.")]
        public void Greet(string greeter, string expectedGreeter)
            => Create(greeter)
                ()
                .Should().Be(expectedGreeter);
    }
}