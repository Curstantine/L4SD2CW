import java.util.Set;

public class Position {
    public static final int ROW_LOWER_BOUND = 0;
    public static final int ROW_HIGHER_BOUND = 3;
    public static final String[] ROW_LETTERS = {"A", "B", "C", "D"};

    public static final int COL_LOWER_BOUND = 0;
    public static final int COL_HIGHER_BOUND = 13;
    public static final int COL_HIGHER_BOUND_ALT = 11;

    public int col;

    public int row;

    /**
     * Creates a position reference from {@link #col} and {@link #row}.
     * This class guarantees that the underlying coordinates are valid in the {@link Seats} class.
     *
     * @throws IndexOutOfBoundsException When either col or row are out-of-bound with the seating layout constraints.
     */
    public Position(int col, int row) throws IndexOutOfBoundsException {
        validatePosition(col, row);

        this.col = col;
        this.row = row;
    }

    static void validateRow(int row) throws IndexOutOfBoundsException {
        if (row < ROW_LOWER_BOUND || row > ROW_HIGHER_BOUND) throw new IndexOutOfBoundsException();
    }

    /**
     * Returns the max column index relative to the row passed.
     *
     * @throws IndexOutOfBoundsException when the row is out-of-bound
     */
    public static int getColumnUpperBound(int row) throws IndexOutOfBoundsException {
        validateRow(row);
        return (row == 0 || row == 3) ? COL_HIGHER_BOUND : COL_HIGHER_BOUND_ALT;
    }

    /**
     * @throws IndexOutOfBoundsException when either the row or the column is out of bound.
     */
    static void validatePosition(int col, int row) throws IndexOutOfBoundsException {
        validateRow(row);
        if (col < COL_LOWER_BOUND || col > getColumnUpperBound(row)) throw new IndexOutOfBoundsException();
    }

    /**
     * Returns the row letter of related row index.
     *
     * @throws IndexOutOfBoundsException When row is out-of-bounds from seat layout.
     * @see #getUnsafeRowLetter(int)
     */
    public static char getUnsafeRowLetter(int row) throws IndexOutOfBoundsException {
        return switch (row) {
            case 0 -> 'A';
            case 1 -> 'B';
            case 2 -> 'C';
            case 3 -> 'D';
            default -> throw new IndexOutOfBoundsException();
        };
    }

    /**
     * Returns the related row index to the row letter.
     * This is useful for internal references.
     */
    static int getRelatedRowIndex(char rowLetter) throws IndexOutOfBoundsException {
        return switch (rowLetter) {
            case 'a', 'A' -> 0;
            case 'b', 'B' -> 1;
            case 'c', 'C' -> 2;
            case 'd', 'D' -> 3;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    public static Position fromUserInput() {
        final String rowLettersHuman = String.join(", ", ROW_LETTERS);
        final String rowLetter = Prompter.promptValidString(
                Set.of(ROW_LETTERS),
                String.format("Insert a row letter (%s): ", rowLettersHuman),
                String.format("Please insert a valid row letter out of %s!", rowLettersHuman)
        );
        final int localRow = Position.getRelatedRowIndex(rowLetter.charAt(0));

        final int colHumanLowerBound = COL_LOWER_BOUND + 1;
        final int colHumanUpperBound = getColumnUpperBound(localRow) + 1;
        final int localCol = Prompter.promptRangeInteger(
                colHumanLowerBound,
                colHumanUpperBound,
                String.format("Insert a column number for the seat (min %s, max: %s): ", colHumanLowerBound, colHumanUpperBound),
                String.format("Please insert a valid number between %s and %s!", colHumanLowerBound, colHumanUpperBound)
        ) - 1;

        return new Position(localCol, localRow);
    }

    public int getPrice() {
        if (col <= 5) return 200;
        if (col <= 9) return 150;

        return 180;
    }

    /**
     * Safer variant of {@link #getUnsafeRowLetter(int)}
     */
    public char getRowLetter() {
        return getUnsafeRowLetter(row);
    }

    /**
     * Returns the display string formatted as "[ROW_LETTER][COL]"
     * <br />
     * E.g. A12, C2
     */
    public String toDisplayString() {
        return String.format("%s%s", getRowLetter(), col + 1);
    }


    @Override
    public String toString() {
        return String.format("Position { row: %s, col: %s }", row, col);
    }
}
