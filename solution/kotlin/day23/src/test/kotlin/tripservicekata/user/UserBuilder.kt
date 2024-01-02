package tripservicekata.user

import org.craftedsw.tripservicekata.trip.Trip
import org.craftedsw.tripservicekata.user.User


class UserBuilder {
    private var friends: List<User> = emptyList()
    private var trips: List<Trip> = emptyList()
    fun friendsWith(vararg friends: User): UserBuilder {
        this.friends = friends.toList()
        return this
    }

    fun travelledTo(vararg trips: Trip): UserBuilder {
        this.trips = trips.toList()
        return this
    }

    fun build(): User = User(trips, friends)

    companion object {
        fun aUser(): UserBuilder = UserBuilder()
    }
}