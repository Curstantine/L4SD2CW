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
            default:
                throw new UnsupportedOperationException();
        }
    }

    static void buySeat(Seats seats) {
        Position position;

        // We want the input process to continue till the inputs are valid, and the seats are empty.
        while (true) {
            position = Position.getColRowFromInput();
            if (seats.isSeatAvailable(position)) break;
            System.out.println("This seat is already taken. Please try another one!");
        }

        seats.buySeat(position);
    }

    static void cancelSeat(Seats seats) {
        Position position;

        // We want the input process to continue till the inputs are valid, and the seats are empty.
        while (true) {
            position = Position.getColRowFromInput();
            if (!seats.isSeatAvailable(position)) break;
            System.out.println("This seat is already free. Please try another one!");
        }

        seats.cancelSeat(position);
    }

    static void findFirstAvailable(Seats seats) {
        final Position firstAvailable = seats.getFirstAvailable();

        System.out.printf("Seat at %s%s is available! ", firstAvailable.getRowLetter(), firstAvailable.col);
        if (!IOUtils.getYesNoPrompt("Do you want to buy it?")) return;

        seats.buySeat(firstAvailable);
    }
}

