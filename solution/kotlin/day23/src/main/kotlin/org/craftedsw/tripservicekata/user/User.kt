package org.craftedsw.tripservicekata.user

import org.craftedsw.tripservicekata.trip.Trip

class User(val trips: List<Trip>, val friends: List<User>) {
    fun addFriend(user: User) = User(trips = trips, friends = friends + user)
    fun addTrip(trip: Trip) = User(trips = trips + trip, friends = friends)
    fun isFriendWith(anotherUser: User?): Boolean = friends.contains(anotherUser)
}
