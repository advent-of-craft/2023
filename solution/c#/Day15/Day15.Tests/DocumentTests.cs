using System.Text;

namespace Day15.Tests
{
    [UsesVerify]
    public class DocumentTests
    {
        [Fact]
        public Task Verify_Combinations()
            => Verify(
                CombineEnumValues<DocumentTemplate, RecordType>()
                    .Select(t => FindTemplateFor(t.Item1, t.Item2))
                    .Aggregate(new StringBuilder(), (builder, s) => builder.AppendLine(s))
            );

        private static IEnumerable<Tuple<TEnum1, TEnum2>> CombineEnumValues<TEnum1, TEnum2>()
            where TEnum1 : struct, Enum where TEnum2 : struct, Enum =>
            from a in Enum.GetValues<TEnum1>()
            from b in Enum.GetValues<TEnum2>()
            select new Tuple<TEnum1, TEnum2>(a, b);

        private static string FindTemplateFor(
            DocumentTemplate documentTemplate,
            RecordType recordType)
            => $"[{documentTemplate},{recordType}] => {FindTemplateSafely(documentTemplate, recordType)}";

        private static string FindTemplateSafely(DocumentTemplate documentTemplate, RecordType recordType)
        {
            try
            {
                return Templates.FindTemplateFor(documentTemplate.ToString(), recordType.ToString()).ToString();
            }
            catch (ArgumentException argumentException)
            {
                return argumentException.Message;
            }
        }
    }
}