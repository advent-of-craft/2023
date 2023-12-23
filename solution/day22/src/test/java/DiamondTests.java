import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.test.Arbitrary;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static diamond.Diamond.print;
import static io.vavr.test.Gen.choose;
import static io.vavr.test.Property.def;
import static java.lang.System.lineSeparator;

class DiamondTests {
    public static final char EMPTY_CHARACTER = ' ';
    private final Arbitrary<Character> upperLetterGenerator = choose('A', 'Z').arbitrary();

    @Test
    void be_horizontally_symmetric() {
        checkProperty("Horizontally symmetric for valid end characters",
                (diamond, c) -> diamond.equals(diamond.reverse())
        );
    }

    @Test
    void be_a_square() {
        checkProperty("A square for valid end characters",
                (diamond, endCharacter) -> diamond.forAll(
                        line -> line.length() == diamond.length()
                )
        );
    }

    @Test
    void contains_2_letters_per_line() {
        checkProperty("Contains 2 identical letters except first and last",
                (diamond, endCharacter) -> diamond
                        .drop(1)
                        .dropRight(1)
                        .map(line -> line.replaceAll(" ", ""))
                        .forAll(x -> x.length() == 2)
        );
    }

    @Test
    void decreasing_number_of_left_white_spaces() {
        checkProperty("Lines have a decreasing number of left white spaces until end character",
                (diamond, endCharacter) -> {
                    var halfDiamond = halfDiamond(diamond, endCharacter);
                    var spaces = countSpacesBeforeFirstLetterPerLine(halfDiamond);

                    return areSpacesPerLineMatch(halfDiamond, spaces);
                });
    }

    @Test
    void decreasing_number_of_right_white_spaces() {
        checkProperty("Lines have a decreasing number of right white spaces until end character",
                (diamond, endCharacter) -> {
                    var halfDiamond = halfDiamond(diamond, endCharacter);
                    var spaces = countSpacesAfterLastLetterPerLine(halfDiamond);

                    return areSpacesPerLineMatch(halfDiamond, spaces);
                });
    }

    private static Seq<String> halfDiamond(Seq<String> diamond, Character endCharacter) {
        return diamond.take(endCharacter - 'A' + 1);
    }

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

    private static List<Character> charList(String line) {
        return List.ofAll(line.chars().toArray())
                .map(c -> (char) c.intValue());
    }

    private static boolean areSpacesPerLineMatch(Seq<String> halfDiamond, Seq<Integer> spaces) {
        AtomicInteger expectedSpaceOnLine = new AtomicInteger(halfDiamond.length());
        return Stream.range(0, halfDiamond.length() - 1)
                .forAll(i -> spaces.get(i) == expectedSpaceOnLine.decrementAndGet());
    }

    private void checkProperty(String name,
                               Function2<Seq<String>, Character, Boolean> property) {
        def(name).forAll(upperLetterGenerator)
                .suchThat(endCharacter ->
                        property.apply(
                                List.of(print(endCharacter)
                                        .get()
                                        .split(lineSeparator())
                                ), endCharacter))
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

    @Nested
    class Approval {
        @Test
        void generate_a_diamond() {
            Approvals.verify(
                    print('K').get()
            );
        }
    }
}
