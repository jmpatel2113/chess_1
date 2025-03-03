package chess;

public class Knight extends Piece {
    
    public Knight(String color, String position) {
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
        if (Math.abs(dest.getFile() - src.getFile()) == 2 && Math.abs(dest.getRank() - src.getRank()) == 1) {
            return true;
        }
        if (Math.abs(dest.getFile() - src.getFile()) == 1 && Math.abs(dest.getRank() - src.getRank()) == 2) {
            return true;
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return color.equals(Constants.WHITE) ? "bN" : "wN";
    }

}
