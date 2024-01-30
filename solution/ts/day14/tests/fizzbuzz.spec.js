"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const fizzbuzz_1 = require("../src/fizzbuzz");
const Option_1 = require("fp-ts/Option");
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
        const conversionResult = (0, fizzbuzz_1.fizzbuzz)(input);
        expect((0, Option_1.isSome)(conversionResult));
        if ((0, Option_1.isSome)(conversionResult)) {
            expect(conversionResult.value).toBe(expectedResult);
        }
    });
    test.each([
        [0],
        [-1],
        [101]
    ])('an error for numbers out of range like %s', (input) => {
        expect(() => (0, fizzbuzz_1.fizzbuzz)(input)).toThrow("Input is out of range");
    });
});
//# sourceMappingURL=fizzbuzz.spec.js.map