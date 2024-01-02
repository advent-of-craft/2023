namespace Day07.CI.Dependencies
{
    public interface ILogger
    {
        void Info(string message);
        void Error(string message);
    }
}