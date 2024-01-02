namespace Day24
{
    public class Submarine(Position position)
    {
        public Submarine(int horizontal = 0, int depth = 0)
            : this(new Position(horizontal, depth))
        {
        }

        public Submarine Move(IEnumerable<Instruction> instructions)
            => instructions
                .Aggregate(this, (submarine, instruction) => submarine.Move(instruction));

        private Submarine Move(Instruction instruction)
            => new(instruction.Text switch
            {
                "down" => position.ChangeDepth(position.Depth + instruction.X),
                "up" => position.ChangeDepth(position.Depth - instruction.X),
                _ => position.MoveHorizontally(position.Horizontal + instruction.X)
            });

        public Position Position() => position;
    }
}