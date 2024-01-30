import {Client} from '../src/accountability'; // Adjust the import path as needed

describe('client', () => {
    const client = new Client({
        'Tenet Deluxe Edition': 45.99,
        'Inception': 30.50,
        'The Dark Knight': 30.50,
        'Interstellar': 23.98
    });

    test('should return statement', () => {
        expect(client.statement()).toEqual(
            'Tenet Deluxe Edition for 45.99€\n' +
            'Inception for 30.50€\n' +
            'The Dark Knight for 30.50€\n' +
            'Interstellar for 23.98€\n' +
            'Total : 130.97€'
        );
    });

    test('should calculate amount', () => {
        expect(client.totalAmount()).toBeCloseTo(130.97, 2);
    });
});
