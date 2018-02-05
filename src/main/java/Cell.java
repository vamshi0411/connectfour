 /**
 * Generic class that is used by the Playing Stand to represent a cell in the stand.
 */
public class Cell {

    private final int column;
    private final int row;

    public Cell(int column, int row) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

}
