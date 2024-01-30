import {fizzbuzz} from '../src/fizzbuzz';

describe('FizzBuzz should return', () => {
    test.each([
        [1, 1],
        [67, 67],
        [82, 82],
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
        expect(fizzbuzz(input)).toBe(expectedResult);
    });

    test.each([
        [0],
        [-1],
        [101]
    ])('an error for numbers out of range like %s', (input) => {
        expect(() => fizzbuzz(input)).toThrow("Input is out of range");
    });
});