package lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

import org.hamcrest.core.IsInstanceOf;

import lab2.Piece.Type;

public class Board {
    public int size = 5;
    //Stack<Move> S = new ArrayStack<>( );

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;

    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Musketeer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }


	/**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) { // TODO
        return this.board[coordinate.row][coordinate.col];
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }

    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() { // TODO
    //Get all the Musketeer cells and add them to the list
    List<Cell> availMusketeers = new ArrayList<>();
    	for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].toString().equals("X")) {
                	availMusketeers.add(getCell(new Coordinate(i, j)));
				}
            }
        }
       return availMusketeers;
    }

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() { // TODO
        //Get all the Guard cells and add them to the list
        List<Cell> availGuards = new ArrayList<>();
    	for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].toString().equals("O")) {
                	availGuards.add(getCell(new Coordinate(i, j)));
				}
            }
        }
        	return availGuards;
    }

    /**
     * Executes the given move on the board and changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) { // TODO
    //Check if the move for fromCell to destination toCell is valid and then perform it
    //Switch the turns
    		if (isValidMove(new Move(move.fromCell,move.toCell))) {
    			this.getCell(move.toCell.getCoordinate()).setPiece(getCell(move.fromCell.getCoordinate()).getPiece());
    			this.getCell(move.fromCell.getCoordinate()).removePiece();
    		}
    		if (this.turn == Type.MUSKETEER) {
                this.turn = Type.GUARD;
    		}
    		else {
                this.turn = Type.MUSKETEER;
   			}
		}
    	
    

    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) { // TODO
    //Undo the move by 'popping' the top (0) element of the stack (moves list) and taking the fromCell and toCells stored
    //Stored move: (from,to) is (X,O). We want to get the stored fromCell or toCell coordinates and set the piece to what we
    //popped from the top of the moves 'stack'.
    //Switch the turns
       this.getCell(move.fromCell.getCoordinate()).setPiece(move.fromCell.getPiece());
       this.getCell(move.toCell.getCoordinate()).setPiece(move.toCell.getPiece());
        if (this.turn == Type.MUSKETEER) {
            this.turn = Type.GUARD;
			}
		else {
            this.turn = Type.MUSKETEER;
			}    
        }

    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) { // TODO
    //If the fromCell has a piece (not null), check if it is possible
    //to go to the toCell depending on if GUARD or MUSKETEER
    	boolean result = false;
    		if (move.fromCell.hasPiece()) {
			result = move.fromCell.getPiece().canMoveOnto(move.toCell);
    		}
        return result;
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return      Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() { // TODO
    	//PLEASE NOTE******
    	//This method depends on the turn. 
    	//It is best testing in-game unless there is a way to manipulate the turn type (Guard or Musketeer) when testing.
    	//In testing, if you input a guard cell coordinate it will not know what the turn is and therefore treat it like a musketeer.
    	List<Cell> availGuards = new ArrayList<>();
        List<Cell> availMusket = new ArrayList<>();
        List<Cell> result = new ArrayList<>();
       
    	if (this.turn.equals(Type.MUSKETEER)) {
    	//If a MUSKETEER cells has a guard 'O' beside it (up, down, left, right) depending on where it is on the board
    	//Then add those MUSKETEER cell to the list of pieces that can be moved on MUSKETEER's turn
    		for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {	
                    if (board[i][j].toString().equals("X")) { 	
                    	try {   		
                    		if ((i==0 && j == 0) && (board[i][j+1].toString().equals("O") || board[i+1][j].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==0 && j == 4) && (board[i][j-1].toString().equals("O") || board[i+1][j].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 0) && (board[i-1][j].toString().equals("O") || board[i][j+1].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 4) && (board[i-1][j].toString().equals("O") || board[i][j-1].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if (i==0 && (board[i+1][j].toString().equals("O") || board[i][j-1].toString().equals("O")|| board[i][j+1].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//can't go left in column A
                    		else if (i==4 && (board[i-1][j].toString().equals("O") || board[i][j-1].toString().equals("O")|| board[i][j+1].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//can't go right in column E
                    		else if (j==0 && (board[i-1][j].toString().equals("O") || board[i][j+1].toString().equals("O")|| board[i+1][j].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//can't go up in row 1
                    		else if (j==4 && (board[i-1][j].toString().equals("O") || board[i][j-1].toString().equals("O")|| board[i+1][j].toString().equals("O"))) {
                            	availMusket.add(getCell(new Coordinate(i, j)));
                    		}//can't go down in row 5
                    		else if (board[i][j-1].toString().equals("O") || board[i][j+1].toString().equals("O") || board[i-1][j].toString().equals("O") || board[i+1][j].toString().equals("O")) {
                             	availMusket.add(getCell(new Coordinate(i, j)));
            				}//every other case where i is not A or E, and j is not 1(0) or 5(4)
						} 
                    	catch (Exception e) {
							// TODO: handle exception
						}
                      //QUESTION: return an exception if there are no muskateers that can make a move? - returns empty list 
    				}
                }
            }
    	  		result = availMusket;
    	}
    	 	
    	else if (this.turn.equals(Type.GUARD)) {
        //If a GUARD cell does not have a piece beside it (empty i.e. not guard or musketeer) then it means that guard can be moved
       	//Then add the GUARD cell's to the list of pieces that can be moved on GUARD's turn
    		for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {	
                    if (board[i][j].toString().equals("O")) { 	
                    	try {   		
                    		if ((i==0 && j == 0) && (!this.board[i][j+1].hasPiece() || !this.board[i+1][j].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//special corner caseYLLI
                    		else if ((i==0 && j == 4) && (!this.board[i][j-1].hasPiece() || !this.board[i+1][j].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 0) && (!this.board[i-1][j].hasPiece() || !this.board[i][j+1].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 4) && (!this.board[i-1][j].hasPiece() || !this.board[i][j-1].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if (i==0 && (!this.board[i+1][j].hasPiece()|| !this.board[i][j-1].hasPiece()|| !this.board[i][j+1].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//can't go left in column A
                    		else if (i==4 && (!this.board[i-1][j].hasPiece() || !this.board[i][j-1].hasPiece()|| !this.board[i][j+1].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//can't go right in column E
                    		else if (j==0 && (!this.board[i-1][j].hasPiece() || !this.board[i][j+1].hasPiece()|| !this.board[i+1][j].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//can't go up in row 1
                    		else if (j==4 && (!this.board[i-1][j].hasPiece() || !this.board[i][j-1].hasPiece()|| !this.board[i+1][j].hasPiece())) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
                    		}//can't go down in row 5
                    		else if (!this.board[i][j-1].hasPiece() || !this.board[i][j+1].hasPiece()|| !this.board[i-1][j].hasPiece() || !this.board[i+1][j].hasPiece()) {
                            	availGuards.add(getCell(new Coordinate(i, j)));
            				}//every other case where i is not A or E, and j is not 1(0) or 5(4)
						} 
                    	catch (Exception e) {
							// TODO: handle exception
						}
    				}
                }
            }
			result = availGuards;
		}
        return result;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) { // TODO
    	//PLEASE NOTE******
    	//This method depends on the turn. 
    	//It is best testing in-game unless there is a way to manipulate the turn type (Guard or Musketeer) when testing.
    	//In testing, if you input a guard cell coordinate it will not know what the turn is and therefore treat it like a musketeer.
        List<Cell> availGuardMove = new ArrayList<>();
        List<Cell> availMusketMove = new ArrayList<>();
        List<Cell> result = new ArrayList<>();
    	//This method depends on the type of piece the fromCell is for testing purposes. 
    	//if (this.turn.equals(Type.MUSKETEER)) {
        if (fromCell.getPiece().getType().toString().equals("MUSKETEER")) {
    	//If a GUARD has a Musketeer to the left, right, up, down (depending on where on the board) the guard is, then add that guard cell to list of possible destinations for the Musketeer to move to
    		for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {	
                    if (this.board[i][j].toString().equals("O")) { 	
                    	String inputString = fromCell.getCoordinate().toString();
                    	try {   		
                    		if ((i==0 && j == 0) && (this.board[i][j+1].getCoordinate().toString().equals(inputString) || this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==0 && j == 4) && (this.board[i][j-1].getCoordinate().toString().equals(inputString) || this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 0) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j+1].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 4) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==0) && (this.board[i+1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString)|| this.board[i][j+1].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go left in column A
                    		else if ((i==4) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString)|| this.board[i][j+1].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go right in column E
                    		else if ((j==0) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j+1].getCoordinate().toString().equals(inputString)|| this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go up in row 1
                    		else if ((j==4) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString)|| this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go down in row 5
                    		else if (this.board[i][j-1].getCoordinate().toString().equals(inputString) || this.board[i][j+1].getCoordinate().toString().equals(inputString) || this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i+1][j].getCoordinate().toString().equals(inputString)) {
                            	availMusketMove.add(getCell(new Coordinate(i, j)));
            				}//every other case where i is not A or E, and j is not 1(0) or 5(4)
						} 
                    	catch (Exception e) {
							// TODO: handle exception
						}
    				}
                }
            }
    		result = availMusketMove;
		}
    	else if (fromCell.getPiece().getType().toString().equals("GUARD")) {
    	//If a cell on the board does not have a piece and it has a guard cell  up, down, left, right (depending on where on board), then add that empty cell to the list of possible destinations for the Guard
    		for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {	
                    if (!this.board[i][j].hasPiece()) { 	
                    	String inputString = fromCell.getCoordinate().toString();
                    	try {   		
                    		if ((i==0 && j == 0) && (this.board[i][j+1].getCoordinate().toString().equals(inputString) || this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==0 && j == 4) && (this.board[i][j-1].getCoordinate().toString().equals(inputString) || this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 0) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j+1].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==4 && j == 4) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//special corner case
                    		else if ((i==0) && (this.board[i+1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString)|| this.board[i][j+1].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go left in column A
                    		else if ((i==4) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString)|| this.board[i][j+1].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go right in column E
                    		else if ((j==0) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j+1].getCoordinate().toString().equals(inputString)|| this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go up in row 1
                    		else if ((j==4) && (this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i][j-1].getCoordinate().toString().equals(inputString)|| this.board[i+1][j].getCoordinate().toString().equals(inputString))) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
                    		}//can't go down in row 5
                    		else if (this.board[i][j-1].getCoordinate().toString().equals(inputString) || this.board[i][j+1].getCoordinate().toString().equals(inputString) || this.board[i-1][j].getCoordinate().toString().equals(inputString) || this.board[i+1][j].getCoordinate().toString().equals(inputString)) {
                            	availGuardMove.add(getCell(new Coordinate(i, j)));
            				}//every other case where i is not A or E, and j is not 1(0) or 5(4)
						} 
                    	catch (Exception e) {
							// TODO: handle exception
						}
    				}
                }
            }
    		result = availGuardMove;
    	}
        return result;
    }

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() { // TODO
    	//Used by RandomAgent class to get a random possible move
    	//For the piece the RandomAgent plays based on possible cells and destinations
        List<Move> possibleMoves= new ArrayList<>();

        for (int i=0; i<getPossibleCells().size(); i++) {
        		for (int j=0; j<getPossibleDestinations(getPossibleCells().get(i)).size(); j++) {
        				possibleMoves.add(new Move(getPossibleCells().get(i), getPossibleDestinations(getPossibleCells().get(i)).get(j)));
        		}	
        }
        return possibleMoves;
      }

    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     * 
     */
    public boolean isGameOver() { // TODO
        //Checks if each Musketeer has no destination i.e. list is empty -> MUSKETEER WINS
    	//Checks if the Musketeers are all on the same row OR on the same column -> GUARD WINS
    	boolean result = false;
		boolean noDestination = false;
		boolean sameRowColumn = false;
        List<Cell> musketWinners= new ArrayList<>();
        	
			//Checks if the number of destinations for each Musketeer cell is 0 (nowhere to move)
    		for (int i=0; i < getMusketeerCells().size(); i++) {
    			if (getPossibleDestinations(getMusketeerCells().get(i)).size() == 0) {
    				musketWinners.add(getMusketeerCells().get(i));
        		}	
    		} 		
    		//If all 3 Musketeers have no possible destination -> MUSKETEER wins
    		if (musketWinners.size() == 3) {
				result = true;
				this.winner = Type.MUSKETEER;
			}
			
    		//Checks if all 3 Musketeers are on the same column OR if all 3 Musketeers are on the same row
    		//If either condition is true for column or row -> GUARD wins
			if(getMusketeerCells().get(0).getCoordinate().col == getMusketeerCells().get(1).getCoordinate().col && getMusketeerCells().get(1).getCoordinate().col == getMusketeerCells().get(2).getCoordinate().col ||
					getMusketeerCells().get(0).getCoordinate().row == getMusketeerCells().get(1).getCoordinate().row && getMusketeerCells().get(1).getCoordinate().row == getMusketeerCells().get(2).getCoordinate().row) {
				result = true;
				this.winner = Type.GUARD;
			}
    	return result;
    
        }    
    

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
    
  ///********************Helper methods*******************************************
    /**
     * HELPER method to return the coordinates of all Musketeeers
     * Doesn't check if the Musketeers have a possible move, just returns their coordinates
     * @return List of Coordinates of Musketeers
     */
    private List<Coordinate> getCoordinatesHelper(List <Cell> cells) { // TODO
        
        List<Coordinate> availCoordinates = new ArrayList<>();
        	
        	for (int i=0; i < cells.size(); i++) {
        		availCoordinates.add(cells.get(i).getCoordinate());
        	}
           return availCoordinates;
        }
    
 
    
}
