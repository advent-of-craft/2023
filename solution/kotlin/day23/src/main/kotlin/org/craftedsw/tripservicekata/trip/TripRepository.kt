package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.user.User

class TripRepository {
    fun findTripsByUser(user: User): List<Trip> = TripDAO.findTripsByUser(user)
}