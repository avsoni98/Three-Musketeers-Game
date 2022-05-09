package lab2.testing;

import org.junit.*;

import lab2.Board;
import lab2.Cell;
import lab2.Coordinate;

public class BoardTest {

    private Board board;

    @Before
    public void setup() {
        this.board = new Board();
    }

    @Test
    public void testGetCell() {
        Cell cell = board.getCell(new Coordinate(0, 4));
        Assert.assertNotNull(cell.getPiece());
    }

}
