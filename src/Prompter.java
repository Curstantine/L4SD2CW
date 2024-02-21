import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Prompter {

    /**
     * Prompts the user to insert a valid integer within the start and end range.
     */
    public static int promptRangeInteger(int start, int end, String message, String invalidMessage) {
        int value;

        while (true) {
            System.out.print(message);

            try {
                final Scanner input = new Scanner(System.in);
                final int opt = input.nextInt();
                if (opt < start || opt > end) throw new InputMismatchException();

                value = opt;
                break;
            } catch (InputMismatchException e) {
                System.out.println(invalidMessage);
            }
        }

        return value;
    }

    /**
     * Prompts the user for a string.
     *
     * @see #promptValidString(Set, String, String)
     */
    public static String promptString(String message) {
        System.out.print(message);
        final Scanner input = new Scanner(System.in);
        return input.next();
    }


    /**
     * Prompts the user for a string input defined in the valid set.
     */
    public static String promptValidString(Set<String> valid, String message, String invalidMessage) {
        String value;

        while (true) {
            System.out.print(message);

            final Scanner input = new Scanner(System.in);
            final String str = input.next();
            if (valid.contains(str)) {
                value = str;
                break;
            }

            System.out.println(invalidMessage);
        }

        return value;
    }

    /**
     * Prompts the user to input a "yes" "no" condition.
     */
    public static boolean promptConditional(String message) {
        boolean value;

        while (true) {
            System.out.print(message + " (Y/N): ");

            try {
                final Scanner input = new Scanner(System.in);
                final String stringVal = input.next().toLowerCase();

                value = switch (stringVal) {
                    case "y", "yes" -> true;
                    case "n", "no" -> false;
                    default -> throw new InputMismatchException();
                };

                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Allowed values are either Y(es) or N(o)");
            }
        }

        return value;
    }
}
