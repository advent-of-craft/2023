package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.craftedsw.tripservicekata.user.UserBuilder.aUser;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class GetTripsByUserShould {
    private final User registeredUser = aUser().build();
    private TripService tripService;

    @Mock
    private TripRepository repositoryMock;

    @BeforeEach
    void setup() {
        tripService = new TripService(repositoryMock);
    }

    @Nested
    class return_An_Error {
        private final User guest = null;

        @Test
        void when_user_is_not_loggedIn() {
            assertThat(tripService.retrieveFriendTrips(registeredUser, guest).getCause())
                    .isInstanceOf(UserNotLoggedInException.class);
        }
    }

    @Nested
    class return_ {
        private final Trip lisbon = new Trip();
        private final Trip springfield = new Trip();
        private final User anotherUser = aUser().build();

        @Test
        void no_trips_when_logged_user_is_not_a_friend_of_the_target_user() {
            var aUserWithTrips = aUser()
                    .friendsWith(anotherUser)
                    .travelledTo(lisbon)
                    .build();

            assertThat(tripService.retrieveFriendTrips(aUserWithTrips, registeredUser).get())
                    .isEmpty();
        }

        @Test
        void all_the_target_user_trips_when_logged_user_and_target_user_are_friends() {
            var aUserWithTrips = aUser()
                    .friendsWith(anotherUser, registeredUser)
                    .travelledTo(lisbon, springfield)
                    .build();

            when(repositoryMock.findTripsByUser(aUserWithTrips))
                    .thenReturn(aUserWithTrips.getTrips());

            assertThat(tripService.retrieveFriendTrips(aUserWithTrips, registeredUser).get())
                    .hasSize(2)
                    .contains(lisbon, springfield);
        }
    }
}