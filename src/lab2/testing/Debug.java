package lab2.testing;

import java.util.List;
import java.util.Random;

import lab2.Board;
import lab2.Cell;
import lab2.Coordinate;
import lab2.HumanAgent;
import lab2.Move;
import lab2.Piece;
import lab2.Piece.Type;
import lab2.ThreeMusketeers;
import lab2.Utils;

public class Debug {
	
	//set to private to not interfere with TA testing
	private static void main(String[] args) throws Exception {
	
	   Board board = new Board();
	   HumanAgent humanAgent = new HumanAgent(board);
	   ThreeMusketeers tMusketeers = new ThreeMusketeers();
	   
	   //Check if this coordinate has a guard cell
	   Coordinate coordinates = new Coordinate(4, 2);
	   String stringCoordinates = "e1";
       Cell fromcell = board.getCell(coordinates); //check if it gives X or O
	   System.out.print("Test#1 if coordinates has a guard cell: " + fromcell.getPiece().getType().toString().equals("GUARD") + "\n");

	   
	   Cell toCell = board.getCell(Utils.parseUserMove(stringCoordinates.toUpperCase()));
	   List<Cell> give = board.getPossibleDestinations(toCell);

	   
	   List<Cell> musk = board.getPossibleCells();
	   System.out.print("Test#2 to get Musketeer cells: " + musk + "\n");
	   
	   
	  //Get a possible Musketeer move
	   int rnd = new Random().nextInt(board.getPossibleMoves().size());
	   Move moves = board.getPossibleMoves().get(rnd);
	   System.out.print("Test#3 to get a random possible Musketeer move: " + moves + "\n");
	   
	   //Check destinations for a musketeer cell at position A5. 
	   //The getPossibleDestination method can be tested in-game because it depends on the turn.
	   String stringCoordinates2 = "a5";
	   Cell cell1 = board.getCell(Utils.parseUserMove(stringCoordinates2.toUpperCase()));
	   List<Cell> muskeDestin = board.getPossibleDestinations(cell1);
	   System.out.print("Test#4 if there are 2 guard destinations for corner Musketeer A5: " + muskeDestin + "\n");

	   //At the start of the game there should be 0 destinations for the guard cells to move to. 
	   String stringCoordinates3 = "c1";
	   Cell cell2 = board.getCell(Utils.parseUserMove(stringCoordinates3.toUpperCase()));
	   List<Cell> guardDestin = board.getPossibleDestinations(cell2);
	   System.out.print("Test#5 if there are 0 destinations for any guard cell at the start of game: " + guardDestin + "\n");
	   
	   //Check if a move by human input is valid:
	   System.out.print("Test#6 if human input is valid: " + board.isValidMove(humanAgent.getMove()));
	   
	   
	  //if (!humanAgent.getCoordinatesHelper(board.getPossibleCells()).toString().contains(fromcell.getCoordinate().toString())) {
		   
		//	System.out.print("Cell isn't in here");

	  // }
	  // else {
			//System.out.print("Cell IS in here!");

	  // }
		
	  // System.out.print(fromcell.getCoordinate());
	   //String cell1 = coordinate.toString();
	   //String cell2 = board.board[2][2].getCoordinate().toString();
	   //Cell cell2 = board.getCell(coordinates); //fromcell - need to get coordinates!
	   //String fromcellString = fromcell.getCoordinate().toString();

	   //Change getCoordiante in Cell class back to protected after testing! 

	   //System.out.print(cell1 + " " + cell2);
	   //System.out.print(cell1.equals(cell2));
		//System.out.print(cell0);


	}


	

	
}
