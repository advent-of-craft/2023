## Day 10: Dot not use "if" statement.
- For now our code looks like this
```java
private static String convertSafely(Integer input) {
    if (is(FIZZBUZZ, input)) {
        return "FizzBuzz";
    }
    if (is(FIZZ, input)) {
        return "Fizz";
    }
    if (is(BUZZ, input)) {
        return "Buzz";
    }
    return input.toString();
}
```

- We may define a `Map` that could contain the mapping logic
  - It would have a signature like this : `Predicate<Integer>, Function<Integer, String>` -> `String`
  - It contains a `Predicate` to check if the input ensures it and the corresponding value `function` in case of a match

```java
public static final boolean DEFAULT = true;

private static final Map<Predicate<Integer>, Function<Integer, String>> mapping;

static {
    mapping = new LinkedHashMap<>();
    mapping.put(i -> is(FIZZBUZZ, i), i -> "FizzBuzz");
    mapping.put(i -> is(FIZZ, i), i -> "Fizz");
    mapping.put(i -> is(BUZZ, i), i -> "Buzz");
    // Default value for numbers in range
    mapping.put(i -> DEFAULT, Object::toString);
}
```

- Now let's use it in our `convert` method
```java
public static String convert(Integer input) throws OutOfRangeException {
    return mapping.entrySet()
            .stream()
            // use filter function to check if the input is valid
            .filter(f -> !isOutOfRange(input))
            // We start by filtering the stream based on wether the Predicate is matching the value or not
            .filter(f -> f.getKey().test(input))
            // We keep only the first entry
            .findFirst()
            // We take the associated value (Function<Integer, String>)
            .map(v -> v.getValue().apply(input))
            // We throw the Exception if no match
            .orElseThrow(OutOfRangeException::new);
}
```

- Then we can clean the code by removing the `convertSafely` method
![remove concert safely](img/remove-convert-safely.png)

- Here is how our code looks like at the end

```java
package games;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class FizzBuzz {
    // We fix the MIN value as discussed in previous days 😉
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
```