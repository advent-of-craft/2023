import {TripRepository} from "./TripRepository";
import {User} from "../user/User";
import Trip from "./Trip";
import * as E from "fp-ts/Either";
import UserNotLoggedInException from "../exception/UserNotLoggedInException";

const noTrips: Trip[] = [];

export class TripService {
    constructor(private readonly repository: TripRepository) {
    }

    retrieveFriendTrips = (user: User, loggedUser: User): E.Either<UserNotLoggedInException, Trip[]> =>
        this.checkUser(loggedUser)
            ? E.right(this.retrieveFriendsSafely(user, loggedUser))
            : E.left(new UserNotLoggedInException());
    private retrieveFriendsSafely = (user: User, loggedUser: User): Trip[] =>
        user.isFriendWith(loggedUser)
            ? this.findTripsByUser(user) :
            noTrips;
    private checkUser = (loggedUser: User): boolean => loggedUser != null;
    private findTripsByUser = (user: User): Trip[] => this.repository.findTripsByUser(user);
}