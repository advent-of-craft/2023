import {none, Option, some} from "fp-ts/Option";

export class Diamond {
    private static readonly START = 'A';

    public static print = (endCharacter: string): Option<string> =>
        this.isValidCharacter(endCharacter)
            ? some(this.fullDiamondSafely(endCharacter))
            : none;

    private static fullDiamondSafely(endCharacter: string): string {
        const halfDiamond = this.generateHalfDiamond(endCharacter);
        const fullDiamond = halfDiamond.concat(halfDiamond.slice(0, -1).reverse());

        return fullDiamond.join('\n');
    }

    private static generateHalfDiamond = (endCharacter: string): string[] =>
        Array.from({length: endCharacter.charCodeAt(0) - this.START.charCodeAt(0) + 1}, (_, index) => {
            const currentChar = String.fromCharCode(this.START.charCodeAt(0) + index);
            return this.toLine(currentChar, endCharacter);
        });

    private static toLine(character: string, endCharacter: string): string {
        const outerSpaces = this.generateSpaces(endCharacter.charCodeAt(0) - character.charCodeAt(0));
        const innerSpaces = character === this.START ? '' : this.generateSpaces((character.charCodeAt(0) - this.START.charCodeAt(0)) * 2 - 1) + character;
        return outerSpaces + character + innerSpaces + outerSpaces;
    }

    private static generateSpaces = (count: number): string => ' '.repeat(count);

    private static isValidCharacter = (endCharacter: string): boolean => endCharacter >= this.START && endCharacter <= 'Z';
}