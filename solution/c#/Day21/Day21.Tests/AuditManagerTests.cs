using FluentAssertions;
using Xunit;

namespace Day21.Tests
{
    public class AuditManagerTests
    {
        private readonly AuditManager _auditManager = new(3);

        private static readonly FileContent[] NoFiles = Array.Empty<FileContent>();
        private static readonly string[] NoContent = Array.Empty<string>();
        private static readonly DateTime TimeOfVisit = DateTime.Parse("2019-04-06T18:00:00");

        private const string NewContent = "Alice;2019-04-06 18:00:00";
        private const string Visitor = "Alice";

        [Fact]
        public void Add_New_Visitor_To_A_New_File_Because_No_File_Today() =>
            AddRecord(NoFiles)
                .Should()
                .Be(new FileUpdate("audit_1.txt", NewContent));

        [Fact]
        public void Add_New_Visitor_To_An_Existing_File() =>
            AddRecord([
                    new FileContent("audit_1.txt", NoContent),
                    new FileContent("audit_2.txt", ["Peter;2019-04-06 16:30:00"])
                ])
                .Should()
                .Be(new FileUpdate("audit_2.txt",
                    """
                    Peter;2019-04-06 16:30:00
                    Alice;2019-04-06 18:00:00
                    """)
                );

        [Fact]
        public void Add_New_Visitor_To_A_New_File_When_End_Of_Last_File_Is_Reached() =>
            AddRecord([
                    new FileContent("audit_1.txt", NoContent),
                    new FileContent("audit_2.txt",
                    [
                        "Peter;2019-04-06 16:30:00",
                        "Jane;2019-04-06 16:40:00",
                        "Jack;2019-04-06 17:00:00"
                    ])
                ])
                .Should()
                .Be(new FileUpdate("audit_3.txt", NewContent)
                );

        private FileUpdate AddRecord(FileContent[] files)
            => _auditManager.AddRecord(files, Visitor, TimeOfVisit);
    }
}