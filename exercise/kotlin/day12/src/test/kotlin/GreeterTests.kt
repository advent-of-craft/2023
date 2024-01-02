import greeting.Greeter
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GreeterTests : StringSpec({
    "say hello" {
        val greeter = Greeter()
        greeter.greet() shouldBe "Hello."
    }

    "say hello formally" {
        val greeter = Greeter("formal")
        greeter.greet() shouldBe "Good evening, sir."
    }

    "say hello casually" {
        val greeter = Greeter("casual")
        greeter.greet() shouldBe "Sup bro?"
    }

    "say hello intimately" {
        val greeter = Greeter("intimate")
        greeter.greet() shouldBe "Hello Darling!"
    }
})