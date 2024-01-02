using NSubstitute;
using Xunit;

namespace Day21.Tests
{
    public class AuditManagerTests
    {
        private const string DirectoryName = "audits";

        [Fact]
        public void A_New_File_Is_Created_When_The_Current_File_Overflows()
        {
            var fileSystemMock = Substitute.For<IFileSystem>();
            fileSystemMock.GetFiles(DirectoryName)
                .Returns(new string[]
                {
                    Path.Combine(DirectoryName, "audit_1.txt"),
                    Path.Combine(DirectoryName, "audit_2.txt")
                });

            fileSystemMock
                .ReadAllLines(Path.Combine(DirectoryName, "audit_2.txt"))
                .Returns(new List<string>
                {
                    "Peter;2019-04-06 16:30:00",
                    "Jane;2019-04-06 16:40:00",
                    "Jack;2019-04-06 17:00:00"
                });

            var sut = new AuditManager(3, DirectoryName, fileSystemMock);

            sut.AddRecord("Alice", DateTime.Parse("2019-04-06T18:00:00"));

            fileSystemMock.Received(1).WriteAllText(
                Path.Combine(DirectoryName, "audit_3.txt"),
                "Alice;2019-04-06 18:00:00"
            );
        }
    }
}