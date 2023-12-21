package audit;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class AuditManager {
    private final int maxEntriesPerFile;
    private final String directoryName;
    private final FileSystem fileSystem;

    public AuditManager(int maxEntriesPerFile, String directoryName, FileSystem fileSystem) {
        this.maxEntriesPerFile = maxEntriesPerFile;
        this.directoryName = directoryName;
        this.fileSystem = fileSystem;
    }

    public void addRecord(String visitorName, LocalDateTime timeOfVisit) {
        String[] filePaths = fileSystem.getFiles(directoryName);
        String[] sorted = sortByIndex(filePaths);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String newRecord = visitorName + ";" + timeOfVisit.format(dateTimeFormatter);

        if (sorted.length == 0) {
            String newFile = Paths.get(directoryName, "audit_1.txt").toString();
            fileSystem.writeAllText(newFile, newRecord);
            return;
        }

        int currentFileIndex = sorted.length - 1;
        String currentFilePath = sorted[currentFileIndex];
        List<String> lines = fileSystem.readAllLines(currentFilePath);

        if (lines.size() < maxEntriesPerFile) {
            lines.add(newRecord);
            String newContent = String.join(System.lineSeparator(), lines);
            fileSystem.writeAllText(currentFilePath, newContent);
        } else {
            String newName = "audit_" + (currentFileIndex + 2) + ".txt";
            String newFile = Paths.get(directoryName, newName).toString();
            fileSystem.writeAllText(newFile, newRecord);
        }
    }

    private String[] sortByIndex(String[] filePaths) {
        return Arrays.stream(filePaths)
                .sorted()
                .toArray(String[]::new);
    }
}
