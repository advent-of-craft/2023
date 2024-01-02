namespace Day12
{
    public static class GreeterFactory
    {
        public const string Casual = "casual";
        public const string Intimate = "intimate";
        public const string Formal = "formal";

        private static IGreeter Create() => new Default();

        public static IGreeter Create(string formality)
            => formality switch
            {
                Casual => new Casual(),
                Intimate => new Intimate(),
                Formal => new Formal(),
                _ => Create()
            };
    }

    public interface IGreeter
    {
        public string Greet() => "Hello.";
    }

    public class Default : IGreeter;

    public class Formal : IGreeter
    {
        public string Greet() => "Good evening, sir.";
    }

    public class Casual : IGreeter
    {
        public string Greet() => "Sup bro?";
    }

    public class Intimate : IGreeter
    {
        public string Greet() => "Hello Darling!";
    }
}