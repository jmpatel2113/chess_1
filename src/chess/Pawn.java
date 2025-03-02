package chess;

public class Pawn extends Piece {
    
    public Pawn(String color, String position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Square src, Square dest, Board board) {
        int srcRank = src.getRank();
        int destRank = dest.getRank();
        int srcFile = src.getFile();
        int destFile = dest.getFile();
        int direction = color.equals(Constants.WHITE) ? 1 : -1;
        int startRank = color.equals(Constants.WHITE) ? 2 : 7;
        int enPassantRank = color.equals(Constants.WHITE) ? 5 : 4;
        int enPassantFile = board.getEnPassantSquare() != null ? board.getEnPassantSquare().getFile() : 0;
        int enPassantRank2 = color.equals(Constants.WHITE) ? 6 : 3;
        int enPassantFile2 = board.getEnPassantSquare() != null ? board.getEnPassantSquare().getFile() : 0;
        if (srcFile == destFile) {
            if (srcRank == startRank) {
                return (destRank == srcRank + 2 * direction || destRank == srcRank + direction) && board.getPiece(dest) == null && board.getPiece(new Square(srcFile, srcRank + direction)) == null;
            } else {
                return destRank == srcRank + direction && board.getPiece(dest) == null;
            }
        } else if (Math.abs(srcFile - destFile) == 1 && destRank == srcRank + direction) {
            if (destRank == enPassantRank && destFile == enPassantFile) {
                return board.getPiece(dest) == null && board.getPiece(new Square(destFile, enPassantRank2)) instanceof Pawn && board.getPiece(new Square(destFile, enPassantRank2)).getColor() != color;
            } else {
                return board.getPiece(dest) != null && board.getPiece(dest).getColor() != color;
            }
        }
        return false;
    }

    @Override
    public char getSymbol() {
        return color.equals(Constants.WHITE) ? 'P' : 'p';
    }

}
