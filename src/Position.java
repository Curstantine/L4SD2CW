import java.util.Set;

public class Position {
    public static final int ROW_LOWER_BOUND = 0;
    public static final int ROW_HIGHER_BOUND = 3;
    public static final String[] ROW_LETTERS = {"a", "b", "c", "d"};

    public static final int COL_LOWER_BOUND = 0;
    public static final int COL_HIGHER_BOUND = 13;
    public static final int COL_HIGHER_BOUND_ALT = 12;

    public int col;
    public int row;

    public Position(int col, int row) {
        validatePosition(col, row);

        this.col = col;
        this.row = row;
    }

    static void validateRow(int row) throws IndexOutOfBoundsException {
        if (row < ROW_LOWER_BOUND || row > ROW_HIGHER_BOUND) throw new IndexOutOfBoundsException();
    }

    /**
     * Returns the max column size relative to the row passed.
     */
    public static int getColumnUpperBound(int row) throws IndexOutOfBoundsException {
        validateRow(row);
        return (row == 0 || row == 3) ? COL_HIGHER_BOUND : COL_HIGHER_BOUND_ALT;
    }

    static void validatePosition(int col, int row) throws IndexOutOfBoundsException {
        validateRow(row);
        if (col < COL_LOWER_BOUND || col > getColumnUpperBound(row)) throw new IndexOutOfBoundsException();
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

    public static Position getColRowFromInput() {
        final String rowLettersHuman = String.join(", ", ROW_LETTERS);
        final String rowLetter = IOUtils.getUserString(
                Set.of(ROW_LETTERS),
                String.format("Insert a row letter (%s): ", rowLettersHuman),
                String.format("Please insert a valid row letter out of %s!", rowLettersHuman)
        );
        final int localRow = Position.getRelatedRowIndex(rowLetter.charAt(0));

        final int colHumanLowerBound = COL_LOWER_BOUND + 1;
        final int colHumanUpperBound = getColumnUpperBound(localRow) + 1;
        final int localCol = IOUtils.getUserInteger(
                colHumanLowerBound,
                colHumanUpperBound,
                String.format("Insert a column number for the seat (min %s, max: %s): ", colHumanLowerBound, colHumanUpperBound),
                String.format("Please insert a valid number between %s and %s!", colHumanLowerBound, colHumanUpperBound)
        ) - 1;

        return new Position(localCol, localRow);
    }
}
