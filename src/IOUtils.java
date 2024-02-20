import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class IOUtils {
    public static int getUserInteger(int start, int end, String message, String invalidMessage) {
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

    public static String getUserString(Set<String> valid, String message, String invalidMessage) {
        String value;

        while (true) {
            System.out.print(message);
            final Scanner input = new Scanner(System.in);
            final String str = input.next();

            if (!valid.contains(str)) {
                System.out.println(invalidMessage);
                continue;
            }


            value = str;
            break;
        }

        return value;
    }

    public static boolean getYesNoPrompt(String message) {
        boolean value;

        while (true) {
            System.out.print(message + " (Y/N): ");

            try {
                final Scanner input = new Scanner(System.in);
                final String stringVal = input.next().toLowerCase();

                if (stringVal.equals("y") || stringVal.equals("yes")) {
                    value = true;
                    break;
                } else if (stringVal.equals("n") || stringVal.equals("no")) {
                    value = false;
                    break;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Allowed values are either Y(es) or N(o)");
            }
        }

        return value;
    }
}
