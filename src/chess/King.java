package chess;

public class King extends Piece {

    public King(String color, String position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Square src, Square dest, Board board, String move) {
        // The King can move exactly one square in any direction: horizontally, vertically, or diagonally.
        int rankDiff = Math.abs(src.getRank() - dest.getRank());
        int fileDiff = Math.abs(src.getFile() - dest.getFile());

        // The King can move only one square in any direction.
        if (rankDiff > 1 || fileDiff > 1) {
            return false;  // Invalid move if the King is moving more than one square.
        }

        // Check if the destination square is either empty or occupied by an opponent's piece.
        Piece destinationPiece = board.getPiece(dest);
        if (destinationPiece != null && destinationPiece.getColor().equals(this.color)) {
            return false;  // Can't move to a square occupied by your own piece.
        }

        // Check if the King is not in check after the move.
        if (isKingInCheckAfterMove(src, dest, board, move)) {
            return false;  // Can't move if the King would still be in check after the move.
        }

        return true;  // The move is valid.
    }

    @Override
    public String getSymbol() {
        return color.equals(Constants.WHITE) ? "wK" : "bK";
    }

    private boolean isKingInCheckAfterMove(Square src, Square dest, Board board, String move) {
        // Temporarily make the move and check if the King would be in check.
        Piece srcPiece = board.getPiece(src);
        Piece destPiece = board.getPiece(dest);
        String position = "";
        position = Character.toString(dest.getFile()) + (Integer.toString(dest.getRank()));
        System.out.println("position: " + position);

        // Place the King at the destination temporarily
        // board.setPiece(8-dest.getRank(), dest.getFile()-'a', 8-src.getRank(), src.getFile()-'a', destPiece.getPosition());
        board.setPiece(8-src.getRank(), src.getFile()-'a', 8-dest.getRank(), dest.getFile()-'a', position);  

        // Now check if the King would be in check after the move
        boolean isInCheck = isInCheck(board, move);

        // Revert the move.
        // board.setPiece(src.getRank(), src.getFile(), dest.getRank(), dest.getFile(), srcPiece.getPosition());  // Restore the King's original square
        board.setPiece(8-dest.getRank(), dest.getFile()-'a', 8-src.getRank(), src.getFile()-'a', srcPiece.getPosition());  // Restore the piece at the destination

        return isInCheck;  // Return whether the King would be in check after the move.
    }

    private boolean isInCheck(Board board, String move) {
        // Get the current position of the King's square
        String pos = move.split(" ")[1];
        System.out.println("King's position: " + pos);
        Square kingSquare = new Square(pos.charAt(0)-'a', 8 - Integer.parseInt(pos.substring(1,2 )));

        // Check all opponent pieces to see if any of them can attack the King's square
        for (Piece opponentPiece : board.getPieces()) {
            // If the piece is an opponent's piece, check if it can attack the King's position
            System.out.println(opponentPiece + " " + opponentPiece.getPosition());
            Square srcOpponent = new Square(opponentPiece.getPosition().charAt(0)-'a', 8 - Integer.parseInt(opponentPiece.getPosition().substring(1,2)));
            if (!opponentPiece.getColor().equals(this.color) && opponentPiece.isValidMove(srcOpponent, kingSquare, board, move)) {
                // If the opponent's piece can attack the King's square, the King is in check
                return true;
            }
        }

        // If no opponent piece can attack the King, the King is not in check
        return false;
    }

}



// Piece srcPiece = board.getPiece(src);
// Piece destPiece = board.getPiece(dest);
// int fromRank = 8 - Integer.parseInt(srcPiece.getPosition().substring(1));
// int fromFile = srcPiece.getPosition().charAt(0) - 'a';
// int toRank = 8 - Integer.parseInt(destPiece.getPosition().substring(1));
// int toFile = destPiece.getPosition().charAt(0) - 'a';
// board.setPiece(fromRank, fromFile, toRank, toFile, destPiece.getPosition());