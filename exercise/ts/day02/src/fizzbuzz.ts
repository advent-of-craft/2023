export function fizzbuzz(input: number): string | number {
    if (input > 0) {
        if (input <= 100) {
            if (input % 3 === 0 && input % 5 === 0) {
                return "FizzBuzz";
            }
            if (input % 3 === 0) {
                return "Fizz";
            }
            if (input % 5 === 0) {
                return "Buzz";
            }
            return input;
        } else {
            throw new Error("Input is out of range");
        }
    } else {
        throw new Error("Input is out of range");
    }
}