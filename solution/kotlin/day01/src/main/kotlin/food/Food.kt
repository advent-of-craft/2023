package food

import java.time.LocalDate
import java.util.*

data class Food(val expirationDate: LocalDate, val approvedForConsumption: Boolean, val inspectorId: UUID?) {
    fun isEdible(now: () -> LocalDate): Boolean {
        return isFresh(now)
                && canBeConsumed()
                && hasBeenInspected()
    }

    private fun isFresh(now: () -> LocalDate) = expirationDate.isAfter(now())
    private fun canBeConsumed() = approvedForConsumption
    private fun hasBeenInspected() = inspectorId != null
}