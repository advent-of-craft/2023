import {none, Option, some} from "fp-ts/Option";

const intToNumerals: Map<number, string> = new Map([
    [1000, "M"],
    [900, "CM"],
    [500, "D"],
    [400, "CD"],
    [100, "C"],
    [90, "XC"],
    [50, "L"],
    [40, "XL"],
    [10, "X"],
    [9, "IX"],
    [5, "V"],
    [4, "IV"],
    [1, "I"],
]);

const maxNumber = 3999;
const isInRange = (number: number): boolean => number > 0 && number <= maxNumber;

export function convert(number: number): Option<string> {
    if (!isInRange(number)) return none;

    let roman = '';
    let remaining = number;

    for (const [value, numeral] of intToNumerals) {
        while (remaining >= value) {
            roman += numeral;
            remaining -= value;
        }
    }
    return some(roman);
}

