package org.craftedsw.tripservicekata.trip;

import io.vavr.collection.Seq;
import org.craftedsw.tripservicekata.user.User;

public class TripRepository {
    public Seq<Trip> findTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }
}
