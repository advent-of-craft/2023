package audit;

import java.util.List;

public interface FileSystem {
    String[] getFiles(String directoryName);

    void writeAllText(String filePath, String content);

    List<String> readAllLines(String filePath);
}
