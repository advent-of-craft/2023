import games.FizzBuzz
import games.OutOfRangeException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class FizzBuzzTests : FunSpec({
    context("returns its numbers representation") {
        withData(
            ValidInput(1, "1"),
            ValidInput(67, "67"),
            ValidInput(82, "82"),
            ValidInput(3, "Fizz"),
            ValidInput(66, "Fizz"),
            ValidInput(99, "Fizz"),
            ValidInput(5, "Buzz"),
            ValidInput(50, "Buzz"),
            ValidInput(85, "Buzz"),
            ValidInput(15, "FizzBuzz"),
            ValidInput(30, "FizzBuzz"),
            ValidInput(45, "FizzBuzz")
        ) { (input, expectedResult) ->
            FizzBuzz.convert(input) shouldBe expectedResult
        }
    }

    context("fails for numbers out of range") {
        withData(0, -1, 101) { x ->
            shouldThrow<OutOfRangeException> {
                FizzBuzz.convert(x)
            }
        }
    }
})

data class ValidInput(val input: Int, val expectedResult: String)