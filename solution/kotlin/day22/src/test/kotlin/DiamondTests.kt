import diamond.Diamond.toDiamond
import org.approvaltests.Approvals.verify
import org.junit.jupiter.api.Test

class DiamondTests {
    @Test
    fun `print diamond for K`() {
        verify('K'.toDiamond().getOrNull())
    }
}