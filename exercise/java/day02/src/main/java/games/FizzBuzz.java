package games;

public class FizzBuzz {
    private FizzBuzz() {
    }
    
    public static String convert(Integer input) throws OutOfRangeException {
        if (isBetween(input, 0, 100) {
            if (input % 3 == 0 && input % 5 == 0) {
                return "FizzBuzz";
            }
            if (input % 3 == 0) {
                return "Fizz";
            }
            if (input % 5 == 0) {
                return "Buzz";
            }
            return input.toString();
        } else {
            throw new OutOfRangeException();
        }
    }

    private static boolean isBetween(Integer input, Integer min, Integer max) {
        return input >= min && input <= max;
    }
}
