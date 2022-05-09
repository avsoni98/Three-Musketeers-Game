package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAgent extends Agent {

    public RandomAgent(Board board) {
        super(board);
    }

    /**
     * Gets a valid random move the RandomAgent can do.
     * @return a valid Move that the RandomAgent can perform on the Board
     */
    @Override
    public Move getMove() { // TODO
    	//RandomAgent uses a random int based on the size of list from getPossibleMoves method
    	//Random int returns a random move that is possible based on piece type (Musketeer or Guard)
    	
    	int rnd = new Random().nextInt(this.board.getPossibleMoves().size());
    	//Cell fromCell = this.board.getCell(getCoordinatesHelper(this.board.getPossibleCells()).get(rnd));
    	//Cell toCell = this.board.getCell(getCoordinatesHelper(this.board.getPossibleDestinations(fromCell)).get(rnd));
    	Move moves = this.board.getPossibleMoves().get(rnd);
    	Cell fromCell = moves.fromCell;
    	Cell toCell = moves.toCell;
    	
    	System.out.printf("[%s (Random Agent)] Moving piece " + fromCell.getCoordinate() + " to " + toCell.getCoordinate() + ".", board.getTurn().getType());
    	
    	return new Move(fromCell, toCell);
    }


///********************Helper methods*******************************************
/**
 * HELPER method to return the coordinates of all Musketeeers or Guards
 * Doesn't check if the Musketeers or Guards have a possible move, just returns their coordinates based on list inserted
 * @return List of Coordinates
 
private List<Coordinate> getCoordinatesHelper(List <Cell> cells) { // TODO
    
    List<Coordinate> availCoordinates = new ArrayList<>();
        
        for (int i=0; i < cells.size(); i++) {
            availCoordinates.add(cells.get(i).getCoordinate());
        }
       return availCoordinates;
    }
   */
}
