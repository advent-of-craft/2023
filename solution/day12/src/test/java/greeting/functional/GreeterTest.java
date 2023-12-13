package greeting.functional;

import org.junit.jupiter.api.Test;

import static greeting.functional.GreeterFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class GreeterTest {
    @Test
    void saysHello() {
        assertGreet(create(), "Hello.");
    }

    @Test
    void saysHelloFormally() {
        assertGreet(
                create(FORMAL),
                "Good evening, sir."
        );
    }

    @Test
    void saysHelloCasually() {
        assertGreet(
                create(CASUAL),
                "Sup bro?"
        );
    }

    @Test
    void saysHelloIntimately() {
        assertGreet(
                create(INTIMATE),
                "Hello Darling!"
        );
    }

    private static void assertGreet(GreeterFactory.Greeter greeter, String expected) {
        assertThat(greeter.get())
                .isEqualTo(expected);
    }
}
