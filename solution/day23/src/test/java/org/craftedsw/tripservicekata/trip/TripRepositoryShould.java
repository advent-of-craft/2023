package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.UserBuilder;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class TripRepositoryShould {
    private final TripRepository tripRepository = new TripRepository();

    @Test
    void retrieve_user_trips_throw_an_exception() {
        assertThatThrownBy(() -> tripRepository.findTripsByUser(UserBuilder.aUser().build()))
                .isInstanceOf(CollaboratorCallException.class);
    }
}
