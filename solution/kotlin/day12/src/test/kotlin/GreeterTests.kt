import greeting.Greeting
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GreeterTests : StringSpec({
    "say hello" {
        Greeting.create()() shouldBe "Hello."
    }

    "say hello formally" {
        Greeting.create(Greeting.FORMAL)() shouldBe "Good evening, sir."
    }

    "say hello casually" {
        Greeting.create(Greeting.CASUAL)() shouldBe "Sup bro?"
    }

    "say hello intimately" {
        Greeting.create(Greeting.INTIMATE)() shouldBe "Hello Darling!"
    }
})