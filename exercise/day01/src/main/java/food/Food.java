package food;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Supplier;

public record Food(LocalDate expirationDate,
                   Boolean approvedForConsumption,
                   UUID inspectorId) {
    public boolean isEdible(Supplier<LocalDate> now) {

        return isInspected()
                && isFresh(now)
                && isApprovedForConsumption();
    }

    private Boolean isApprovedForConsumption() {
        return approvedForConsumption;
    }

    private boolean isFresh(Supplier<LocalDate> now) {
        return expirationDate.isAfter(now.get());
    }

    private boolean isInspected() {
        return inspectorId != null;
    }
}