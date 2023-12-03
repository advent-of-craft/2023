package food;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Supplier;

public record Food(LocalDate expirationDate,
                   Boolean approvedForConsumption,
                   UUID inspectorId) {
    public boolean isEdible(Supplier<LocalDate> now) {

        if (inspectorId == null) return false;

        boolean isNotExpired = expirationDate.isAfter(now.get());

        return isNotExpired && approvedForConsumption;
    }
}