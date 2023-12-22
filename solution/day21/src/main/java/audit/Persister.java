package audit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Persister {
    public FileContent[] readDirectory(String directory) throws IOException {
        return Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
                .map(Persister::readFile)
                .toArray(FileContent[]::new);
    }

    public void applyUpdate(String directory, FileUpdate update) throws IOException {
        Files.writeString(Path.of(directory, update.fileName()), update.newContent());
    }

    private static FileContent readFile(Path f) {
        try {
            return new FileContent(f.getFileName().toString(), new ArrayList<>(Files.readAllLines(f)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
