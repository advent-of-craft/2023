import {Password} from '../src/password'; // Adjust the import path as needed
import {isLeft, isRight} from 'fp-ts/Either';

describe('parse a password', () => {
    const validPasswords = [
        'P@ssw0rd',
        'Advent0fCraft&'
    ];

    test.each(validPasswords)('successfully for a valid password %s', (password) => {
        const result = Password.from(password);

        expect(isRight(result)).toBeTruthy();

        if (isRight(result)) {
            expect(result.right.toString())
                .toEqual(password);
        }
    });

    describe('fail when', () => {
        test.each([
            ['', 'Too short'],
            ['aa', 'Too short'],
            ['xxxxxxx', 'Too short'],
            ['adventofcraft', 'No capital letter'],
            ['p@ssw0rd', 'No capital letter'],
            ['ADVENTOFCRAFT', 'No lower letter'],
            ['P@SSW0RD', 'No lower letter'],
            ['Adventofcraft', 'No number'],
            ['P@sswOrd', 'No number'],
            ['Adventof09craft', 'No special character'],
            ['PAssw0rd', 'No special character'],
            ['Advent@of9Craft¨', 'Invalid character'],
            ['P@ssw^rd1', 'Invalid character']
        ])('invalid passwords %s', (password, reason) => {
            const result = Password.from(password);
            expect(isLeft(result)).toBeTruthy();
            if (isLeft(result)) {
                expect(result.left.reason).toEqual(reason);
            }
        });
    });
});
