package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User

class TripService(private val tripRepository: TripRepository) {
    private val NO_TRIPS = emptyList<Trip>();

    fun retrieveFriendTrips(user: User, loggedUser: User?): Result<List<Trip>> {
        return checkUser(loggedUser) { retrieveFriendsSafely(user, loggedUser!!) }
    }

    private fun retrieveFriendsSafely(user: User, loggedUser: User): List<Trip> =
        if (user.isFriendWith(loggedUser)) findTripsByUser(user)
        else NO_TRIPS

    private fun checkUser(loggedUser: User?, continueWith: () -> List<Trip>): Result<List<Trip>> =
        if (loggedUser != null) Result.success(continueWith())
        else Result.failure(UserNotLoggedInException())

    private fun findTripsByUser(user: User): List<Trip> = tripRepository.findTripsByUser(user)
}