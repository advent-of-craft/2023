import java.time.LocalDate
import java.util.UUID

case class Food(
    expirationDate: LocalDate,
    approvedForConsumption: Boolean,
    insperctorId: Option[UUID]
) {
  def isEdible(now: LocalDate): Boolean =
    expirationDate.isAfter(now) &&
      approvedForConsumption &&
      this.insperctorId.nonEmpty
}
