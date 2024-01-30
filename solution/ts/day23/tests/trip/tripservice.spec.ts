import {TripService} from "../../src/trip/TripService";
import {TripRepository} from "../../src/trip/TripRepository";
import {UserBuilder} from "../user/userBuilder";
import UserNotLoggedInException from "../../src/exception/UserNotLoggedInException";
import Trip from "../../src/trip/Trip";
import * as E from "fp-ts/Either";

describe('get trips by user', () => {
    let tripService: TripService;
    let repository: jest.Mocked<TripRepository>;
    const registeredUser = UserBuilder.aUser().build();

    beforeEach(() => {
        repository = {
            findTripsByUser: jest.fn(),
        };
        tripService = new TripService(repository);
    });

    describe('return', () => {
        const lisbon = new Trip();
        const springfield = new Trip();
        const anotherUser = UserBuilder.aUser().build();

        test('no trips when logged user is not a friend of the target user', async () => {
            const aUserWithTrips = UserBuilder.aUser()
                .friendsWith(anotherUser)
                .travelledTo(lisbon)
                .build();

            assertTrips(
                tripService.retrieveFriendTrips(aUserWithTrips, registeredUser),
                []
            );
        });

        test('all the target user trips when logged user and target user are friends', async () => {
            const aUserWithTrips = UserBuilder.aUser()
                .friendsWith(anotherUser, registeredUser)
                .travelledTo(lisbon, springfield)
                .build();

            repository.findTripsByUser.mockReturnValue(aUserWithTrips.trips);

            assertTrips(
                tripService.retrieveFriendTrips(aUserWithTrips, registeredUser),
                [lisbon, springfield]
            );
        });

        const assertTrips = (result: E.Either<UserNotLoggedInException, Trip[]>, expectedTrips: Trip[]) => {
            expect(E.isRight(result)).toBeTruthy();

            if (E.isRight(result)) {
                expect(result.right).toStrictEqual(expectedTrips);
            }
        }
    });

    describe('return an Error', () => {
        const guest = null;

        test('when user is not logged in', () => {
            let result = tripService.retrieveFriendTrips(registeredUser, guest);
            expect(E.isLeft(result)).toBeTruthy();

            if (E.isLeft(result)) {
                expect(result.left).toBeInstanceOf(UserNotLoggedInException);
            }
        });
    });
});
