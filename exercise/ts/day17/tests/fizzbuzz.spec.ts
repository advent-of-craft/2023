import {fizzbuzz} from '../src/fizzbuzz';
import {isNone, isSome} from "fp-ts/Option";

describe('FizzBuzz should return', () => {
    test.each([
        [1, "1"],
        [67, "67"],
        [82, "82"],
        [3, "Fizz"],
        [66, "Fizz"],
        [99, "Fizz"],
        [5, "Buzz"],
        [50, "Buzz"],
        [85, "Buzz"],
        [15, "FizzBuzz"],
        [30, "FizzBuzz"],
        [45, "FizzBuzz"]
    ])('its representation %s -> %s', (input, expectedResult) => {
        const conversionResult = fizzbuzz(input);
        expect(isSome(conversionResult));

        if (isSome(conversionResult)) {
            expect(conversionResult.value).toBe(expectedResult);
        }
    });

    test.each([
        [0],
        [-1],
        [101]
    ])('an error for numbers out of range like %s', (input) => {
        expect(isNone(fizzbuzz(input))).toBeTruthy();
    });
});