import java.util.Arrays;

public class Seats {


    private final boolean[][] seats = new boolean[4][];

    public Seats() {
        seats[0] = new boolean[14];
        seats[1] = new boolean[12];
        seats[2] = new boolean[12];
        seats[3] = new boolean[14];
    }


    public int getPrice(Position pos) throws IndexOutOfBoundsException {
        // validateColRow already takes care of anything out of bounds, so we can safely assume indexes.
        if (pos.col <= 5) return 200;
        if (pos.col <= 9) return 150;

        return 180;
    }

    public boolean isSeatAvailable(Position pos) throws IndexOutOfBoundsException {
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
     * @throws IndexOutOfBoundsException If the col or row is out-of-bound.
     * @throws IllegalStateException     If the seat of row and col is already taken.
     * @see #isSeatAvailable(Position)
     */
    public void buySeat(Position pos) throws IndexOutOfBoundsException, IllegalStateException {
        if (seats[pos.row][pos.col]) throw new IllegalStateException("Seat is already bought");

        seats[pos.row][pos.col] = true;
    }

    /**
     * @throws IndexOutOfBoundsException If the col or row is out-of-bound.
     * @throws IllegalStateException     If the seat of row and col is free.
     * @see #isSeatAvailable(Position)
     */
    public void cancelSeat(Position pos) throws IndexOutOfBoundsException, IllegalStateException {
        if (!seats[pos.row][pos.col]) throw new IllegalStateException("Seat is free");

        seats[pos.row][pos.col] = true;
    }


    @Override
    public String toString() {
        final String aSeats = Arrays.toString(seats[0]);
        final String bSeats = Arrays.toString(seats[1]);
        final String cSeats = Arrays.toString(seats[2]);
        final String dSeats = Arrays.toString(seats[3]);


        return String.format("seat.Seats {\n\ta: %s,\n\tb: %s,\n\tc: %s,\n\td: %s\n}", aSeats, bSeats, cSeats, dSeats);
    }
}
