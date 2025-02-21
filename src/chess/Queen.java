package chess;

public class Queen extends Piece {

    public Queen(String color, String position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Square src, Square dest, Board board) {
        int rowDiff = Math.abs(src.getRank() - dest.getRank());
        int colDiff = Math.abs(src.getFile() - dest.getFile());

        // Determine if the move is vertical, horizontal, or diagonal.
        boolean isVertical = (src.getFile() == dest.getFile());
        boolean isHorizontal = (src.getRank() == dest.getRank());
        boolean isDiagonal = (rowDiff == colDiff);

        if (!isVertical && !isHorizontal && !isDiagonal) {
            return false; // The move is not valid for a queen.
        }

        // Check that the path is clear (no pieces between src and dest).
        if (!board.isPathClear(src, dest)) {
            return false;
        }

        // Check if the destination square is occupied by a piece of the same color.
        Piece destinationPiece = board.getPiece(dest);
        if (destinationPiece != null && destinationPiece.getColor().equals(this.color)) {
            return false;
        }

        return true;
    }

    @Override
    public char getSymbol() {
        // Return an uppercase symbol for white, lowercase for black.
        return (this.color.equals(Constants.WHITE)) ? 'Q' : 'q';
    }
}