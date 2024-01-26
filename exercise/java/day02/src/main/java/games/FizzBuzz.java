package games;

public class FizzBuzz {

    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int FIZZ = 3;
    public static final int BUZZ = 5;
    public static final int FIZZBUZZ = 15;

    private FizzBuzz() {
    }

    /*
     * On extrait le range dans une methode et on fait un early throw dans le cas ou le nombre n'est pas dans le range
     * Tous les autre input sont donc dans le range on peut donc mettre les condition en dessous
     * Extraire les division euclidienne dans une methode
     * A noter qu'un nombre divisible par 5 et 3 ne peut que être divisible pas 15
     * Créer des constante
     */
    public static String convert(Integer input) throws OutOfRangeException {
        if (isNotBetween(input)) {
            throw new OutOfRangeException();
        }
        if (canDivide(input, FIZZBUZZ)) {
            return "FizzBuzz";
        }
        if (canDivide(input, FIZZ)) {
            return "Fizz";
        }
        if (canDivide(input, BUZZ)) {
            return "Buzz";
        }
        return input.toString();
    }

    private static boolean canDivide(Integer input, Integer divisor) {
        return input % divisor == 0;
    }

    private static boolean isNotBetween(Integer input) {
        return input <= MIN || input > MAX;
    }
}
