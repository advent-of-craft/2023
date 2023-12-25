package M;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Original specifications available here : https://adventofcode.com/2021/day/2
class SubmarineTests {
    @Test
    void should_move_on_given_instructions() throws URISyntaxException, IOException {
        var instructions = loadInstructions();
        var submarine = new K991_P(0, 0)
                .toString(instructions);

        assertThat(calculateResult(submarine))
                .isEqualTo(1_690_020);
    }

    private int calculateResult(K991_P submarine) {
        return submarine.getB().b() * submarine.getB().a();
    }

    private List<K991_P.A> loadInstructions() throws URISyntaxException, IOException {
        return Arrays.stream(FileUtils.getInputAsSeparatedLines("submarine.txt"))
                .map(K991_P.A::f)
                .toList();
    }
}