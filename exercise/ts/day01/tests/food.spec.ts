import {v4 as uuidv4} from 'uuid';
import {Food} from "../src/food";

const ExpirationDate = new Date(2023, 11, 1);
const Inspector = uuidv4();

const NotFreshDate = new Date(ExpirationDate);
NotFreshDate.setDate(NotFreshDate.getDate() + 7);

const FreshDate = new Date(ExpirationDate);
FreshDate.setDate(FreshDate.getDate() - 7);

describe('Food', () => {
    test.each([
        [true, Inspector, NotFreshDate],
        [false, Inspector, FreshDate],
        [true, null, FreshDate],
        [false, null, NotFreshDate],
        [false, null, FreshDate]
    ])('is Not Edible If Not Fresh %s, %s, %s', (approvedForConsumption, inspectorId, now) => {
        const food = new Food(ExpirationDate, approvedForConsumption, inspectorId);
        expect(food.isEdible(() => now)).toBeFalsy();
    });

    test('is Edible When Fresh', () => {
        const food = new Food(ExpirationDate, true, Inspector);
        expect(food.isEdible(() => FreshDate)).toBeTruthy();
    });
});