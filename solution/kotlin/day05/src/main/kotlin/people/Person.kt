package people

enum class PetType {
    CAT,
    DOG,
    HAMSTER,
    TURTLE,
    BIRD,
    SNAKE
}

data class Pet(val type: PetType, val name: String, val age: Int)
data class Person(val firstName: String, val lastName: String, val pets: List<Pet> = emptyList()) {
    fun addPet(petType: PetType, name: String, age: Int): Person =
        Person(firstName, lastName, pets + Pet(petType, name, age))
}
