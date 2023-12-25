package M;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.lang.System.lineSeparator;

public class FileUtils {
    public static String getInputAsString(String input) throws URISyntaxException, IOException {
        return Files.readString(Path.of(Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(input)).toURI()));
    }

    public static String[] getInputAsSeparatedLines(String input) throws URISyntaxException, IOException {
        return getInputAsString(input).split(lineSeparator());
    }
}
