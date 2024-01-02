import document.DocumentTemplateType
import document.RecordType
import org.approvaltests.combinations.CombinationApprovals.verifyAllCombinations
import org.junit.jupiter.api.Test

class DocumentTests {
    @Test
    fun combinationTests() {
        verifyAllCombinations(
            { document, record -> DocumentTemplateType.fromDocumentTypeAndRecordType(document, record) },
            DocumentTemplateType.entries.map { e -> e.name }.toTypedArray(),
            RecordType.entries.map { e -> e.name }.toTypedArray()
        )
    }
}