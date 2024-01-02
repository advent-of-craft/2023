namespace Day12
{
    public class Greeter
    {
        public string? Formality { get; set; }

        public string Greet()
        {
            if (Formality == null)
            {
                return "Hello.";
            }

            if (Formality == "formal")
            {
                return "Good evening, sir.";
            }
            else if (Formality == "casual")
            {
                return "Sup bro?";
            }
            else if (Formality == "intimate")
            {
                return "Hello Darling!";
            }
            else
            {
                return "Hello.";
            }
        }
    }
}