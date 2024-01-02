import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import people.Person
import people.PetType.*
import java.lang.System.lineSeparator

class PopulationTests : FunSpec({
    val population: List<Person> = listOf(
        Person("Peter", "Griffin")
            .addPet(CAT, "Tabby", 2),
        Person("Stewie", "Griffin")
            .addPet(CAT, "Dolly", 3)
            .addPet(DOG, "Brian", 9),
        Person("Joe", "Swanson")
            .addPet(DOG, "Spike", 4),
        Person("Lois", "Griffin")
            .addPet(SNAKE, "Serpy", 1),
        Person("Meg", "Griffin")
            .addPet(BIRD, "Tweety", 1),
        Person("Chris", "Griffin")
            .addPet(TURTLE, "Speedy", 4),
        Person("Cleveland", "Brown")
            .addPet(HAMSTER, "Fuzzy", 1)
            .addPet(HAMSTER, "Wuzzy", 2),
        Person("Glenn", "Quagmire")
    )

    fun formatPopulation(): String {
        var response = ""

        for (person in population) {
            response += "${person.firstName} ${person.lastName}"

            if (person.pets.isNotEmpty()) {
                response += " who owns : "
            }
            for (pet in person.pets) {
                response += "${pet.name} "
            }
            if (population.last() != person) {
                response += lineSeparator()
            }
        }
        return response
    }

    test("people with their pets") {
        val response = formatPopulation()

        response shouldBe """Peter Griffin who owns : Tabby 
            |Stewie Griffin who owns : Dolly Brian 
            |Joe Swanson who owns : Spike 
            |Lois Griffin who owns : Serpy 
            |Meg Griffin who owns : Tweety 
            |Chris Griffin who owns : Speedy 
            |Cleveland Brown who owns : Fuzzy Wuzzy 
            |Glenn Quagmire""".trimMargin()
    }
})
