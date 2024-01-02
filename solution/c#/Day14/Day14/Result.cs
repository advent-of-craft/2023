namespace Day14
{
    public record Result<T>
    {
        private readonly T? _success;
        private Result(T success) => _success = success;

        private Result()
        {
        }

        public static Result<T> FromSuccess(T success) => new(success);
        public static Result<T> Failure() => new();

        public T? Success() => _success;
        public bool Failed() => _success == null;
    }
}