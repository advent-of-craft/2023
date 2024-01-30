import Trip from "../../src/trip/Trip";
import {User} from "../../src/user/User";

export class UserBuilder {
    private friends: User[] = [];
    private trips: Trip[] = [];

    static aUser(): UserBuilder {
        return new UserBuilder();
    }

    friendsWith(...friends: User[]): UserBuilder {
        this.friends = friends;
        return this;
    }

    travelledTo(...trips: Trip[]): UserBuilder {
        this.trips = trips;
        return this;
    }

    build(): User {
        return new User(this.trips, this.friends);
    }
}