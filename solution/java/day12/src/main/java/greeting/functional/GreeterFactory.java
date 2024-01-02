package greeting.functional;

import java.util.Map;
import java.util.function.Supplier;

public class GreeterFactory {
    public static final String CASUAL = "casual";
    public static final String INTIMATE = "intimate";
    public static final String FORMAL = "formal";
    private static final Map<String, Greeter> mapping = Map.of(
            CASUAL, () -> "Sup bro?",
            FORMAL, () -> "Good evening, sir.",
            INTIMATE, () -> "Hello Darling!"
    );

    public static Greeter create(String formality) {
        return mapping.getOrDefault(formality, create());
    }

    public static Greeter create() {
        return () -> "Hello.";
    }

    public interface Greeter extends Supplier<String> {
    }
}