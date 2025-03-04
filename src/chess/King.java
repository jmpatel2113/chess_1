package chess;

public class King extends Piece{

    public King(String color, String position) {
        super(color, position);
    }

    public String getColor() {
        return color;
    }


    public String getPosition() {
        return position;
    }

    public boolean isValidMove(Square src, Square dest, Board board) {
        if (Math.abs(src.getRank() - dest.getRank()) <= 1 && Math.abs(src.getFile() - dest.getFile()) <= 1) {
            // Get the piece at the destination square
            Piece destinationPiece = board.getPiece(dest);

            // If the destination is empty or contains an opponent's piece, move is valid
            if (destinationPiece == null || !destinationPiece.getColor().equals(this.color)) {
                return true;
            }
        }
        return false;
    }

    public String getSymbol() {
        return color.equals(Constants.WHITE) ? "wK" : "bK";
}
}