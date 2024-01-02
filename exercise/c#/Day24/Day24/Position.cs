namespace Day24
{
    public record Position(int Horizontal, int Depth)
    {
        public Position ChangeDepth(int newDepth) => this with {Depth = newDepth};
        public Position MoveHorizontally(int newHorizontal) => this with {Horizontal = newHorizontal};
    }
}