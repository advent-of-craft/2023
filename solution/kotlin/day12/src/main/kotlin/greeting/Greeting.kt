package greeting

typealias Greeter = () -> String

object Greeting {
    const val CASUAL = "casual"
    const val INTIMATE = "intimate"
    const val FORMAL = "formal"

    fun create(formality: String): Greeter = when (formality) {
        CASUAL -> { -> "Sup bro?" }
        FORMAL -> { -> "Good evening, sir." }
        INTIMATE -> { -> "Hello Darling!" }
        else -> create()
    }

    fun create(): Greeter = { "Hello." }
}