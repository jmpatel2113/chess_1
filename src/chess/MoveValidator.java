package chess;
public class MoveValidator {

    /**
     * Validates a chess move provided in standard algebraic notation (e.g., "e2 e4").
     *
     * @param move         The move string, e.g., "e2 e4".
     * @param board        The current Board object.
     * @param currentTurn  The color of the player whose turn it is (e.g., Constants.WHITE or Constants.BLACK).
     * @return true if the move is valid; false otherwise.
     */
    public static boolean isValidMove(String move, Board board, String currentTurn) {
        // Checks if the move string is null or empty.
        if (move == null || move.trim().isEmpty()) {
            return false;
        }
        System.out.println("Passed 1");
        
        // Expects the move string to be in the format "e2 e4" (at least two tokens).
        String[] tokens = move.trim().split("\\s+");
        if (tokens.length < 2) {
            return false;
        }
        System.out.println("Passed 2");

        // Converts the first token into the source square and the second into the destination square.
        Square src = Square.convertSquare(tokens[0]);
        Square dest = Square.convertSquare(tokens[1]);
        
        // Ensures both source and destination squares are within valid board boundaries.
        if (!src.isValid() || !dest.isValid()) {
            return false;
        }
        System.out.println("Passed 3");

        // Ensures src is not null and dest is not occupied
        Piece srcPiece = board.getPiece(src);
        Piece destPiece = board.getPiece(dest);
        if (srcPiece == null || destPiece != null) {
            return false; 
        }
        System.out.println("Passed 4");

        // Checks that the piece belongs to the player whose turn it is.
        System.out.println(srcPiece.getColor());
        System.out.println(srcPiece);
        if (!srcPiece.getColor().equals(currentTurn)) {
            return false;
        }
        System.out.println("Passed 5");

        // Uses the piece's own isValidMove method to check if it can move from src to dest.
        if (!srcPiece.isValidMove(src, dest, board)) {
            return false;
        }
        System.out.println("Passed 6");

        // (Optional/TODO): Add additional checks here (e.g., ensure that moving the piece doesn't leave the king in check).
        
        return true;
    }
}
