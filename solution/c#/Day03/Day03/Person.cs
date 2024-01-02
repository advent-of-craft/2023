namespace Day03
{
    public record Person(string FirstName, string LastName, params Pet[] Pets);

    public record Pet(PetType Type, string Name, int Age);

    public enum PetType
    {
        Cat,
        Dog,
        Hamster,
        Turtle,
        Bird,
        Snake
    }
}