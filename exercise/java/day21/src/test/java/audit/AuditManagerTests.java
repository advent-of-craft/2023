package audit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuditManagerTests {
    @Test
    void addsNewVisitorToANewFileWhenEndOfLastFileIsReached() {
        FileSystem fileSystemMock = Mockito.mock(FileSystem.class);
        when(fileSystemMock.getFiles("audits"))
                .thenReturn(new String[]{
                        "audits/audit_2.txt",
                        "audits/audit_1.txt"}
                );
        when(fileSystemMock.readAllLines("audits/audit_2.txt"))
                .thenReturn(List.of(
                        "Peter;2019-04-06 16:30:00",
                        "Jane;2019-04-06 16:40:00",
                        "Jack;2019-04-06 17:00:00"
                ));

        var sut = new AuditManager(3, "audits", fileSystemMock);

        sut.addRecord("Alice", LocalDateTime.parse("2019-04-06T18:00:00"));

        verify(fileSystemMock).writeAllText("audits/audit_3.txt", "Alice;2019-04-06 18:00:00");
    }
}


