package tripservicekata.trip

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import org.craftedsw.tripservicekata.exception.CollaboratorCallException
import org.craftedsw.tripservicekata.trip.TripRepository
import tripservicekata.user.UserBuilder

class RepositoryTests : StringSpec({
    "retrieve user trips throw an exception" {
        shouldThrow<CollaboratorCallException> {
            TripRepository()
                .findTripsByUser(UserBuilder.aUser().build())
        }
    }
})