import java.time.LocalDate
import java.util.UUID

case class Food(
    expirationDate: LocalDate,
    approvedForConsumption: Boolean,
    insperctorId: Option[UUID]
) {
  def isEdible(now: LocalDate): Boolean =
    isFresh(now) &&
      hasBeenInspected &&
      canBeConsumed

  private def isFresh(now: LocalDate): Boolean = {
    expirationDate.isAfter(now)
  }

  private def hasBeenInspected: Boolean = {
    insperctorId.nonEmpty
  }

  private def canBeConsumed: Boolean = {
    approvedForConsumption
  }

}
