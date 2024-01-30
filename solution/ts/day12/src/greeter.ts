export type Greeter = () => string;

export class GreeterFactory {
    private static readonly casual = 'casual';
    private static readonly intimate = 'intimate';
    private static readonly formal = 'formal';

    private static readonly mapping: Record<string, Greeter> = {
        [GreeterFactory.casual]: () => 'Sup bro?',
        [GreeterFactory.formal]: () => 'Good evening, sir.',
        [GreeterFactory.intimate]: () => 'Hello Darling!'
    };

    static create = (formality?: string): Greeter =>
        GreeterFactory.mapping[formality] || GreeterFactory.createDefault();

    private static createDefault = (): Greeter => () => 'Hello.';
}