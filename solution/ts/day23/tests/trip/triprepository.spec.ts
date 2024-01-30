import {TripRepository} from "../../src/trip/TripRepository";
import {UserBuilder} from "../user/userBuilder";
import CollaboratorCallException from "../../src/exception/CollaboratorCallException";

describe('trip repository', () => {
    test('retrieve user throw an exception', () => {
        expect(() => new TripRepository().findTripsByUser(UserBuilder.aUser().build())).toThrow(CollaboratorCallException);
    });
});