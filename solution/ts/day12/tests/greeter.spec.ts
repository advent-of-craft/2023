import {Greeter, GreeterFactory} from '../src/greeter';

describe('Greeter', () => {
    test('says Hello', () => {
        assertGreeter(GreeterFactory.create(), 'Hello.');
    });

    test('says Hello Formally', () => {
        assertGreeter(GreeterFactory.create('formal'), 'Good evening, sir.');
    });

    test('says Hello Casually', () => {
        assertGreeter(GreeterFactory.create('casual'), 'Sup bro?');
    });

    test('says Hello Intimately', () => {
        assertGreeter(GreeterFactory.create('intimate'), 'Hello Darling!');
    });

    function assertGreeter(greeter: Greeter, expectedResult: string) {
        expect(greeter()).toBe(expectedResult);
    }
});

