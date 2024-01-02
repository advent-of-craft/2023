package greeting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterTest {
    @Test
    void saysHello() {
        var greeter = new Greeter();

        assertThat(greeter.greet())
                .isEqualTo("Hello.");
    }

    @Test
    void saysHelloFormally() {
        var greeter = new Greeter();
        greeter.setFormality("formal");

        assertThat(greeter.greet())
                .isEqualTo("Good evening, sir.");
    }

    @Test
    void saysHelloCasually() {
        var greeter = new Greeter();
        greeter.setFormality("casual");

        assertThat(greeter.greet())
                .isEqualTo("Sup bro?");
    }

    @Test
    void saysHelloIntimately() {
        var greeter = new Greeter();
        greeter.setFormality("intimate");

        assertThat(greeter.greet())
                .isEqualTo("Hello Darling!");
    }
}
