import {convert} from "../src/romanNumerals";
import * as fc from 'fast-check';
import {isNone, isSome} from "fp-ts/Option";

describe('RomanNumeral conversion', () => {
    test.each([
        [1, "I"],
        [3, "III"],
        [4, "IV"],
        [5, "V"],
        [10, "X"],
        [13, "XIII"],
        [50, "L"],
        [100, "C"],
        [500, "D"],
        [1000, "M"],
        [2499, "MMCDXCIX"]
    ])('generates roman for numbers %i -> %s', (number, expectedRoman) => {
        const result = convert(number)
        expect(isSome(result)).toBeTruthy();

        if (isSome(result)) {
            expect(result.value).toEqual(expectedRoman);
        }
    });


    test('fails for any number out of range', () => {
        fc.assert(
            fc.property(
                fc.integer().filter(n => n <= 0 || n > 3999),
                (n) => isNone(convert(n))
            )
        );
    });

    test('returns only valid symbols for valid numbers', () => {
        fc.assert(
            fc.property(
                fc.integer({min: 1, max: 3999}),
                (n) => isSome(convert(n))
            )
        );
    });
});
