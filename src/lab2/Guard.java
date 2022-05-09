package lab2;

public class Guard extends Piece {

    public Guard() {
        super("O", Type.GUARD);
    }

    /**
     * Returns true if the Guard can move onto the given cell.
     * @param cell Cell to check if the Guard can move onto
     * @return True, if Guard can move onto given cell, false otherwise
     */
    @Override
    public boolean canMoveOnto(Cell cell) { // TODO 
    	//cell is the toCell we want to move to
    	//Guard cannot move to another guard cell and cannot move onto a musketeer cell
    	//Guard may only move to a cell that is empty (not reserved by a guard or musketeer)
    	boolean result = false;
    	
    	if (cell.hasPiece()) {
    		if (!cell.getPiece().getType().toString().equals("GUARD") && !cell.getPiece().getType().toString().equals("MUSKETEER")  ) {
    			result = true;
    		}
    	}
    	else {
			result = true;
		}
    	return result;
    }
}
