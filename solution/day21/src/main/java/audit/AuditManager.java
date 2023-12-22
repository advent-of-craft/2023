package audit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;

public class AuditManager {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String AUDIT = "audit_";
    private final int maxEntriesPerFile;

    public AuditManager(int maxEntriesPerFile) {
        this.maxEntriesPerFile = maxEntriesPerFile;
    }

    public FileUpdate addRecord(FileContent[] files, String visitorName, LocalDateTime timeOfVisit) {
        var sortedFiles = sortByIndex(files);
        var newRecord = createNewRecord(visitorName, timeOfVisit);

        return (sortedFiles.isEmpty())
                ? createANewFile(sortedFiles, newRecord)
                : createNewFileOrUpdate(sortedFiles, newRecord);
    }

    private static FileUpdate createANewFile(List<FileContent> sortedFiles, String newRecord) {
        var currentFileIndex = sortedFiles.size();
        var newFileName = createAuditFileName(currentFileIndex + 1);

        return new FileUpdate(newFileName, newRecord);
    }

    private FileUpdate createNewFileOrUpdate(List<FileContent> sortedFiles, String newRecord) {
        var currentFile = sortedFiles.getLast();

        return canAppendToExistingFile(currentFile)
                ? appendToExistingFile(newRecord, currentFile)
                : createANewFile(sortedFiles, newRecord);
    }

    private List<FileContent> sortByIndex(FileContent[] files) {
        return stream(files)
                .sorted(comparing(FileContent::fileName))
                .toList();
    }

    private static String createNewRecord(String visitorName, LocalDateTime timeOfVisit) {
        return visitorName + ";" + timeOfVisit.format(DATE_TIME_FORMATTER);
    }

    private static String createAuditFileName(int index) {
        return AUDIT + index + ".txt";
    }

    private static FileUpdate appendToExistingFile(String newRecord, FileContent currentFile) {
        var lines = new ArrayList<>(currentFile.lines());
        lines.add(newRecord);
        var newContent = lines.stream().collect(joining(lineSeparator()));

        return new FileUpdate(currentFile.fileName(), newContent);
    }

    private boolean canAppendToExistingFile(FileContent currentFile) {
        return currentFile.lines().size() < maxEntriesPerFile;
    }
}