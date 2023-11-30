package food;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Supplier;

public record Food(LocalDate expirationDate,
                   Boolean approvedForConsumption,
                   UUID inspectorId) {
    public boolean isEdible(Supplier<LocalDate> now) {
        if (this.expirationDate.isAfter(now.get()) &&
                this.approvedForConsumption &&
                this.inspectorId != null) {
            return true;
        } else {
            return false;
        }
    }
}