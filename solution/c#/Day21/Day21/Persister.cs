namespace Day21
{
    public sealed class Persister
    {
        public FileContent[] ReadDirectory(string directoryName)
            => Directory
                .GetFiles(directoryName)
                .Select(x => new FileContent(
                    Path.GetFileName(x),
                    File.ReadAllLines(x)))
                .ToArray();

        public void ApplyUpdate(string directoryName, FileUpdate update)
            => File.WriteAllText(
                Path.Combine(directoryName, update.FileName),
                update.NewContent);
    }
}