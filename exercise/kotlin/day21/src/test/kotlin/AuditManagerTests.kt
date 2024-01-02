import audit.AuditManager
import audit.FileSystem
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime.parse

class AuditManagerTests : StringSpec({
    "add new visitor to a new file when end of last file is reached" {
        val fileSystemMock = mockk<FileSystem>()
        every { fileSystemMock.getFiles("audits") } returns listOf(
            "audits/audit_2.txt",
            "audits/audit_1.txt"
        )

        every { fileSystemMock.readAllLines("audits/audit_2.txt") } returns listOf(
            "Peter;2019-04-06 16:30:00",
            "Jane;2019-04-06 16:40:00",
            "Jack;2019-04-06 17:00:00"
        )

        every { fileSystemMock.writeAllText(any(), any()) } returns Unit

        val sut = AuditManager(3, "audits", fileSystemMock)

        sut.addRecord("Alice", parse("2019-04-06T18:00:00"))

        verify { fileSystemMock.writeAllText("audits/audit_3.txt", "Alice;2019-04-06 18:00:00") }
    }
})