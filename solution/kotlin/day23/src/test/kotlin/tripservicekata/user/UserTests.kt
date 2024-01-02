package tripservicekata.user

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

private val rick = UserBuilder.aUser().build()
private val morty = UserBuilder.aUser().build()

class UserTests : StringSpec({
    "return false when 2 users are not friends" {
        UserBuilder.aUser()
            .friendsWith(rick)
            .build()
            .isFriendWith(morty) shouldBe false
    }

    "return true when 2 users are friends" {
        UserBuilder.aUser()
            .friendsWith(rick, morty)
            .build()
            .isFriendWith(morty) shouldBe true
    }
})