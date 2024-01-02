import food.Food
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.util.*

class EdibleTests : FunSpec({
    val expirationDate = LocalDate.of(2023, 12, 1)
    val inspector = UUID.randomUUID()
    val notFreshDate = expirationDate.plusDays(7)
    val freshDate = expirationDate.minusDays(7)

    data class NotEdibleFood(val approvedForConsumption: Boolean, val inspectorId: UUID?, val now: LocalDate)

    context("not edible") {
        withData(
            NotEdibleFood(true, inspector, notFreshDate),
            NotEdibleFood(false, inspector, freshDate),
            NotEdibleFood(true, null, freshDate),
            NotEdibleFood(false, null, notFreshDate),
            NotEdibleFood(false, null, freshDate)
        ) { (approvedForConsumption, inspectorId, now) ->
            Food(expirationDate, approvedForConsumption, inspectorId).isEdible { now } shouldBe false
        }
    }

    context("edible") {
        Food(expirationDate, true, inspector).isEdible { freshDate } shouldBe true
    }
})