namespace Day12.Functional
{
    public static class GreeterFactory
    {
        public const string Casual = "casual";
        public const string Intimate = "intimate";
        public const string Formal = "formal";

        private static readonly IReadOnlyDictionary<string, Greeter> Mapping =
            new Dictionary<string, Greeter>
            {
                {Casual, () => "Sup bro?"},
                {Formal, () => "Good evening, sir."},
                {Intimate, () => "Hello Darling!"},
            };

        public static Greeter Create(string formality)
            => Mapping.GetValueOrDefault(formality, () => "Hello.");
    }

    public delegate string Greeter();
}