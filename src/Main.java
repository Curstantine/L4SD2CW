import java.io.IOException;

public class Main {
    static String menuMessage = """
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
            """;


    public static void main(String[] args) throws UnsupportedOperationException {
        System.out.println("Welcome to the Plane Management system");
        System.out.println(menuMessage);

        final Seats seats = new Seats();
        boolean firstTime = true;

        while (true) {
            if (!firstTime) {
                System.out.println();
                if (!Prompter.promptConditional("Do you want to continue to the session?")) break;
                System.out.println(menuMessage);
            }

            final int option = Prompter.promptRangeInteger(0, 6, "Please select an option: ", "You need to select a valid number!");

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
                case 5:
                    printTicketsInfo(seats);
                    break;
                case 6:
                    searchTicket(seats);
                    break;
                case 0:
                    return;
                default:
                    throw new UnsupportedOperationException();
            }

            firstTime = false;
        }
    }

    static void buySeat(Seats seats) {
        Position position;

        // We want the input process to continue until the user choose an available seat
        while (true) {
            position = Position.fromUserInput();
            if (seats.isSeatAvailable(position)) break;
            System.out.println("This seat is already taken. Please try another one!");
        }

        try {
            final Person person = Person.fromUserInput();
            final Ticket ticket = seats.buySeat(position, person);
            ticket.save();
        } catch (IOException e) {
            System.out.printf("Failed to write ticket file with error: %s", e);
        }
    }

    static void cancelSeat(Seats seats) {
        Position position;

        // We want the input process to continue until the user choose an occupied seat
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
            if (!Prompter.promptConditional("Do you want to buy it?")) return;

            final Person person = Person.fromUserInput();
            seats.buySeat(firstAvailable, person);
        } catch (IllegalStateException e) {
            System.out.println("It seems that all the seats are taken :(");
        }
    }

    static void showSeatingPlan(Seats seats) {
        System.out.println(seats.toDisplayString());
    }

    static void printTicketsInfo(Seats seats) {
        System.out.println("\n**** Ticket sales information ****");

        Ticket[] tickets = seats.getTickets();

        if (tickets.length == 0) {
            System.out.println("None of the tickets were sold :(");
            return;
        }

        int total = 0;
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < tickets.length; i++) {
            Ticket ticket = tickets[i];

            total += ticket.getPrice();
            builder.append(String.format("%s. %s\n", i + 1, ticket.toDisplayString()));
        }


        System.out.println(builder);
        System.out.printf("With total sales resulting in %sLKR!\n", total);
    }

    static void searchTicket(Seats seats) {
        final Position select = Position.fromUserInput();

        if (seats.isSeatAvailable(select)) {
            System.out.println("This seat is available!");
            return;
        }


        final Ticket ticket = seats.getTicket(select);
        System.out.printf("This seats is already taken by %s", ticket.toDisplayString());

    }
}

