package org.craftedsw.tripservicekata.user;

import io.vavr.collection.Seq;
import lombok.Getter;
import org.craftedsw.tripservicekata.trip.Trip;

@Getter
public class User {
    private final Seq<Trip> trips;
    private final Seq<User> friends;

    public User(Seq<Trip> trips, Seq<User> friends) {
        this.trips = trips;
        this.friends = friends;
    }

    public User addFriend(User friend) {
        return new User(trips, friends.append(friend));
    }

    public User addTrip(Trip trip) {
        return new User(trips.append(trip), friends);
    }

    public boolean isFriendWith(User anotherUser) {
        return friends.contains(anotherUser);
    }
}
