## Day 22: Design a diamond program using T.D.D and Property-Based Testing.
We think about a possible contract for our class:
- It may contain a `print` method taking the `endCharacter` as parameter
  - It could look like `Character` -> `Option<String>`
  - We could use an `Option` because there are some cases that are not supported (ex: '^', '1', ...)

Let's design and implement it starting from less to more complex properties.

### None for invalid characters

```text
for all (invalidEndCharacter)
such that print(invalidEndCharacter) fail
```

🔴 We start by creating this first property

```java
@Test
void fail_for_invalid_end_character() {
    Property.def("None for invalid characters")
            .forAll(invalidEndCharacters)
            .suchThat(endCharacter -> diamond.Diamond.print(endCharacter).isEmpty())
            .check()
            .assertIsSatisfied();
}
```

- We can generate production code from the `property`
![Generate code from usage](img/generate-code-from-first-property.png)

- The `Diamond` looks like this

```java
public class Diamond {
    public static Option<String> print(char endCharacter) {
        return null;
    }
}
```

- We work on how to generate `invalidEndCharacters`

```java
var invalidEndCharacters = Gen.choose(' ', '~')
        .filter(x -> !Character.isLetter(x))
        .arbitrary();
```

- We check the generation of random characters
  - Here is an example of values

```text
[ ) < > 2 " 9 * 0 ( > >   < ( @ ] | 6 ) ` 2 ? - 4 + # * < % 8 }    
```

🟢 We make it pass by `hardcoding` the result

```java
public static Option<String> print(char endCharacter) {
    return Option.none();
}
```

🔵 We clean a little bit the test

```java
@Test
void fail_for_invalid_end_character() {
    var notALetter = choose(' ', '~')
            .filter(x -> !Character.isLetter(x))
            .arbitrary();

    def("None for invalid characters")
            .forAll(notALetter)
            .suchThat(endCharacter -> print(endCharacter).isEmpty())
            .check()
            .assertIsSatisfied();
}
```

### Horizontally symmetric

```text
for all (validEndCharacter)
such that diamond(validEndCharacter) == reverse(diamond(validEndCharacter))
```

🔴 We add a first version of the property

```java
@Test
void be_horizontally_symmetric() {
    var upperLetterGenerator = choose('A', 'Z').arbitrary();

    def("Horizontally symmetric for valid end characters")
            .forAll(upperLetterGenerator)
            .suchThat(endCharacter -> {
                // Not sure about that
                var diamond = print(endCharacter).get();
                var lines = List.of(diamond.split(lineSeparator()));
                var reversedDiamond = lines.reverse();
        
                return lines.equals(reversedDiamond);

            })
            .check()
            .assertIsSatisfied();
}
```

🟢 We make it pass by `hardcoding` the return value

```java
public static Option<String> print(char endCharacter) {
    if (endCharacter >= 'A' && endCharacter <= 'Z') {
        return Some("");
    }
    return Option.none();
}
```

🔵 We extract the guard and use ternary operator

```java
public class Diamond {
    public static Option<String> print(char endCharacter) {
        return isValidCharacter(endCharacter)
                ? Some("")
                : Option.none();
    }

    private static boolean isValidCharacter(char endCharacter) {
        return endCharacter >= 'A' && endCharacter <= 'Z';
    }
}
```

### Is a square  (height = width)

```text
for all (validEndCharacter)
such that diamond(validEndCharacter) is a square
```

The result `String` should a square meaning that each line contains the same number of characters than the number of lines.

🔴 Let's identify if it is a square

```java
@Test
void be_a_square() {
    var upperLetterGenerator = choose('A', 'Z').arbitrary();

    def("A square for valid end characters")
            .forAll(upperLetterGenerator)
            .suchThat(endCharacter -> {
                var diamond = print(endCharacter).get().split(lineSeparator());
                return List.of(diamond).forAll(line -> line.length() == diamond.length);
            })
            .check()
            .assertIsSatisfied();
}
```

🟢 We can make it pass by simply returning 'A'

```java
public static Option<String> print(char endCharacter) {
    return isValidCharacter(endCharacter)
            ? Some("A")
            : Option.none();
}
```

🔵 We have plenty of duplication in our tests

```java
class DiamondTests {
    @Test
    void fail_for_invalid_end_character() {
        var notALetterGenerator = choose(' ', '~')
                .filter(x -> !Character.isLetter(x))
                .arbitrary();

        def("None for invalid end characters")
                .forAll(notALetterGenerator)
                .suchThat(endCharacter -> print(endCharacter).isEmpty())
                // check and satisfied on each property
                .check()
                .assertIsSatisfied();
    }

