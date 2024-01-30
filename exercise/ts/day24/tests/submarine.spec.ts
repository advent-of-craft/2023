import {getInputAsSeparatedLines} from "./fileutils";
import {Instruction} from "../src/instruction";
import {Submarine} from "../src/submarine";

describe('Submarine', () => {
    test('should move on given instructions', async () => {
        const instructionsText = getInputAsSeparatedLines('submarine.txt');
        const instructions = instructionsText.map(Instruction.fromText);

        expect(calculateResult(new Submarine(0, 0).move(instructions)))
            .toEqual(1690020);
    });

    const calculateResult = (submarine: Submarine): number =>
        submarine.getPosition().depth * submarine.getPosition().horizontal;
});
