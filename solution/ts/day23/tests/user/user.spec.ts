import {UserBuilder} from "./userBuilder";

describe('user', () => {
    const rick = UserBuilder.aUser().build();
    const morty = UserBuilder.aUser().build();

    test('should return false when 2 users are not friends', () => {
        const user = UserBuilder.aUser()
            .friendsWith(rick)
            .build();

        expect(user.isFriendWith(morty)).toBeFalsy();
    });

    test('should return true when 2 users are friends', () => {
        const user = UserBuilder.aUser()
            .friendsWith(rick, morty)
            .build();

        expect(user.isFriendWith(morty)).toBeTruthy();
    });
});
