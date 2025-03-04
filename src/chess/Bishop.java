package chess;

public class Bishop extends Piece {
    
    public Bishop(String color, String position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Square src, Square dest, Board board) {
        if (!isInsideBoard(dest)) {
            return false;
        }
        if (src.equals(dest)) {
            return false;
        }
        if (Math.abs(dest.getFile() - src.getFile()) != Math.abs(dest.getRank() - src.getRank())) {
            return false;
        }
        if (board.getPiece(dest) != null && board.getPiece(dest).getColor().equals(color)) {
            return false;
        }
        int fileDirection = dest.getFile() > src.getFile() ? 1 : -1;
        int rankDirection = dest.getRank() > src.getRank() ? 1 : -1;
        Square current = new Square((char) (src.getFile() + fileDirection), src.getRank() + rankDirection);
        while (!current.equals(dest)) {
            if (board.getPiece(current) != null) {
                return false;
            }
            current = new Square((char) (current.getFile() + fileDirection), current.getRank() + rankDirection);
        }
        return true;
    }

    @Override
    public String getSymbol() {
        return color.equals(Constants.WHITE) ? "wB" : "bB";
    }

}
