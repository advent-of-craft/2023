package diamond

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

object Diamond {
    private const val START = 'A'

    fun Char.toDiamond(): Option<String> = print(this)

    private fun print(endCharacter: Char): Option<String> =
        if (isValidCharacter(endCharacter)) Some(fullDiamondSafely(endCharacter)) else None

    private fun fullDiamondSafely(endCharacter: Char): String =
        concatLines(
            generateDiamond(endCharacter)
        )

    private fun generateDiamond(endCharacter: Char): List<String> =
        generateHalfDiamond(endCharacter)
            .let { halfDiamond ->
                return halfDiamond + halfDiamond.reversed().drop(1)
            }

    private fun generateHalfDiamond(endCharacter: Char): List<String> =
        (START.code..START.code + (endCharacter.code - START.code))
            .map { it.toChar() }
            .map { c -> toLine(c, endCharacter) }

    private fun toLine(character: Char, endCharacter: Char): String =
        outer(character, endCharacter)
            .let { out ->
                return out + character + inner(character) + out
            }

    private fun outer(character: Char, endCharacter: Char): String =
        generateEmptyCharacters(endCharacter.code - character.code)

    private fun inner(character: Char): String =
        if (character != START) generateEmptyCharacters(numberOfEmptyCharactersFor(character)) + character else ""

    private fun numberOfEmptyCharactersFor(character: Char): Int = (character.code - START.code) * 2 - 1

    private fun generateEmptyCharacters(count: Int): String = (0..<count)
        .fold("") { acc, _ -> "$acc " }

    private fun isValidCharacter(endCharacter: Char): Boolean = endCharacter in START..'Z'

    private fun concatLines(lines: List<String>): String = lines.joinToString(System.lineSeparator())
}
