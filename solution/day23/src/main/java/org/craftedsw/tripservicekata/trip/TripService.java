package org.craftedsw.tripservicekata.trip;

import io.vavr.collection.Seq;
import io.vavr.control.Try;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.function.Supplier;

import static io.vavr.collection.List.empty;
import static io.vavr.control.Try.failure;
import static io.vavr.control.Try.success;

public class TripService {
    private static final Seq<Trip> NO_TRIPS = empty();
    private final TripRepository repository;

    public TripService(TripRepository repository) {
        this.repository = repository;
    }

    public Try<Seq<Trip>> retrieveFriendTrips(User user, User loggedUser) {
        return checkUser(loggedUser, () -> retrieveFriendsSafely(user, loggedUser));
    }

    private Seq<Trip> retrieveFriendsSafely(User user, User loggedUser) {
        return user.isFriendWith(loggedUser)
                ? findTripsByUser(user)
                : NO_TRIPS;
    }

    private Try<Seq<Trip>> checkUser(User loggedUser,
                                     Supplier<Seq<Trip>> continueWith) {
        return (loggedUser != null)
                ? success(continueWith.get())
                : failure(new UserNotLoggedInException());
    }

    protected Seq<Trip> findTripsByUser(User user) {
        return repository.findTripsByUser(user);
    }
}