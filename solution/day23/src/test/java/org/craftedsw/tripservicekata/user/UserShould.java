package org.craftedsw.tripservicekata.user;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.craftedsw.tripservicekata.user.UserBuilder.aUser;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
class UserShould {
    private final User rick = aUser().build();
    private final User morty = aUser().build();

    @Test
    void return_false_when_2_users_are_not_friends() {
        var user = aUser()
                .friendsWith(rick)
                .build();

        assertThat(user.isFriendWith(morty))
                .isFalse();
    }

    @Test
    void return_true_when_2_users_are_friends() {
        var user = aUser()
                .friendsWith(rick, morty)
                .build();

        assertThat(user.isFriendWith(morty))
                .isTrue();
    }
}