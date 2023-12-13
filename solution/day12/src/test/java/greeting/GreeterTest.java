package greeting;

import org.junit.jupiter.api.Test;

import static greeting.Greeter.*;
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

    private static void assertGreet(Greeter greeter, String expected) {
        assertThat(greeter.greet())
                .isEqualTo(expected);
    }
}
