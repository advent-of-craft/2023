import {Greeter} from '../src/greeter';

describe('Greeter', () => {
    let greeter: Greeter;

    beforeEach(() => {
        greeter = new Greeter();
    })

    test('says Hello', () => {
        expect(greeter.greet()).toBe('Hello.');
    });

    test('says Hello Formally', () => {
        greeter.setFormality('formal');
        expect(greeter.greet()).toBe('Good evening, sir.');
    });

    test('says Hello Casually', () => {
        greeter.setFormality('casual');
        expect(greeter.greet()).toBe('Sup bro?');
    });

    test('says Hello Intimately', () => {
        greeter.setFormality('intimate');
        expect(greeter.greet()).toBe('Hello Darling!');
    });
});
