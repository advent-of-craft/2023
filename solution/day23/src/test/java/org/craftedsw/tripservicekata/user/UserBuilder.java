package org.craftedsw.tripservicekata.user;

import io.vavr.collection.Seq;
import org.craftedsw.tripservicekata.trip.Trip;

import static io.vavr.collection.List.empty;
import static io.vavr.collection.List.of;

public class UserBuilder {
    private Seq<User> friends = empty();
    private Seq<Trip> trips = empty();

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder friendsWith(User... friends) {
        this.friends = of(friends);
        return this;
    }

    public UserBuilder travelledTo(Trip... trips) {
        this.trips = of(trips);
        return this;
    }

    public User build() {
        return new User(trips, friends);
    }
}
