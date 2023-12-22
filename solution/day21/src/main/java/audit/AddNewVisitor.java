package audit;

import java.time.LocalDateTime;

public record AddNewVisitor(String visitor, LocalDateTime time) {
    
}
