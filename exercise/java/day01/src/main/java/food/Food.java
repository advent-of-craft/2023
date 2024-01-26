package food;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Supplier;

public record Food(LocalDate expirationDate,
                   Boolean approvedForConsumption,
                   UUID inspectorId) {

    /*
     * The most important is to simplify the code if/else
     * Extract the condition in a method make more readable the code
     * NOTE: Supplier LocalDate is used to mock the date in the test, can replace by LocalDate.now()
     */
    public boolean isEdible(Supplier<LocalDate> now) {
        return isFreshFood(now) && canBeConsumed() && hasBeenInspected();
    }

    private Boolean canBeConsumed() {
        return this.approvedForConsumption;
    }

    private boolean hasBeenInspected() {
        return this.inspectorId != null;
    }

    private boolean isFreshFood(Supplier<LocalDate> now) {
        return this.expirationDate.isAfter(now.get());
    }
}