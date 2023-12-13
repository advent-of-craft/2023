package greeting;

public interface Greeter {
    String CASUAL = "casual";
    String INTIMATE = "intimate";
    String FORMAL = "formal";

    static Greeter create() {
        return new Default();
    }

    static Greeter create(String formality) {
        return switch (formality) {
            case CASUAL -> new Casual();
            case INTIMATE -> new Intimate();
            case FORMAL -> new Formal();
            default -> create();
        };
    }

    default String greet() {
        return "Hello.";
    }

    class Default implements Greeter {
    }

    class Formal implements Greeter {
        @Override
        public String greet() {
            return "Good evening, sir.";
        }
    }

    class Casual implements Greeter {
        @Override
        public String greet() {
            return "Sup bro?";
        }
    }

    class Intimate implements Greeter {
        @Override
        public String greet() {
            return "Hello Darling!";
        }
    }
}