import {fizzbuzz} from '../src/fizzbuzz';

describe('FizzBuzz should return', () => {
    test('the given number for 1', () => {
        expect(fizzbuzz(1)).toBe(1);
    });

    test('the given number for 67', () => {
        expect(fizzbuzz(67)).toBe(67);
    });

    test('the given number for 82', () => {
        expect(fizzbuzz(82)).toBe(82);
    });

    test('Fizz for 3', () => {
        expect(fizzbuzz(3)).toBe("Fizz");
    });

    test('Fizz for 66', () => {
        expect(fizzbuzz(66)).toBe("Fizz");
    });

    test('Fizz for 99', () => {
        expect(fizzbuzz(99)).toBe("Fizz");
    });

    test('Buzz for 5', () => {
        expect(fizzbuzz(5)).toBe("Buzz");
    });

    test('Buzz for 55', () => {
        expect(fizzbuzz(55)).toBe("Buzz");
    });

    test('Buzz for 95', () => {
        expect(fizzbuzz(95)).toBe("Buzz");
    });

    test('Buzz for 15', () => {
        expect(fizzbuzz(15)).toBe("FizzBuzz");
    });

    test('Buzz for 30', () => {
        expect(fizzbuzz(30)).toBe("FizzBuzz");
    });

    test('Buzz for 45', () => {
        expect(fizzbuzz(45)).toBe("FizzBuzz");
    });

    test('an error for 0', () => {
        expect(() => fizzbuzz(0)).toThrow("Input is out of range");
    });

    test('an error for 101', () => {
        expect(() => fizzbuzz(0)).toThrow("Input is out of range");
    });

    test('an error for -1', () => {
        expect(() => fizzbuzz(0)).toThrow("Input is out of range");
    });
});