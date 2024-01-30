"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.fizzbuzz = void 0;
const Option_1 = require("fp-ts/Option");
const min = 1;
const max = 100;
let mapping = new Map([
    [15, 'FizzBuzz'],
    [3, 'Fizz'],
    [5, 'Buzz'],
]);
const fizzbuzz = (input) => isOutOfRange(input)
    ? Option_1.none
    : (0, Option_1.some)(convertSafely(input));
exports.fizzbuzz = fizzbuzz;
function convertSafely(input) {
    for (const [divisor, value] of mapping) {
        if (is(divisor, input)) {
            return value;
        }
    }
    return input.toString();
}
const is = (divisor, input) => input % divisor === 0;
const isOutOfRange = (input) => input < min || input > max;
//# sourceMappingURL=fizzbuzz.js.map