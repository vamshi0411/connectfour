import junit.framework.TestCase;

public class CellTest extends TestCase {

    public void testConstructor() {
        Cell newCell = new Cell(10, 5);
        assertEquals(10, newCell.getColumn());
        assertEquals(5, newCell.getRow());
    }
}
