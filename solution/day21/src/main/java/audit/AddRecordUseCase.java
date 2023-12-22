package audit;

import java.io.IOException;

public class AddRecordUseCase {
    private final String directory;
    private final AuditManager auditManager;
    private final Persister persister;

    public AddRecordUseCase(String directory, AuditManager auditManager, Persister persister) {
        this.directory = directory;
        this.auditManager = auditManager;
        this.persister = persister;
    }

    public void handle(AddNewVisitor addNewVisitor) throws IOException {
        var files = persister.readDirectory(directory);
        var update = auditManager.addRecord(files, addNewVisitor.visitor(), addNewVisitor.time());

        persister.applyUpdate(directory, update);
    }
}
