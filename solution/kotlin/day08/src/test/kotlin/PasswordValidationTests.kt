import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import password.ParsingError
import password.Password

data class InvalidPassword(val password: String, val reason: String)
class PasswordValidationTests : FunSpec({
    context("invalid passwords") {
        withData(
            InvalidPassword("", "Too short"),
            InvalidPassword("aa", "Too short"),
            InvalidPassword("xxxxxxx", "Too short"),
            InvalidPassword("adventofcraft", "No capital letter"),
            InvalidPassword("p@ssw0rd", "No capital letter"),
            InvalidPassword("ADVENTOFCRAFT", "No lower letter"),
            InvalidPassword("P@SSW0RD", "No lower letter"),
            InvalidPassword("Adventofcraft", "No number"),
            InvalidPassword("P@sswOrd", "No number"),
            InvalidPassword("Adventof09craft", "No special character"),
            InvalidPassword("PAssw0rd", "No special character"),
            InvalidPassword("Advent@of9Craft¨", "Invalid character"),
            InvalidPassword("P@ssw^rd1", "Invalid character")
        ) { (password, reason) ->
            Password.parse(password) shouldBeLeft ParsingError(reason)
        }
    }

    context("valid passwords") {
        withData("P@ssw0rd", "Advent0fCraft&") { password ->
            val parsedPassword = Password.parse(password)
            parsedPassword.isRight() shouldBe true
            parsedPassword.onRight { p -> p.value() shouldBe password }
        }
    }
})