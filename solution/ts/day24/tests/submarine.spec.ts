import {getInputAsSeparatedLines} from "./fileutils";
import {A, K991_P} from "../src/K991_P";

describe('Submarine', () => {
    test('should move on given instructions', async () => {
        const instructions = getInputAsSeparatedLines('submarine.txt').map(A.f);

        expect(calculateResult(new K991_P(0, 0).toString(instructions)))
            .toEqual(1690020);
    });

    const calculateResult = (submarine: K991_P): number => submarine.getB().b * submarine.getB().a;
});
