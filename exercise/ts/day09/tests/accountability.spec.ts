import {Client} from '../src/accountability'; // Adjust the import path as needed

describe('Client Tests', () => {
    const client = new Client({
        'Tenet Deluxe Edition': 45.99,
        'Inception': 30.50,
        'The Dark Knight': 30.50,
        'Interstellar': 23.98
    });

    test('Client Should Return Statement', () => {
        const statement = client.toStatement();

        expect(client.getTotalAmount()).toBeCloseTo(130.97, 2);
        expect(statement.trim()).toEqual(
            'Tenet Deluxe Edition for 45.99€\n' +
            'Inception for 30.50€\n' +
            'The Dark Knight for 30.50€\n' +
            'Interstellar for 23.98€\n' +
            'Total : 130.97€'
        );
    });
});
