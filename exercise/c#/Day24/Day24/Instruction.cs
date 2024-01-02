namespace Day24
{
    public record Instruction(string Text, int X)
    {
        public static Instruction FromText(string text)
        {
            var split = text.Split(" ");
            return new Instruction(split[0], int.Parse(split[1]));
        }
    }
}