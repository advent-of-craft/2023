namespace Day15
{
    public static class Templates
    {
        private static readonly Template Spec = new(DocumentTemplate.SPEC, RecordType.All, "SPEC");

        private static readonly IEnumerable<Template> Registry = new[]
        {
            new Template(DocumentTemplate.DEERPP, RecordType.IndividualProspect, "DEER"),
            new Template(DocumentTemplate.DEERPM, RecordType.LegalProspect, "DEER"),
            new Template(DocumentTemplate.AUTP, RecordType.IndividualProspect, "AUTP"),
            new Template(DocumentTemplate.AUTM, RecordType.LegalProspect, "AUTM"),
            Spec,
            new Template(DocumentTemplate.GLPP, RecordType.IndividualProspect, "GLPP"),
            new Template(DocumentTemplate.GLPM, RecordType.LegalProspect, "GLPM")
        };

        private static string FormatKey(string documentType, string recordType)
            => documentType.ToUpper() + "-" + recordType.ToUpper();

        private static IReadOnlyDictionary<string, Template> Mapping()
            => Registry.ToDictionary(t => FormatKey(t.DocumentType, t.RecordType.ToString()), t => t)
                .Union(GenerateMappingForSpec())
                .ToDictionary();

        private static Dictionary<string, Template> GenerateMappingForSpec()
            => Enum.GetValues<RecordType>()
                .Select(t => (FormatKey(DocumentTemplate.SPEC.ToString(), t.ToString()), Spec))
                .ToDictionary();


        public static Template FindTemplateFor(string documentType, string recordType)
            => Mapping().TryGetValue(FormatKey(documentType, recordType), out var value)
                ? value
                : throw new ArgumentException("Invalid Document template type or record type");
    }
}