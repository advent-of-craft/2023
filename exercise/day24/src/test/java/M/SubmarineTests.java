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
        var submarine = new Submarine(0, 0)
                .move(instructions);

        assertThat(calculateResult(submarine))
                .isEqualTo(1_690_020);
    }

    private int calculateResult(Submarine submarine) {
        return submarine.getPosition().depth() * submarine.getPosition().horizontal();
    }

    private List<Instruction> loadInstructions() throws URISyntaxException, IOException {
        return Arrays.stream(FileUtils.getInputAsSeparatedLines("submarine.txt"))
                .map(Instruction::fromText)
                .toList();
    }
}