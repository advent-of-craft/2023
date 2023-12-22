package audit;

import java.util.ArrayList;

public record FileContent(String fileName, ArrayList<String> lines) {
}