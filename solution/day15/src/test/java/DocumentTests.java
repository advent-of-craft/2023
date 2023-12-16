import document.DocumentTemplateType;
import document.RecordType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.approvaltests.combinations.CombinationApprovals.verifyAllCombinations;

class DocumentTests {
    @Test
    void combinationTests() {
        verifyAllCombinations(
                DocumentTemplateType::fromDocumentTypeAndRecordType,
                Arrays.stream(DocumentTemplateType.values()).map(Enum::name).toArray(String[]::new),
                Arrays.stream(RecordType.values()).map(Enum::name).toArray(String[]::new)
        );
    }
}
