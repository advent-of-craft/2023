using FluentAssertions;
using Xunit;
using static System.Environment;
using static System.String;
using static Day05.PetType;

namespace Day05.Tests
{
    public class PopulationTests
    {
        private static readonly IEnumerable<Person> Population = new List<Person>
        {
            new("Peter", "Griffin", new Pet(Cat, "Tabby", 2)),
            new("Stewie", "Griffin", new Pet(Cat, "Dolly", 3), new Pet(Dog, "Brian", 9)),
            new("Joe", "Swanson", new Pet(Dog, "Spike", 4)),
            new("Lois", "Griffin", new Pet(Snake, "Serpy", 1)),
            new("Meg", "Griffin", new Pet(Bird, "Tweety", 1)),
            new("Chris", "Griffin", new Pet(Turtle, "Speedy", 4)),
            new("Cleveland", "Brown", new Pet(Hamster, "Fuzzy", 1), new Pet(Hamster, "Wuzzy", 2)),
            new("Glenn", "Quagmire")
        };

        [Fact]
        public void People_With_Their_Pets()
            => FormatPopulation()
                .Should()
                .Be("""
                    Peter Griffin who owns : Tabby
                    Stewie Griffin who owns : Dolly Brian
                    Joe Swanson who owns : Spike
                    Lois Griffin who owns : Serpy
                    Meg Griffin who owns : Tweety
                    Chris Griffin who owns : Speedy
                    Cleveland Brown who owns : Fuzzy Wuzzy
                    Glenn Quagmire
                    """);

        private static string FormatPopulation() => Join(NewLine, Population.Select(FormatPerson));

        private static string FormatPerson(Person person) =>
            $"{person.FirstName} {person.LastName}{FormatPets(person)}";

        private static string FormatPets(Person person)
            => person.Pets.Length > 0
                ? $" who owns : {Join(' ', person.Pets.Select(pet => pet.Name))}"
                : Empty;

        [Fact]
        public void Who_Owns_The_Youngest_Pet()
        {
            var filtered = Population.MinBy(YoungestPetAgeOfThePerson);

            filtered.Should().NotBeNull();
            filtered!.FirstName.Should().Be("Lois");
        }

        private static int YoungestPetAgeOfThePerson(Person person) =>
            person.Pets.MinBy(p => p.Age)?.Age ?? int.MaxValue;
    }
}