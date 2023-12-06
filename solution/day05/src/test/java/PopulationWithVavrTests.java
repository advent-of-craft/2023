import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import people.Person;
import people.Pet;
import people.PetType;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

class PopulationWithVavrTests {
    private static Seq<Person> population;

    @BeforeAll
    static void setup() {
        population = List.of(
                new Person("Peter", "Griffin")
                        .addPet(PetType.CAT, "Tabby", 2),
                new Person("Stewie", "Griffin")
                        .addPet(PetType.CAT, "Dolly", 3)
                        .addPet(PetType.DOG, "Brian", 9),
                new Person("Joe", "Swanson")
                        .addPet(PetType.DOG, "Spike", 4),
                new Person("Lois", "Griffin")
                        .addPet(PetType.SNAKE, "Serpy", 1),
                new Person("Meg", "Griffin")
                        .addPet(PetType.BIRD, "Tweety", 1),
                new Person("Chris", "Griffin")
                        .addPet(PetType.TURTLE, "Speedy", 4),
                new Person("Cleveland", "Brown")
                        .addPet(PetType.HAMSTER, "Fuzzy", 1)
                        .addPet(PetType.HAMSTER, "Wuzzy", 2),
                new Person("Glenn", "Quagmire")
        );
    }

    @Test
    void peopleWithTheirPets() {
        assertThat(formatPopulation())
                .hasToString("Peter Griffin who owns : Tabby " + lineSeparator() +
                        "Stewie Griffin who owns : Dolly Brian " + lineSeparator() +
                        "Joe Swanson who owns : Spike " + lineSeparator() +
                        "Lois Griffin who owns : Serpy " + lineSeparator() +
                        "Meg Griffin who owns : Tweety " + lineSeparator() +
                        "Chris Griffin who owns : Speedy " + lineSeparator() +
                        "Cleveland Brown who owns : Fuzzy Wuzzy " + lineSeparator() +
                        "Glenn Quagmire");
    }

    private static String formatPopulation() {
        return population
                .map(PopulationWithVavrTests::formatPerson)
                .mkString(lineSeparator());
    }

    private static String formatPerson(Person person) {
        return format("%s %s", person.firstName(), person.lastName()) +
                (!person.pets().isEmpty() ? formatPets(person) : "");
    }

    private static String formatPets(Person person) {
        return List.ofAll(person.pets())
                .map(Pet::name)
                .mkString(" who owns : ", " ", " ");
    }

    @Test
    void whoOwnsTheYoungestPet() {
        assertThat(population
                .minBy(PopulationWithVavrTests::youngestPetAgeOfThePerson)
                .get()
                .firstName()
        ).isEqualTo("Lois");
    }

    private static int youngestPetAgeOfThePerson(Person p) {
        return List.ofAll(p.pets())
                .map(Pet::age)
                .min()
                .getOrElse(MAX_VALUE);
    }
}
