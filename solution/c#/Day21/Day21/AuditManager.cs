namespace Day21
{
    public sealed class AuditManager(int maxEntriesPerFile)
    {
        private const string FirstFileName = "audit_1.txt";

        public FileUpdate AddRecord(
            FileContent[] files,
            string visitorName,
            DateTime timeOfVisit)
        {
            var sorted = SortByIndex(files);
            var newRecord = NewRecord(visitorName, timeOfVisit);

            return sorted switch
            {
                {Length: 0} => CreateFirstFile(newRecord),
                _ => CreateNewFileOrUpdate(sorted.Last(), newRecord)
            };
        }

        private static FileUpdate CreateFirstFile(string newRecord)
            => new(FirstFileName, newRecord);

        private FileUpdate CreateNewFileOrUpdate((int, FileContent) currentFile, string newRecord)
        {
            var (fileIndex, fileContent) = currentFile;
            return fileContent.Lines.Length < maxEntriesPerFile
                ? AppendToExistingFile(fileContent, newRecord)
                : CreateANewFile(fileIndex, newRecord);
        }

        private static FileUpdate AppendToExistingFile(FileContent currentFile, string newRecord)
        {
            var lines = currentFile.Lines.ToList();
            lines.Add(newRecord);
            var newContent = string.Join(Environment.NewLine, lines);

            return new FileUpdate(currentFile.FileName, newContent);
        }

        private static FileUpdate CreateANewFile(int currentFileIndex, string newRecord)
        {
            var newIndex = currentFileIndex + 1;
            var newName = $"audit_{newIndex}.txt";

            return new FileUpdate(newName, newRecord);
        }

        private static string NewRecord(string visitorName, DateTime timeOfVisit)
            => visitorName + ';' + timeOfVisit.ToString("yyyy-MM-dd HH:mm:ss");

        private static (int index, FileContent)[] SortByIndex(FileContent[] files)
            => files
                .AsEnumerable()
                .Select((content, index) => (index + 1, content))
                .ToArray();
    }

    public record FileUpdate(string FileName, string NewContent);

    public record FileContent(string FileName, string[] Lines);
}