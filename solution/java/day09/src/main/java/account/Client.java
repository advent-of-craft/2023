package account;

import java.util.Map;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

public class Client {
    private final Map<String, Double> orderLines;

    public Client(Map<String, Double> orderLines) {
        this.orderLines = orderLines;
    }

    public String toStatement() {
        return orderLines.entrySet()
                .stream()
                .map(entry -> formatLine(entry.getKey(), entry.getValue()))
                .collect(joining(lineSeparator(), "", formatTotal()));
    }

    private String formatLine(String name, Double value) {
        return name + " for " + value + "€";
    }

    private String formatTotal() {
        return lineSeparator() + "Total : " + totalAmount() + "€";
    }

    public Double totalAmount() {
        return orderLines.values()
                .stream()
                .mapToDouble(x -> x)
                .sum();
    }
}