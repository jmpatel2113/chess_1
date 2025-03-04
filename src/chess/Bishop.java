package chess;

public class Bishop extends Piece {
    
    public Bishop(String color, String position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Square src, Square dest, Board board, String move) {
        boolean isDiagonal = Math.abs(src.getRank() - dest.getRank()) == Math.abs(src.getFile() - dest.getFile());

        if (!isDiagonal) {
            return false;  // The bishop can only move diagonally.
        }

        // Check if the path between src and dest is clear.
        if (!isPathClear(src, dest, board)) {
            return false;
        }

        // Check if the destination square is either empty or occupied by an opponent's piece.
        Piece destinationPiece = board.getPiece(dest);
        if (destinationPiece != null && destinationPiece.getColor().equals(this.color)) {
            return false;  // Can't move to a square occupied by your own piece.
        }

        return true;
    }

    public boolean isPathClear(Square src, Square dest, Board board) {
        int rowIncrement = (dest.getRank() - src.getRank()) > 0 ? 1 : -1;
        int colIncrement = (dest.getFile() - src.getFile()) > 0 ? 1 : -1;

        // Starting from the square after src, move step-by-step toward the destination.
        int row = src.getRank() + rowIncrement;
        int col = src.getFile() + colIncrement;

        // Check every square along the path from src to dest.
        while (row != dest.getRank() && col != dest.getFile()) {
            Square nextSquare = new Square((char) col, row);
            if (board.getPiece(nextSquare) != null) {
                return false;  // If there is a piece on the path, the move is not clear.
            }

            // Move to the next square along the diagonal.
            row += rowIncrement;
            col += colIncrement;
        }

        return true;
    }

    @Override
    public String getSymbol() {
        return color.equals(Constants.WHITE) ? "wB" : "bB";
    }

}
