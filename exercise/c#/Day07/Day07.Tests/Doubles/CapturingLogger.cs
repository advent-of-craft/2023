using Day07.CI.Dependencies;

namespace Day07.Tests.Doubles
{
    public class CapturingLogger : ILogger
    {
        private readonly List<string> _lines = [];
        public void Info(string message) => _lines.Add($"INFO: {message}");
        public void Error(string message) => _lines.Add($"ERROR: {message}");
        public IReadOnlyList<string> LoggedLines => _lines;
    }
}