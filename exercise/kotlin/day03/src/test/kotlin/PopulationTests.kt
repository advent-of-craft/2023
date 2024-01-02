import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import people.Person
import people.PetType.*

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

    test("who owns the youngest pet") {
        val filtered = population.filter { person -> person.pets.isNotEmpty() }
            .minBy { person -> person.pets.map { p -> p.age }.min() }
        filtered.firstName shouldBe "Lois"
    }
})
