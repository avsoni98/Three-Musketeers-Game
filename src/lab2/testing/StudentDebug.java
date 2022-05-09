package lab2.testing;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import lab2.Board;
import lab2.Cell;
import lab2.Coordinate;
import lab2.Utils;
import lab2.Exceptions.InvalidMoveException;

public class StudentDebug {
//NOTE:
//Most of the testing was done in the Debug class to print out the expected results
	
	
      //Check there are 22 guards to start with
	  @Test
	    public void testGetGuardCells() {
		   Board board = new Board();
		   List<Cell> guards = board.getGuardCells();
		   int numGuards = guards.size();
		   int expectedGuards = 22;
		   assertEquals(expectedGuards, numGuards);
	    }
	  
	  //Check there are 3 Musketeers to start with
	  @Test
	    public void testGetMusketeerCells() {
		   Board board = new Board();
		   List<Cell> guards = board.getMusketeerCells();
		   int numMuske = guards.size();
		   int expectedMuske = 3;
		   assertEquals(expectedMuske, numMuske);
	    }

	  //Check if written coordinate is equivalent to number coordinate
	  @Test
	    public void testGetStringCell() {
		   Board board = new Board();
		   String tryCorString = "e1";
		   Cell toCell = null;
		try {
			toCell = board.getCell(Utils.parseUserMove(tryCorString.toUpperCase()));
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  Cell ExpectedtoCell = board.getCell(new Coordinate(0, 4));
		   assertEquals(ExpectedtoCell, toCell);
	    }
	  
	  //Check if written coordinate is equivalent to number coordinate
	  @Test
	    public void testGetDestinations() {
		   Board board = new Board();
		   String tryCorString = "c3";
		   Cell fromCell = null;
		try {
			fromCell = board.getCell(Utils.parseUserMove(tryCorString.toUpperCase()));
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  int numDestinations = board.getPossibleDestinations(fromCell).size();
		  int Expectednum = 4;
		   assertEquals(Expectednum, numDestinations);		
	    }
}
