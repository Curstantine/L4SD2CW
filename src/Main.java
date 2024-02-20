public class Main {
    public static void main(String[] args) throws UnsupportedOperationException {
        System.out.println("Welcome to the Plane Management system");
        System.out.println("""
                *************************************************
                *                 Menu Options                  *
                *************************************************
                    1. Buy a seat
                    2. Cancel a seat
                    3. Find first available seat
                    4. Show seating plan
                    5. Print tickets information and total sales
                    6. Search ticket
                    0. Quit
                *************************************************
                """);

        final int option = IOUtils.getUserInteger(0, 6, "Please select an option: ", "You need to select a valid number!");
        final Seats seats = new Seats();

        switch (option) {
            case 1:
                buySeat(seats);
                break;
            case 2:
                cancelSeat(seats);
                break;
            case 3:
                findFirstAvailable(seats);
                break;
            case 4:
                showSeatingPlan(seats);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static void buySeat(Seats seats) {
        Position position;

        // We want the input process to continue till the inputs are valid, and the seats are empty.
        while (true) {
            position = Position.fromUserInput();
            if (seats.isSeatAvailable(position)) break;
            System.out.println("This seat is already taken. Please try another one!");
        }

        final Person person = Person.fromUserInput();
        seats.buySeat(position, person);
    }

    static void cancelSeat(Seats seats) {
        Position position;

        // We want the input process to continue till the inputs are valid, and the seats are empty.
        while (true) {
            position = Position.fromUserInput();
            if (!seats.isSeatAvailable(position)) break;
            System.out.println("This seat is already free. Please try another one!");
        }

        seats.cancelSeat(position);
    }

    static void findFirstAvailable(Seats seats) {
        try {
            final Position firstAvailable = seats.getFirstAvailable();

            System.out.printf("Seat at %s is available! ", firstAvailable.toDisplayString());
            if (!IOUtils.getYesNoPrompt("Do you want to buy it?")) return;

            final Person person = Person.fromUserInput();
            seats.buySeat(firstAvailable, person);
        } catch (IllegalStateException e) {
            System.out.println("It seems that all the seats are taken :(");
        }
    }

    static void showSeatingPlan(Seats seats) {
        System.out.println(seats.toDisplayString());
    }
}

