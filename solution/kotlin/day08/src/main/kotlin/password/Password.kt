package password

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.None
import arrow.core.Option
import arrow.core.Some

class Password private constructor(private val value: String) {
    fun value(): String {
        return value
    }

    companion object {
        private val rules: List<(String) -> Option<ParsingError>> = listOf(
            { input -> matches(input, ".{8,}", "Too short") },
            { input -> matches(input, ".*[A-Z].*", "No capital letter") },
            { input -> matches(input, ".*[a-z].*", "No lower letter") },
            { input -> matches(input, ".*[0-9].*", "No number") },
            { input -> matches(input, ".*[.*#@$%&].*", "No special character") },
            { input -> matches(input, "[a-zA-Z0-9.*#@$%&]+", "Invalid character") }
        )

        fun parse(input: String): Either<ParsingError, Password> = rules.map { f -> f(input) }
            .find { r -> r.isSome() }
            .let { toEither(it, input) }

        private fun matches(
            input: String,
            regex: String,
            reason: String
        ): Option<ParsingError> = when (input.matches(regex.toRegex())) {
            true -> None
            else -> Some(ParsingError.from(reason))
        }

        private fun toEither(it: Option<ParsingError>?, input: String) = when (it) {
            is Some -> Left(it.value)
            else -> Right(Password(input))
        }
    }
}