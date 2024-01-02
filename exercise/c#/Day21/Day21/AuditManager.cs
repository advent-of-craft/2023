namespace Day21
{
    public class AuditManager
    {
        private readonly int _maxEntriesPerFile;
        private readonly string _directoryName;
        private readonly IFileSystem _fileSystem;

        public AuditManager(
            int maxEntriesPerFile,
            string directoryName,
            IFileSystem fileSystem)
        {
            _maxEntriesPerFile = maxEntriesPerFile;
            _directoryName = directoryName;
            _fileSystem = fileSystem;
        }

        public void AddRecord(string visitorName, DateTime timeOfVisit)
        {
            var filePaths = _fileSystem.GetFiles(_directoryName);
            var sorted = SortByIndex(filePaths);
            var newRecord = visitorName + ';' + timeOfVisit.ToString("yyyy-MM-dd HH:mm:ss");

            if (sorted.Length == 0)
            {
                var newFile = Path.Combine(_directoryName, "audit_1.txt");
                _fileSystem.WriteAllText(newFile, newRecord);

                return;
            }

            var (currentFileIndex, currentFilePath) = sorted.Last();
            var lines = _fileSystem.ReadAllLines(currentFilePath).ToList();

            if (lines.Count < _maxEntriesPerFile)
            {
                lines.Add(newRecord);
                var newContent = string.Join(Environment.NewLine, lines);
                _fileSystem.WriteAllText(currentFilePath, newContent);
            }
            else
            {
                var newIndex = currentFileIndex + 1;
                var newName = $"audit_{newIndex}.txt";
                var newFile = Path.Combine(_directoryName, newName);
                _fileSystem.WriteAllText(newFile, newRecord);
            }
        }

        private static (int index, string path)[] SortByIndex(string[] filePaths)
            => filePaths
                .OrderBy(x => x)
                .Select((path, index) => (index + 1, path))
                .ToArray();
    }
}