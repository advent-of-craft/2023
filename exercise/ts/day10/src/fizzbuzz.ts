const min = 0;
const max = 100;
const fizz = 3;
const buzz = 5;
const fizzBuzz = 15;

export function fizzbuzz(input: number): string | number {
    if (isOutOfRange(input)) {
        throw new Error("Input is out of range")
    }
    return convertSafely(input);
}

function convertSafely(input: number): string | number {
    if (is(fizzBuzz, input)) {
        return "FizzBuzz";
    }
    if (is(fizz, input)) {
        return "Fizz";
    }
    if (is(buzz, input)) {
        return "Buzz";
    }
    return input;
}

function is(divisor: number, input: number): boolean {
    return input % divisor === 0;
}

function isOutOfRange(input: number): boolean {
    return input <= min || input > max;
}