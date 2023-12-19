package blog;

import java.time.LocalDate;

public record Comment(String text, String author, LocalDate creationDate) {
}
