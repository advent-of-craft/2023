package games

object FizzBuzz {
    fun convert(input: Int): String {
        return if (input > 0) {
            if (input <= 100) {
                if (input % 3 == 0 && input % 5 == 0) {
                    return "FizzBuzz"
                }
                if (input % 3 == 0) {
                    return "Fizz"
                }
                if (input % 5 == 0) {
                    "Buzz"
                } else input.toString()
            } else {
                throw OutOfRangeException()
            }
        } else {
            throw OutOfRangeException()
        }
    }
}

