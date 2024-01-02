package food

import java.time.LocalDate
import java.util.*

data class Food(val expirationDate: LocalDate, val approvedForConsumption: Boolean, val inspectorId: UUID?) {
    fun isEdible(now: () -> LocalDate): Boolean {
        return if (expirationDate.isAfter(now()) &&
            approvedForConsumption &&
            inspectorId != null
        ) {
            true
        } else {
            false
        }
    }
}