import java.time.LocalDate
import java.time.LocalDate.of
import java.util.UUID
import java.util.UUID.randomUUID


class FoodSpec extends munit.FunSuite {

  private val expirationDate = of(2023, 12, 1)
  private val inspector = randomUUID()
  private val notFreshDate = expirationDate.plusDays(7)
  private val freshDate = expirationDate.minusDays(7)

  test("not edible food") {
    val res = notEdibleFood
      .forall {
        case (bool, maybeUuid, now) => !Food(expirationDate, bool, maybeUuid).isEdible(now)
      }
    assert(res)
  }

  test("edible food") {
    assert(Food(expirationDate, true, Some(inspector)).isEdible(freshDate))
  }

  def notEdibleFood: List[(Boolean, Option[UUID], LocalDate)] = List(
    (true, Some(inspector), notFreshDate),
    (false, Some(inspector), freshDate),
    (true, None, freshDate),
    (false, None, notFreshDate),
    (false, None, freshDate)
  )
}
