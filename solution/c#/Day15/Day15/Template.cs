namespace Day15
{
    public record Template(
        DocumentTemplate DocumentTemplate,
        RecordType RecordType,
        string DocumentType)
    {
        public override string ToString() => DocumentTemplate.ToString();
    }
}