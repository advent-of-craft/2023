package tripservicekata.trip

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.trip.Trip
import org.craftedsw.tripservicekata.trip.TripRepository
import org.craftedsw.tripservicekata.trip.TripService
import org.craftedsw.tripservicekata.user.User
import tripservicekata.user.UserBuilder

class RetrieveTripsByUser : StringSpec({
    lateinit var repository: TripRepository
    lateinit var tripService: TripService

    val registeredUser = UserBuilder.aUser().build()
    val guest: User? = null

    val lisbon = Trip()
    val springfield = Trip()
    val anotherUser = UserBuilder.aUser().build()

    beforeEach {
        repository = mockk()
        tripService = TripService(repository)
    }

    "no trips when logged user is not a friend of the target user" {
        val aUserWithTrips = UserBuilder.aUser()
            .friendsWith(anotherUser)
            .travelledTo(lisbon)
            .build()

        tripService.retrieveFriendTrips(aUserWithTrips, registeredUser) shouldBeSuccess emptyList()
    }

    "all the target user trips when logged user and target user are friends" {
        val aUserWithTrips = UserBuilder.aUser()
            .friendsWith(anotherUser, registeredUser)
            .travelledTo(lisbon, springfield)
            .build()

        every { repository.findTripsByUser(aUserWithTrips) } returns aUserWithTrips.trips

        tripService.retrieveFriendTrips(aUserWithTrips, registeredUser) shouldBeSuccess listOf(
            lisbon, springfield
        )
    }

    "an error when user is not logged in" {
        tripService.retrieveFriendTrips(registeredUser, guest).shouldBeFailure<UserNotLoggedInException>()
    }
})