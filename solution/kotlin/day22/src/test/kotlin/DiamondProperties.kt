import diamond.Diamond.toDiamond
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.PropertyContext
import io.kotest.property.arbitrary.char
import io.kotest.property.arbitrary.filter
import io.kotest.property.forAll
import java.lang.Character.isLetter
import java.lang.System.lineSeparator

class DiamondProperties : StringSpec({
    "horizontally symmetric for valid end characters" {
        checkProperty { diamond, _ -> diamond == diamond.reversed() }
    }

    "is a square for valid end characters" {
        checkProperty { diamond, _ -> diamond.all { line -> line.length == diamond.size } }
    }

    "contains 2 identical letters per line except first and last" {
        checkProperty { diamond, _ ->
            diamond
                .drop(1)
                .dropLast(1)
                .map { it.replace(" ", "") }
                .all { it.length == 2 && it[0] == it[1] }
        }
    }

    "lines have a decreasing number of left white spaces until end character" {
        checkProperty { diamond, endCharacter ->
            val halfDiamond = halfDiamond(diamond, endCharacter)
            val spaces = countSpacesBeforeFirstLetterPerLine(halfDiamond)

            areSpacesPerLineMatch(halfDiamond, spaces)
        }
    }

    "fail for invalid end character" {
        forAll(notALetter) { invalidCharacter ->
            invalidCharacter.toDiamond().isNone()
        }
    }
})

private const val EMPTY_CHARACTER = ' '
private val notALetter = Arb.char(' '..'~').filter { !isLetter(it) }
private val upperLetter = Arb.char('A'..'Z')

suspend fun checkProperty(property: (diamond: List<String>, endCharacter: Char) -> Boolean): PropertyContext =
    forAll(upperLetter) { endCharacter ->
        endCharacter.toDiamond()
            .map { it.split(lineSeparator()) }
            .filter { property(it, endCharacter) }
            .isSome()
    }

private fun halfDiamond(diamond: List<String>, endCharacter: Char): List<String> = diamond.take(endCharacter - 'A' + 1)

private fun countSpacesOnLine(halfDiamond: List<String>, mapLine: (String) -> String): List<Int> =
    halfDiamond.map { mapLine(it).takeWhile { c -> c == EMPTY_CHARACTER }.length }

private fun countSpacesBeforeFirstLetterPerLine(halfDiamond: List<String>): List<Int> =
    countSpacesOnLine(halfDiamond) { line -> line }

private fun countSpacesAfterLastLetterPerLine(halfDiamond: List<String>): List<Int> =
    countSpacesOnLine(halfDiamond) { line -> line.reversed() }

private fun areSpacesPerLineMatch(halfDiamond: List<String>, spaces: List<Int>): Boolean {
    var expectedSpaceOnLine = halfDiamond.size
    return halfDiamond.indices
        .all { i ->
            expectedSpaceOnLine -= 1
            return spaces[i] == expectedSpaceOnLine
        }
}