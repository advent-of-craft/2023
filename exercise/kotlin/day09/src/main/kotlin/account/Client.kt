package account

import java.lang.System.lineSeparator

class Client(private val orderLines: Map<String, Double>) {
    var totalAmount = 0.0

    fun toStatement(): String =
        orderLines
            .map { (key, value) -> formatLine(key, value) }
            .joinToString(lineSeparator()) + lineSeparator() + "Total : " + totalAmount + "€"

    private fun formatLine(name: String, value: Double): String {
        totalAmount += value
        return name + " for " + value + "€"
    }
}