    @Test
    void be_horizontally_symmetric() {
        var upperLetterGenerator = choose('A', 'Z').arbitrary();

        def("Horizontally symmetric for valid end characters")
                // Extract this
                .forAll(upperLetterGenerator)
                .suchThat(endCharacter -> {
                    var diamond = print(endCharacter).get();
                    // retrieving the lines
                    var lines = List.of(diamond.split(lineSeparator()));
                    var reversedDiamond = lines.reverse();

                    return lines.equals(reversedDiamond);

                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void be_a_square() {
        var upperLetterGenerator = choose('A', 'Z').arbitrary();

        def("A square for valid end characters")
                .forAll(upperLetterGenerator)
                .suchThat(endCharacter -> {
                    var diamond = print(endCharacter).get().split(lineSeparator());
                    return List.of(diamond).forAll(line -> line.length() == diamond.length);
                })
                .check()
                .assertIsSatisfied();
    }
}
```

- By extracting the logic, we end up with tests like this

```java
class DiamondTests {
    private final Arbitrary<Character> upperLetterGenerator = choose('A', 'Z').arbitrary();

    @Test
    void be_horizontally_symmetric() {
        checkProperty("Horizontally symmetric for valid end characters",
                diamond -> diamond.equals(diamond.reverse())
        );
    }

    @Test
    void be_a_square() {
        checkProperty("A square for valid end characters (height = width)",
                diamond -> diamond.forAll(line -> line.length() == diamond.length())
        );
    }

    private void checkProperty(String name,
                               Predicate<Seq<String>> property) {
        def(name).forAll(upperLetterGenerator)
                .suchThat(endCharacter -> property.test(
                        List.of(print(endCharacter).get()
                                .split(lineSeparator()))
                ))
                .check()
                .assertIsSatisfied();
    }

    @Nested
    class Fail {
        @Test
        void fail_for_invalid_end_character() {
            var notALetterGenerator = choose(' ', '~')
                    .filter(x -> !Character.isLetter(x))
                    .arbitrary();

            def("None for invalid end characters")
                    .forAll(notALetterGenerator)
                    .suchThat(endCharacter -> print(endCharacter).isEmpty())
                    .check()
                    .assertIsSatisfied();
        }
    }
}
```

### Contain 2 identical letters per line

```text
for all (validEndCharacter)
such that each line in diamond(validEndCharacter) contains 2 identical letters except first and last 
```

```java
@Test
void contains_2_letters_per_line() {
    checkProperty("Contains 2 identical letters except first and last",
            diamond -> diamond
                    .drop(1)
                    .dropRight(1)
                    .map(line -> line.replaceAll(" ", ""))
                    .forAll(c -> c.length() == 2)
    );
}
```

😬 It is already green...

It is maybe a signal that we need to iterate on the implementation

```java
public static Option<String> print(char endCharacter) {
    return isValidCharacter(endCharacter)
            ? Some(concatLines(generateDiamond(endCharacter)))
            : Option.none();
}

private static Seq<String> generateDiamond(char endCharacter) {
    // We would like a pipeline like this
    // Generate lines of the same size -> (endCharacter x 2) - 1
    // Empty for now
}
```

- We design from the implementation

![Generate code from implementation](img/generate-code-impl.png)

```java
private static Seq<String> generateDiamond(char endCharacter) {
    // lines
    return Stream.range(START, (endCharacter * 2) - 1)
            // take the character that should appear 2 times per line
            .map(i -> (char) i.intValue())
            .map(c -> toLine(c, endCharacter));
}
```

🔴 Our properties are now failing, we can triangulate the algorithm

- We experiment and learn from the properties

```java
private static Seq<String> generateDiamond(char endCharacter) {
    return Stream.range(START, START + (endCharacter - START))
            .map(i -> (char) i.intValue())
            .map(c -> toLine(c, endCharacter));
}

private static String toLine(char character, char endCharacter) {
    return generateEmptyCharacters(endCharacter - character);
}

private static String generateEmptyCharacters(int count) {
    return Stream.range(0, count + 1)
            .foldLeft("", (acc, c) -> acc + "X");
}
```

- We fix the property `be_horizontally_symmetric` by iterating on the code

```java
public static final char START = 'A';

private Diamond() {
}

public static Option<String> print(char endCharacter) {
    return isValidCharacter(endCharacter)
            ? Some(concatLines(generateDiamond(endCharacter)))
            : Option.none();
}

private static Seq<String> generateDiamond(char endCharacter) {
    return fullDiamond(generateHalfDiamond(endCharacter));
}

private static Seq<String> fullDiamond(Seq<String> halfDiamond) {
    return halfDiamond.appendAll(
            halfDiamond.reverse().drop(1)
    );
}

private static Seq<String> generateHalfDiamond(char endCharacter) {
    return Stream.range(START, START + (endCharacter - START) + 1)
            .map(i -> (char) i.intValue())
            .map(c -> toLine(c, endCharacter));
}

private static String toLine(char character, char endCharacter) {
    var out = outer(character, endCharacter);
    var inner = character != START
            ? generateEmptyCharacters((character - START) * 2 - 1)
            : "";

    return out + character + inner + out;
}

private static String outer(char character, char endCharacter) {
    return generateEmptyCharacters(endCharacter - character);
}

private static String generateEmptyCharacters(int count) {
    return Stream.range(0, count + 1)
            .foldLeft("", (acc, c) -> acc + " ");
}

private static String concatLines(Seq<String> lines) {
    return lines.mkString(System.lineSeparator());
}

private static boolean isValidCharacter(char endCharacter) {
    return endCharacter >= START && endCharacter <= 'Z';
}
```

- We fix `contains_2_letters_per_line` by fixing the `toLine` method
 
```java
public class Diamond {

    public static final char START = 'A';

    private Diamond() {
    }

    public static Option<String> print(char endCharacter) {
        if (isValidCharacter(endCharacter)) {
            Seq<String> lines = generateDiamond(endCharacter);
            return Some(lines.mkString(System.lineSeparator()));
        } else {
            return Option.none();
        }
    }

    private static Seq<String> generateDiamond(char endCharacter) {
        Seq<String> halfDiamond = generateHalfDiamond(endCharacter);
        return halfDiamond.appendAll(
                halfDiamond.reverse()
                        .drop(1)
        );
    }

    private static Seq<String> generateHalfDiamond(char endCharacter) {
        return Stream.range(START, START + (endCharacter - START) + 1)
                .map(i -> (char) i.intValue())
                .map(c -> toLine(c, endCharacter));
    }

    private static String toLine(char character, char endCharacter) {
        var out = generateEmptyCharacters(endCharacter - character);
        var inner = character != START
                ? generateEmptyCharacters((character - START) * 2 - 1) + character
                : "";

        return out + character + inner + out;
    }

    private static String generateEmptyCharacters(int count) {
        return Stream.range(0, count + 1)
                .foldLeft("", (acc, c) -> acc + " ");
    }

    private static boolean isValidCharacter(char endCharacter) {
        return endCharacter >= START && endCharacter <= 'Z';
    }
}
```

- We fix the property `be_a_square` by fixing the `generateEmptyCharacters`

```java
private static String generateEmptyCharacters(int count) {
    return Stream.range(0, count)
            .foldLeft("", (acc, c) -> acc + " ");
}
```

🟢 All our properties are green again 🤩

🔵 Let's refactor our `Diamond` to extract some method and give business names

```java
private static Seq<String> generateHalfDiamond(char endCharacter) {
    return range(START, START + (endCharacter - START) + 1)
            .map(i -> (char) i.intValue())
            .map(c -> toLine(c, endCharacter));
}

private static String toLine(char character, char endCharacter) {
    var out = outer(character, endCharacter);
    return out + character + inner(character) + out;
}

private static String inner(char character) {
    return character != START
            ? generateEmptyCharacters((character - START) * 2 - 1) + character
            : "";
}

private static String outer(char character, char endCharacter) {
    return generateEmptyCharacters(endCharacter - character);
}

private static String generateEmptyCharacters(int count) {
    return range(0, count)
            .foldLeft("", (acc, c) -> acc + " ");
}
```

### Lines have a decreasing number of left spaces

```text
for all (validEndCharacter)
such that Lines have a decreasing number of left white spaces until end character 
```

🟢 Not that easy to create...

```java
@Test
void decreasing_number_of_left_white_spaces() {
    checkProperty("Lines have a decreasing number of left white spaces until end character",
            (diamond, endCharacter) -> {
                var linesUntilInputChar = diamond.take(endCharacter - 'A' + 1);
                var spaces = linesUntilInputChar
                        .map(line -> List.ofAll(line.chars().toArray())
                                .takeWhile(c -> (char) c.intValue() == ' ')
                        ).map(List::length);

                AtomicInteger expectedSpaceOnLine = new AtomicInteger(linesUntilInputChar.length());
                return Stream.range(0, linesUntilInputChar.length() - 1)
                        .forAll(i -> spaces.get(i) == expectedSpaceOnLine.decrementAndGet());
            });
}
```

🔵 We refactor the test to make it more clear what we do in it

```java
@Test
void decreasing_number_of_left_white_spaces() {
    checkProperty("Lines have a decreasing number of left white spaces until end character",
            (diamond, endCharacter) -> {
                var halfDiamond = halfDiamond(diamond, endCharacter);
                var spaces = countSpacesBeforeFirstLetterPerLine(halfDiamond);

                return areSpacesPerLineMatch(halfDiamond, spaces);
            });
}

private static Seq<String> halfDiamond(Seq<String> diamond, Character endCharacter) {
    return diamond.take(endCharacter - 'A' + 1);
}

private static Seq<Integer> countSpacesBeforeFirstLetterPerLine(Seq<String> halfDiamond) {
    return halfDiamond
            .map(line -> List.ofAll(line.chars().toArray())
                    .takeWhile(c -> (char) c.intValue() == EMPTY_CHARACTER)
            ).map(List::length);
}

private static boolean areSpacesPerLineMatch(Seq<String> halfDiamond, Seq<Integer> spaces) {
    AtomicInteger expectedSpaceOnLine = new AtomicInteger(halfDiamond.length());
    return Stream.range(0, halfDiamond.length() - 1)
            .forAll(i -> spaces.get(i) == expectedSpaceOnLine.decrementAndGet());
}
```

### Lines have a decreasing number of right spaces

🟢 As you may expect the property is green

```java
@Test
void decreasing_number_of_right_white_spaces() {
    checkProperty("Lines have a decreasing number of right white spaces until end character",
            (diamond, endCharacter) -> {
                var halfDiamond = halfDiamond(diamond, endCharacter);
                var spaces = countSpacesAfterLastLetterPerLine(halfDiamond);

                return areSpacesPerLineMatch(halfDiamond, spaces);
            });
}
```

🔵 We can remove duplications in the test

```java
private static Seq<Integer> countSpacesBeforeFirstLetterPerLine(Seq<String> halfDiamond) {
    return halfDiamond
            .map(line -> List.ofAll(line.chars().toArray())
                    .takeWhile(c -> (char) c.intValue() == EMPTY_CHARACTER)
            ).map(List::length);
}

private static Seq<Integer> countSpacesAfterLastLetterPerLine(Seq<String> halfDiamond) {
    return halfDiamond
            .map(line -> List.ofAll(line.chars().toArray())
                    .reverse()
                    .takeWhile(c -> (char) c.intValue() == EMPTY_CHARACTER)
            ).map(List::length);
}
```

- We create a new `method` to centralize this logic

```java
private static Seq<Integer> countSpacesBeforeFirstLetterPerLine(Seq<String> halfDiamond) {
    return countSpacesOnLine(
            halfDiamond,
            line -> line
    );
}

private static Seq<Integer> countSpacesAfterLastLetterPerLine(Seq<String> halfDiamond) {
    return countSpacesOnLine(
            halfDiamond,
            line -> new StringBuilder(line).reverse().toString()
    );
}

private static Seq<Integer> countSpacesOnLine(Seq<String> halfDiamond,
                                              Function1<String, String> mapLine) {
    return halfDiamond.map(line ->
            charList(mapLine.apply(line))
                    .takeWhile(c -> c == EMPTY_CHARACTER)
    ).map(List::length);
}
```

> All our properties are green 🤩. Are we confident enough?

### Add an `Approval` test

To increase our confidence we secure our implementation with a `Unit Test`.
We choose to use an `Approval` one.

```xml
<approvaltests.version>22.3.2</approvaltests.version>
<dependency>
    <groupId>com.approvaltests</groupId>
    <artifactId>approvaltests</artifactId>
    <version>${approvaltests.version}</version>
</dependency>
```

- We add this test

```java
@Test
void generate_a_diamond() {
    Approvals.verify(
            print('K').get()
    );
}
```

🔴 It fails because we need to approve the result

![Approve test](img/approve-content.png)

🟢 It seems pretty good

We approve the file, and we're done, for now 😉.

Here is how looks our `Diamond`

```java
public class Diamond {
    public static final char START = 'A';

    private Diamond() {
    }

    public static Option<String> print(char endCharacter) {
        return isValidCharacter(endCharacter)
                ? Some(fullDiamondSafely(endCharacter))
                : none();
    }

    private static String fullDiamondSafely(char endCharacter) {
        return concatLines(
                generateDiamond(endCharacter)
        );
    }

    private static Seq<String> generateDiamond(char endCharacter) {
        var halfDiamond = generateHalfDiamond(endCharacter);
        return halfDiamond.appendAll(
                halfDiamond.reverse().drop(1)
        );
    }

    private static Seq<String> generateHalfDiamond(char endCharacter) {
        return range(START, START + (endCharacter - START) + 1)
                .map(i -> (char) i.intValue())
                .map(c -> toLine(c, endCharacter));
    }

    private static String toLine(char character, char endCharacter) {
        var out = outer(character, endCharacter);
        return out + character + inner(character) + out;
    }

    private static String outer(char character, char endCharacter) {
        return generateEmptyCharacters(endCharacter - character);
    }

    private static String inner(char character) {
        return character != START
                ? generateEmptyCharacters(numberOfEmptyCharactersFor(character)) + character
                : "";
    }

    private static int numberOfEmptyCharactersFor(char character) {
        return (character - START) * 2 - 1;
    }

    private static String generateEmptyCharacters(int count) {
        return range(0, count)
                .foldLeft("", (acc, c) -> acc + " ");
    }

    private static boolean isValidCharacter(char endCharacter) {
        return endCharacter >= START && endCharacter <= 'Z';
    }

    private static String concatLines(Seq<String> lines) {
        return lines.mkString(System.lineSeparator());
    }
}
```