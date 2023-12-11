package games;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class FizzBuzz {
    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int FIZZ = 3;
    private static final int BUZZ = 5;
    private static final int FIZZBUZZ = 15;
    private static final boolean DEFAULT = true;

    private static final Map<Predicate<Integer>, Function<Integer, String>> mapping;

    static {
        mapping = new LinkedHashMap<>();
        mapping.put(i -> is(FIZZBUZZ, i), i -> "FizzBuzz");
        mapping.put(i -> is(FIZZ, i), i -> "Fizz");
        mapping.put(i -> is(BUZZ, i), i -> "Buzz");
        mapping.put(i -> DEFAULT, Object::toString);
    }

    public static String convert(Integer input) throws OutOfRangeException {
        return mapping.entrySet()
                .stream()
                .filter(f -> !isOutOfRange(input))
                .filter(f -> f.getKey().test(input))
                .findFirst()
                .map(v -> v.getValue().apply(input))
                .orElseThrow(OutOfRangeException::new);
    }

    private static boolean is(Integer divisor, Integer input) {
        return input % divisor == 0;
    }

    private static boolean isOutOfRange(Integer input) {
        return input < MIN || input > MAX;
    }
}