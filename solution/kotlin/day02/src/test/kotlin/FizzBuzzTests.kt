import games.FizzBuzz
import games.OutOfRangeException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class FizzBuzzTests : FunSpec({
    test("returns the given number for 1") {
        FizzBuzz.convert(1) shouldBe "1"
    }

    test("returns the given number for 67") {
        FizzBuzz.convert(67) shouldBe "67"
    }

    test("returns the given number for 82") {
        FizzBuzz.convert(82) shouldBe "82"
    }

    test("returns Fizz for 3") {
        FizzBuzz.convert(3) shouldBe "Fizz"
    }

    test("returns Fizz for 66") {
        FizzBuzz.convert(66) shouldBe "Fizz"
    }

    test("returns Fizz for 99") {
        FizzBuzz.convert(99) shouldBe "Fizz"
    }

    test("returns Fizz for 5") {
        FizzBuzz.convert(5) shouldBe "Buzz"
    }

    test("returns Fizz for 50") {
        FizzBuzz.convert(50) shouldBe "Buzz"
    }

    test("returns Fizz for 85") {
        FizzBuzz.convert(85) shouldBe "Buzz"
    }

    test("returns Fizz for 15") {
        FizzBuzz.convert(15) shouldBe "FizzBuzz"
    }

    test("returns Fizz for 30") {
        FizzBuzz.convert(30) shouldBe "FizzBuzz"
    }

    test("returns Fizz for 45") {
        FizzBuzz.convert(45) shouldBe "FizzBuzz"
    }

    test("throw an exception for 0") {
        shouldThrow<OutOfRangeException> {
            FizzBuzz.convert(0)
        }
    }

    test("throw an exception for 101") {
        shouldThrow<OutOfRangeException> {
            FizzBuzz.convert(101)
        }
    }

    test("throw an exception for -1") {
        shouldThrow<OutOfRangeException> {
            FizzBuzz.convert(-1)
        }
    }
})