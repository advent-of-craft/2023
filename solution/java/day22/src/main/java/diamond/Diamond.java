package diamond;

import io.vavr.collection.Seq;
import io.vavr.control.Option;

import static io.vavr.API.Some;
import static io.vavr.collection.Stream.range;
import static io.vavr.control.Option.none;

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
