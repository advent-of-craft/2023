import audit.AuditManager
import audit.FileContent
import audit.FileUpdate
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.lang.System.lineSeparator
import java.time.LocalDateTime.parse

lateinit var auditManager: AuditManager
private val NO_FILES = listOf<FileContent>()
private val TIME_OF_VISIT = parse("2019-04-06T18:00:00")
const val NEW_CONTENT = "Alice;2019-04-06 18:00:00"
const val VISITOR = "Alice"
val NO_CONTENT = ArrayList<String>()

private fun AuditManager.addRecord(files: List<FileContent>) = addRecord(files, VISITOR, TIME_OF_VISIT)

class AuditManagerTests : StringSpec({
    beforeEach {
        auditManager = AuditManager(3)
    }

    "add new visitor to a new file because no file today" {
        auditManager.addRecord(NO_FILES) shouldBe FileUpdate("audit_1.txt", NEW_CONTENT)
    }

    "add new visitor to an existing file" {
        val files = listOf(
            FileContent("audit_1.txt", NO_CONTENT),
            FileContent("audit_2.txt", listOf("Peter;2019-04-06 16:30:00"))
        )
        auditManager.addRecord(files) shouldBe FileUpdate(
            "audit_2.txt",
            "Peter;2019-04-06 16:30:00" + lineSeparator() + NEW_CONTENT
        )
    }

    "add new visitor to a new file when end of last file is reached" {
        val files = listOf(
            FileContent("audit_1.txt", NO_CONTENT),
            FileContent(
                "audit_2.txt", listOf(
                    "Peter;2019-04-06 16:30:00",
                    "Jane;2019-04-06 16:40:00",
                    "Jack;2019-04-06 17:00:00"
                )
            )
        )
        auditManager.addRecord(files) shouldBe FileUpdate("audit_3.txt", NEW_CONTENT)
    }
})