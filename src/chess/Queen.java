package chess;

public class Queen extends Piece {

    public Queen(String color, String position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Square src, Square dest, Board board, String move) {
        // The queen can move horizontally, vertically, or diagonally.
        boolean isHorizontalOrVertical = src.getRank() == dest.getRank() || src.getFile() == dest.getFile();
        boolean isDiagonal = Math.abs(src.getRank() - dest.getRank()) == Math.abs(src.getFile() - dest.getFile());

        if (!isHorizontalOrVertical && !isDiagonal) {
            return false;  // The queen must move either horizontally, vertically, or diagonally.
        }

        // Check if the path between src and dest is clear.
        if (!isPathClear(src, dest, board, move)) {
            return false;
        }

        // Check if the destination square is either empty or occupied by an opponent's piece.
        Piece destinationPiece = board.getPiece(dest);
        if (destinationPiece != null && destinationPiece.getColor().equals(this.color)) {
            return false;  // Can't move to a square occupied by your own piece.
        }

        return true;  // The move is valid.
    }

    @Override
    public String getSymbol() {
        return color.equals(Constants.WHITE) ? "wQ" : "bQ";
    }

    public boolean isPathClear(Square src, Square dest, Board board, String move) {
        // Check if the move is horizontal, vertical, or diagonal, and check the path accordingly.
        if (src.getRank() == dest.getRank()) {
            // Horizontal move: Check columns.
            return isHorizontalPathClear(src, dest, board);
        } else if (src.getFile() == dest.getFile()) {
            // Vertical move: Check rows.
            return isVerticalPathClear(src, dest, board);
        } else if (Math.abs(src.getRank() - dest.getRank()) == Math.abs(src.getFile() - dest.getFile())) {
            // Diagonal move: Check diagonal.
            return isDiagonalPathClear(src, dest, board, move);
        }
        return false;  // Invalid move (shouldn't reach here if isValidMove is working properly).
    }

    private boolean isHorizontalPathClear(Square src, Square dest, Board board) {
        // Determine the direction of the horizontal move.
        int colIncrement = (dest.getFile() - src.getFile()) > 0 ? 1 : -1;

        // Check each square between src and dest along the horizontal path.
        int col = src.getFile() + colIncrement;
        while (col != dest.getFile()) {
            Square nextSquare = new Square((char) col, src.getRank());
            if (board.getPiece(nextSquare) != null) {
                return false;  // A piece is blocking the path.
            }
            col += colIncrement;
        }

        return true;
    }

    private boolean isVerticalPathClear(Square src, Square dest, Board board) {
        // Determine the direction of the vertical move.
        int rowIncrement = (dest.getRank() - src.getRank()) > 0 ? 1 : -1;

        // Check each square between src and dest along the vertical path.
        int row = src.getRank() + rowIncrement;
        while (row != dest.getRank()) {
            Square nextSquare = new Square(src.getFile(), row);
            if (board.getPiece(nextSquare) != null) {
                return false;  // A piece is blocking the path.
            }
            row += rowIncrement;
        }

        return true;
    }

    private boolean isDiagonalPathClear(Square src, Square dest, Board board, String move) {
        // Determine the direction of the diagonal move.
        int rowIncrement = (dest.getRank() - src.getRank()) > 0 ? 1 : -1;
        int colIncrement = (dest.getFile() - src.getFile()) > 0 ? 1 : -1;
        
        // Check each square between src and dest along the diagonal path.
        int row = src.getRank() + rowIncrement;
        int col = src.getFile() + colIncrement;
        while (row != dest.getRank() && col != dest.getFile()) {
            Square nextSquare = new Square((char) (col), row);
            if (board.getPiece(nextSquare) != null) {
                return false;  // A piece is blocking the path.
            }
            row += rowIncrement;
            col += colIncrement;
        }

        return true;
    }
}
