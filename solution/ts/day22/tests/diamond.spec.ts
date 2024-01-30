import {Diamond} from '../src/diamond';
import * as fc from 'fast-check';
import * as O from "fp-ts/Option";
import {Option} from "fp-ts/Option";
import {verify} from "approvals/lib/Providers/Jest/JestApprovals";
import {JestReporter} from "approvals/lib/Providers/Jest/JestReporter";
import {beforeAll, describe, test} from "@jest/globals";
import {configure} from "approvals";

describe('diamond', () => {
    beforeAll(() => {
        configure({
            reporters: [new JestReporter()],
        });
    });

    test('is horizontally symmetric', () =>
        checkProperty(diamond => diamond === diamond.reverse()));

    test('is a square', () =>
        checkProperty(diamond => diamond.every(line => line.length === diamond.length)));


    test('contains 2 identical letters except first and last', () =>
        checkProperty(diamond => diamond
            .slice(1)
            .reverse()
            .slice(1)
            .map(line => line.replace(/\s/g, ''))
            .every(line => line.length === 2 && line.charAt(0) === line.charAt(1))));

    test('have a decreasing number of left white spaces until end character', () =>
        checkProperty(((diamond, endCharacter) => {
            const half = halfDiamond(diamond, endCharacter);
            const spaces = countSpacesBeforeFirstLetterPerLine(half);

            return areSpacesPerLineMatch(half, spaces);
        })));

    test('have a decreasing number of right white spaces until end character', () =>
        checkProperty(((diamond, endCharacter) => {
            const half = halfDiamond(diamond, endCharacter);
            const spaces = countSpacesAfterLastLetterPerLine(half);

            return areSpacesPerLineMatch(half, spaces);
        })));

    test('generate a pretty diamond for human eyes', () => {
        verify(valueOf(Diamond.print('K')));
    });

    test('fails for invalid end character', () => {
        fc.assert(
            fc.property(invalidCharacterGenerator, endCharacter => O.isNone(Diamond.print(endCharacter)))
        );
    });
});

const emptyCharacter = ' ';
const upperLetterGenerator = fc.char().filter(n => n >= 'A' && n < 'Z');
const invalidCharacterGenerator = fc.char().filter(n => n < 'A' || n > 'Z');

const checkProperty = (property: (diamond: string[], endCharacter: string) => boolean) => {
    fc.assert(
        fc.property(upperLetterGenerator, endCharacter =>
            property(valueOf(Diamond.print(endCharacter))
                .split('\n'), endCharacter
            ))
    );
}

const valueOf = (opt: Option<string>): string => {
    if (O.isSome(opt)) return opt.value
    else return fail('opt should not be empty');
};

const halfDiamond = (diamond: string[], endCharacter: string) =>
    diamond.slice(0, endCharacter.charCodeAt(0) - 'A'.charCodeAt(0) + 1)

const countSpacesBeforeFirstLetterPerLine = (halfDiamond: string[]): number[] =>
    countSpacesOnLine(halfDiamond, line => line);

const countSpacesAfterLastLetterPerLine = (halfDiamond: string[]): number[] =>
    countSpacesOnLine(halfDiamond, line => line.split('').reverse().join(''));

const countSpacesOnLine = (halfDiamond: string[], mapLine: (line: string) => string): number[] => halfDiamond.map(line => {
    let count = 0;
    for (let char of mapLine(line)) {
        if (char === emptyCharacter) {
            count++;
        } else {
            break;
        }
    }
    return count;
});

const areSpacesPerLineMatch = (halfDiamond: string[], spaces: number[]): boolean => {
    let expectedSpaceOnLine = halfDiamond.length;
    for (let i = 0; i < halfDiamond.length - 1; i++) {
        if (spaces[i] !== --expectedSpaceOnLine) {
            return false;
        }
    }
    return true;
};