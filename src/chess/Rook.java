package chess;

public class Rook extends Piece {

    public Rook(String color, String position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Square src, Square dest, Board board) {
        // Rook moves must be in a straight line: either horizontal or vertical.
        boolean isHorizontal = (src.getRank() == dest.getRank());
        boolean isVertical = (src.getFile() == dest.getFile());
        
        if (!isHorizontal && !isVertical) {
            return false;  // Not a valid rook move.
        }
        
        // Check that the path between src and dest is clear.
        if (!board.isPathClear(src, dest)) {
            return false;
        }
        
        // Check if the destination square is either empty or occupied by an opponent's piece.
        Piece destinationPiece = board.getPiece(dest);
        if (destinationPiece != null && destinationPiece.getColor().equals(this.color)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String getSymbol() {
        // Return an rook symbol for white and black
        return (this.color.equals(Constants.WHITE)) ? "bR" : "wR";
    }
}
