package audit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static java.time.LocalDateTime.parse;
import static org.assertj.core.api.Assertions.assertThat;

class AuditManagerShould {
    private static final FileContent[] NO_FILES = {};
    private static final LocalDateTime TIME_OF_VISIT = parse("2019-04-06T18:00:00");
    public static final String NEW_CONTENT = "Alice;2019-04-06 18:00:00";
    public static final String VISITOR = "Alice";
    public static final ArrayList<String> NO_CONTENT = new ArrayList<>();
    private AuditManager auditManager;

    @BeforeEach
    void setup() {
        auditManager = new AuditManager(3);
    }

    @Test
    void add_new_visitor_to_a_new_file_because_no_file_today() {
        assertThat(addRecord(NO_FILES))
                .isEqualTo(new FileUpdate("audit_1.txt", NEW_CONTENT));
    }

    @Test
    void add_new_visitor_to_an_existing_file() {
        var files = files(
                fileContent("audit_1.txt", NO_CONTENT),
                fileContent("audit_2.txt", contentFrom("Peter;2019-04-06 16:30:00"))
        );

        assertThat(addRecord(files))
                .isEqualTo(new FileUpdate("audit_2.txt",
                        "Peter;2019-04-06 16:30:00" + System.lineSeparator() +
                                NEW_CONTENT
                ));
    }

    @Test
    void add_new_visitor_to_a_new_file_when_end_of_last_file_is_reached() {
        var files = files(
                fileContent("audit_1.txt", NO_CONTENT),
                fileContent("audit_2.txt",
                        contentFrom(
                                "Peter;2019-04-06 16:30:00",
                                "Jane;2019-04-06 16:40:00",
                                "Jack;2019-04-06 17:00:00"
                        )

                )
        );

        assertThat(addRecord(files))
                .isEqualTo(new FileUpdate("audit_3.txt", NEW_CONTENT));
    }

    private FileUpdate addRecord(FileContent[] files) {
        return auditManager.addRecord(files, VISITOR, TIME_OF_VISIT);
    }

    private static FileContent[] files(FileContent... files) {
        return files;
    }

    private static FileContent fileContent(String fileName, ArrayList<String> lines) {
        return new FileContent(fileName, lines);
    }

    private static ArrayList<String> contentFrom(String... lines) {
        return new ArrayList<>(Arrays.stream(lines).toList());
    }
}