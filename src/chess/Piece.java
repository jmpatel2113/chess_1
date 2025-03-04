package chess;

public abstract class Piece {
    
    protected String color;
    protected String position;

    public Piece(String color, String position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public String getPosition() {
        return position;
    }

    public abstract boolean isValidMove(Square src, Square dest, Board board);
    public abstract String getSymbol();

    protected boolean isInsideBoard(Square square) {
        return square.getFile() >= 'a' && square.getFile() <= 'h' && square.getRank() >= 1 && square.getRank() <= 8;
    }



}
