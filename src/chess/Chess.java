package chess;

import java.util.ArrayList;

public class Chess {

    enum Player { white, black }
    public static Board board;
    public static Player currentPlayer;

    public Chess() {
        board = new Board();
        currentPlayer = Player.white;
    }
    
    /**
     * Plays the next move for whichever player has the turn.
     *
     * @param move String for next move, e.g. "a2 a3"
     *
     * @return A ReturnPlay instance that contains the result of the move.
     *         See the section "The Chess class" in the assignment description for details of
     *         the contents of the returned ReturnPlay instance.
     */
    public static ReturnPlay play(String move) {
                
        move = move.trim();
        ReturnPlay result = new ReturnPlay();
        
        // empty input
        if (move.isEmpty()) {
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            result.piecesOnBoard = board.populatePiecesOnBoard();
            return result;  // Return illegal move if empty input
        }

        // resign
        if (move.equalsIgnoreCase("resign")) {
            if(currentPlayer == Player.white){
                result.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
                return result;
            }
            else{
                result.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
                return result;
            }
        }

        // draw?
        boolean draw = false;
        if (move.endsWith(" draw?")) {
            move = move.substring(0, move.length() - 6).trim();  // Strip "draw?" from move
            draw = true;
        }
        /*********** may need to remove this if block since we need 
        to account for pawn promotion which has 3 parts **********/
        // Simple move parsing (assumes input is valid)
        String[] parts = move.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid move format! Use 'e2 e4' format.");
            ReturnPlay invalidMove = new ReturnPlay();
            invalidMove.message = ReturnPlay.Message.ILLEGAL_MOVE;
            result.piecesOnBoard = board.populatePiecesOnBoard();
            return result;
        }   

        int fromRow = Character.getNumericValue(parts[0].charAt(1)) - 1;     // fromRank
        int fromCol = parts[0].charAt(0) - 'a';                            // fromFile
        int toRow = Character.getNumericValue(parts[1].charAt(1)) - 1;       // toRank
        int toCol = parts[1].charAt(0) - 'a';                              // toFile   

        // validation check for every piece move here
        Piece piece = board.getPiece(fromRow, fromCol);
        if (piece == null) {
            System.out.println("No piece at that position!");
            ReturnPlay invalidMove = new ReturnPlay();
            invalidMove.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return invalidMove;
        }

        // Move the piece once it passes validation
        board.setPiece(fromRow, fromCol, toRow, toCol, parts[1]);
        
        if(draw){
            result.message = ReturnPlay.Message.DRAW;
            result.piecesOnBoard = board.populatePiecesOnBoard();
            return result;
        }
        // Swap turns
        currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
        System.out.println("It is now " + currentPlayer + "'s turn.");
        
        result.piecesOnBoard = board.populatePiecesOnBoard();
        return result;
    }
    
    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        /* FILL IN THIS METHOD */
        board = new Board();

        // Initialize pieces (as you did before)
        board.initializeBoard();

        currentPlayer = Player.white; // Reset the turn to white
       
        // need to resent any en passant square, castling rights, pawn promotions
       
        System.out.println("New chess game started! White goes first.");
        
        board.printBoard();
    }

}