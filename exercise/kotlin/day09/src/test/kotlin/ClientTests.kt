import account.Client
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ClientTests : StringSpec({
    val client = Client(
        mapOf(
            "Tenet Deluxe Edition" to 45.99,
            "Inception" to 30.50,
            "The Dark Knight" to 30.50,
            "Interstellar" to 23.98
        )
    )

    "client should return statement" {
        val statement = client.toStatement()
        client.totalAmount shouldBe 130.97
        statement shouldBe """|Tenet Deluxe Edition for 45.99€
                   |Inception for 30.5€
                   |The Dark Knight for 30.5€
                   |Interstellar for 23.98€
                   |Total : 130.97€""".trimMargin()
    }
})
