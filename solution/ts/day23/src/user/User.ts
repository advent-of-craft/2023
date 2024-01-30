import Trip from "../trip/Trip";

export class User {
    constructor(readonly trips: Trip[] = [], readonly friends: User[] = []) {
    }

    addFriend(friend: User): User {
        return new User(this.trips, [...this.friends, friend]);
    }

    addTrip(trip: Trip): User {
        return new User([...this.trips, trip], this.friends);
    }

    isFriendWith(anotherUser: User): boolean {
        return this.friends.includes(anotherUser);
    }
}
