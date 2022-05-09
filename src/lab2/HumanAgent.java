package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lab2.Piece.Type;
import lab2.Exceptions.InvalidMoveException;

public class HumanAgent extends Agent {
    
    private final Scanner scanner = new Scanner(System.in);


    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() { // TODO
    	//Asks for user input. If user input is not what is expected per the regex, ask again. 
    	//If a cell input by user is not in the list of possible cells, ask again.
    	//If a possible cell input by user is not in the list possible destinations (given the possible cell) then ask again. 
        String fromString = "";
        String toString = "";
        System.out.printf("[%s] Possible pieces are: " + getCoordinatesHelper(this.board.getPossibleCells()) + ". Enter the piece you want to move: ", board.getTurn().getType()); 
        while (!scanner.hasNext("[A-Ea-e][1-5]")) {
             System.out.print(scanner.next() + " Is an invalid option. \n ");
             System.out.printf("[%s] Possible pieces are: " + getCoordinatesHelper(this.board.getPossibleCells()) + ". Enter the piece you want to move: ", board.getTurn().getType()); 
        }
         fromString = scanner.next().toUpperCase();
         Cell fromcell = null;
        try {
            fromcell = board.getCell(Utils.parseUserMove(fromString));
          
            while(!getCoordinatesHelper(this.board.getPossibleCells()).toString().contains(fromcell.getCoordinate().toString())) {
                System.out.print(fromString.toUpperCase() + " Is an invalid option. \n");
                System.out.printf("[%s] Possible pieces are: " + getCoordinatesHelper(this.board.getPossibleCells()) + ". Enter the piece you want to move: ", board.getTurn().getType()); 
                 fromString = scanner.next();
                 fromcell = this.board.getCell(Utils.parseUserMove(fromString.toUpperCase()));
            }
          System.out.printf("[%s] Possible destinations are: " + getCoordinatesHelper(this.board.getPossibleDestinations(fromcell)) + ". Enter where you want to move: ", board.getTurn().getType()); 
        } catch (InvalidMoveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        Cell tocell = null;
         try {
             while (!scanner.hasNext("[A-Ea-e][1-5]")) {
                 System.out.print(scanner.next().toUpperCase() + " Is an invalid option. \n ");
                 System.out.printf("[%s] Possible destinations are: " + getCoordinatesHelper(this.board.getPossibleDestinations(fromcell)) + ". Enter where you want to move: ", board.getTurn().getType());
            }
            toString = scanner.next().toUpperCase();
            tocell = this.board.getCell(Utils.parseUserMove(toString));
            
            while(!getCoordinatesHelper(this.board.getPossibleDestinations(fromcell)).toString().contains(tocell.getCoordinate().toString())) {
                System.out.print(toString.toUpperCase() + " is an invalid destination. \n");
                System.out.printf("[%s] Possible destinations are: " + getCoordinatesHelper(this.board.getPossibleDestinations(fromcell)) + ". Enter where you want to move: ", board.getTurn().getType());
                 toString = scanner.next();
                 tocell = this.board.getCell(Utils.parseUserMove(toString.toUpperCase()));
            }
        } catch (InvalidMoveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
         return new Move(fromcell, tocell);
    }
    
  ///********************Helper methods*******************************************
    /**
     * HELPER method to return the coordinates of all Musketeeers or Guards
     * Doesn't check if the Musketeers or Guards have a possible move, just returns their coordinates
     * @return List of Coordinates
     */
    private List<Coordinate> getCoordinatesHelper(List <Cell> cells) { // TODO
        
        List<Coordinate> availCoordinates = new ArrayList<>();
            
            for (int i=0; i < cells.size(); i++) {
                availCoordinates.add(cells.get(i).getCoordinate());
            }
           return availCoordinates;
        }
   
}