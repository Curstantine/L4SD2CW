import java.util.Arrays;

/**
 * All the methods under this class are protected by {@link Position},
 * so none of the methods that digest a position doesn't throw {@link IndexOutOfBoundsException}.
 * <br /> <br />
 * This safety comes from how {@link Position} validates col and row at initialization time.
 */
public class Seats {
    private final boolean[][] seats = new boolean[4][];
    private Ticket[] tickets = {};

    public Seats() {
        seats[0] = new boolean[14];
        seats[1] = new boolean[12];
        seats[2] = new boolean[12];
        seats[3] = new boolean[14];
    }

    public boolean isSeatAvailable(Position pos) {
        return !seats[pos.row][pos.col];
    }

    /**
     * Returns the first available position in the seat layout.
     *
     * @throws IllegalStateException When there are no seats available.
     */
    public Position getFirstAvailable() throws IllegalStateException {
        for (int rowIdx = 0; rowIdx < Position.ROW_HIGHER_BOUND + 1; rowIdx++) {
            final boolean[] row = seats[rowIdx];

            for (int colIdx = 0; colIdx < Position.getColumnUpperBound(rowIdx) + 1; colIdx++) {
                final boolean colTaken = row[colIdx];
                if (!colTaken) return new Position(colIdx, rowIdx);
            }
        }

        throw new IllegalStateException();
    }

    /**
     * @throws IllegalStateException If the seat of row and col is already taken.
     * @see #isSeatAvailable(Position)
     */
    public void buySeat(Position position, Person person) throws IllegalStateException {
        if (seats[position.row][position.col]) throw new IllegalStateException("Seat is already occupied");

        seats[position.row][position.col] = true;
        final Ticket ticket = new Ticket(position, person);

        // We are not allowed to use LinkedLists or ArrayLists :(
        // What we do is essentially creating a new array with the same content, but with a size of + 1,
        // so we can replace the last item in the array with our new ticket.
        tickets = Arrays.copyOf(tickets, tickets.length + 1);
        tickets[tickets.length - 1] = ticket;
    }

    /**
     * @throws IllegalStateException If the seat of row and col is free.
     * @see #isSeatAvailable(Position)
     */
    public void cancelSeat(Position pos) throws IllegalStateException {
        if (!seats[pos.row][pos.col]) throw new IllegalStateException("Seat is already free");

        seats[pos.row][pos.col] = false;
        tickets = (Ticket[]) Arrays.stream(tickets).filter(x -> !x.position.equals(pos)).toArray();
    }

    public String toDisplayString() {
        final StringBuilder builder = new StringBuilder();
        builder.insert(0, "    ");

        for (int colIdx = 1; colIdx <= Position.COL_HIGHER_BOUND + 1; colIdx++) {
            builder.append(String.format("  %s", colIdx));
            builder.append(" ".repeat(colIdx > 9 ? 2 : 3));
        }

        builder.append("\n");

        for (int rowIdx = 0; rowIdx < seats.length; rowIdx++) {
            final boolean[] cols = seats[rowIdx];
            final StringBuilder rowBuilder = new StringBuilder();

            final char rowLetter = Position.getUnsafeRowLetter(rowIdx);
            rowBuilder.append(String.format("%s  |", rowLetter));

            for (boolean isSlotTaken : cols) {
                rowBuilder.append(String.format("  %s  |", isSlotTaken ? 'X' : 'O'));
            }

            if (cols.length == 12) {
                rowBuilder.append("     |".repeat(2));
            }

            builder.append(rowBuilder.append("\n"));
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        final String aSeats = Arrays.toString(seats[0]);
        final String bSeats = Arrays.toString(seats[1]);
        final String cSeats = Arrays.toString(seats[2]);
        final String dSeats = Arrays.toString(seats[3]);


        return String.format("Seats {\n\ta: %s,\n\tb: %s,\n\tc: %s,\n\td: %s\n}", aSeats, bSeats, cSeats, dSeats);
    }
}
