package account

import java.lang.System.lineSeparator

class Client(private val orderLines: Map<String, Double>) {
    fun statement(): String =
        orderLines
            .map { (key, value) -> formatLine(key, value) }
            .joinToString(lineSeparator(), "", lineSeparator()) + formatTotal()

    fun totalAmount(): Double = orderLines.values.sum()
    private fun formatLine(name: String, value: Double): String = name + " for " + value + "€"
    private fun formatTotal(): String = "Total : " + totalAmount() + "€"
}