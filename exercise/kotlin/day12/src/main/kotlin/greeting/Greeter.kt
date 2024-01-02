package greeting

class Greeter(private val formality: String? = null) {
    fun greet(): String {
        if (formality == null) {
            return "Hello."
        }
        return if (formality == "formal") {
            "Good evening, sir."
        } else if (formality == "casual") {
            "Sup bro?"
        } else if (formality == "intimate") {
            "Hello Darling!"
        } else {
            "Hello."
        }
    }
}