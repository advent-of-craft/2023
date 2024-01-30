import {Either, left, right} from 'fp-ts/Either';
import {pipe} from 'fp-ts/function';
import * as O from "fp-ts/Option";
import {none, Option, some} from "fp-ts/Option";

export class Password {
    private constructor(private readonly value: string) {
    }

    static from(input: string): Either<ParsingError, Password> {
        const rules: Array<(input: string) => Option<ParsingError>> = [
            input => Password.matches(input, /.{8,}/, 'Too short'),
            input => Password.matches(input, /.*[A-Z].*/, 'No capital letter'),
            input => Password.matches(input, /.*[a-z].*/, 'No lower letter'),
            input => Password.matches(input, /.*[0-9].*/, 'No number'),
            input => Password.matches(input, /.*[.*#@$%&].*/, 'No special character'),
            input => Password.matches(input, /^[a-zA-Z0-9.*#@$%&]+$/, 'Invalid character')
        ];

        return pipe(
            rules,
            rule => rule.map(rule => rule(input)),
            results => results.find(parsingError => O.isSome(parsingError)) ?? none,
            option => pipe(
                option,
                O.match(
                    () => right(new Password(input)),
                    onError => left(onError)
                )
            )
        );
    }

    private static matches = (input: string, regex: RegExp, reason: string): Option<ParsingError> =>
        regex.test(input)
            ? none
            : some(ParsingError.from(reason));
    toString = (): string => this.value;
}

class ParsingError {
    constructor(public reason: string) {
    }

    static from(reason: string): ParsingError {
        return new ParsingError(reason);
    }
}