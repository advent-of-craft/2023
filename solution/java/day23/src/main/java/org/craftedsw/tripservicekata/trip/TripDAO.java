package org.craftedsw.tripservicekata.trip;

import io.vavr.collection.Seq;
import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.User;

public class TripDAO {

    public static Seq<Trip> findTripsByUser(User user) {
        throw new CollaboratorCallException(
                "TripDAO should not be invoked on an unit test.");
    }

}