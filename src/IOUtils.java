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

    public static boolean getYesNoPrompt(String message, String invalidMessage) {
        boolean value;

        while (true) {
            System.out.print(message);

            try {
                final Scanner input = new Scanner(System.in);
                value = input.nextBoolean();

                break;
            } catch (InputMismatchException e) {
                System.out.println(invalidMessage);
                continue;
            }
        }

        return value;
    }
}
