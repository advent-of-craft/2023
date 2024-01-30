const min = 1;
const max = 100;
const fizz = 3;
const buzz = 5;
const fizzBuzz = 15;

let mapping: Map<(input: number) => boolean, (input: number) => string> = new Map([
    [(input: number) => is(fizzBuzz, input), () => "FizzBuzz"],
    [(input: number) => is(fizz, input), () => "Fizz"],
    [(input: number) => is(buzz, input), () => "Buzz"],
    [(input: number) => !isOutOfRange(input), (input: number) => input.toString()],
]);

export function fizzbuzz(input: number): string {
    switch (isOutOfRange(input)) {
        case true:
            throw new Error("Input is out of range");
        case false:
            return (firstMatchingPredicateFor(input))[1](input);
    }
}

const is = (divisor: number, input: number): boolean => input % divisor === 0;
const isOutOfRange = (input: number): boolean => input < min || input > max;
const firstMatchingPredicateFor = (input: number) =>
    Array.from(mapping.entries())
        .find(([predicate]) => predicate(input));