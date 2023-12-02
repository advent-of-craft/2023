package games;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FizzBuzzTests {
    @Test
    void returns_the_given_number_for_1() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(1))
                .isEqualTo("1");
    }

    @Test
    void returns_the_given_number_for_67() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(67))
                .isEqualTo("67");
    }

    @Test
    void returns_the_given_number_for_82() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(82))
                .isEqualTo("82");
    }

    @Test
    void returns_Fizz_for_3() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(3))
                .isEqualTo("Fizz");
    }

    @Test
    void returns_Fizz_for_66() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(66))
                .isEqualTo("Fizz");
    }

    @Test
    void returns_Fizz_for_99() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(99))
                .isEqualTo("Fizz");
    }

    @Test
    void returns_Buzz_for_5() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(5))
                .isEqualTo("Buzz");
    }

    @Test
    void returns_Buzz_for_50() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(50))
                .isEqualTo("Buzz");
    }

    @Test
    void returns_Buzz_for_85() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(85))
                .isEqualTo("Buzz");
    }

    @Test
    void returns_FizzBuzz_for_15() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(15))
                .isEqualTo("FizzBuzz");
    }

    @Test
    void returns_FizzBuzz_for_30() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(30))
                .isEqualTo("FizzBuzz");
    }

    @Test
    void returns_FizzBuzz_for_45() throws OutOfRangeException {
        assertThat(FizzBuzz.convert(45))
                .isEqualTo("FizzBuzz");
    }

    @Test
    void throws_an_exception_for_0() {
        assertThatThrownBy(() -> FizzBuzz.convert(0))
                .isInstanceOf(OutOfRangeException.class);
    }

    @Test
    void throws_an_exception_for_101() {
        assertThatThrownBy(() -> FizzBuzz.convert(101))
                .isInstanceOf(OutOfRangeException.class);
    }

    @Test
    void throws_an_exception_for_minus_1() {
        assertThatThrownBy(() -> FizzBuzz.convert(-1))
                .isInstanceOf(OutOfRangeException.class);
    }
}