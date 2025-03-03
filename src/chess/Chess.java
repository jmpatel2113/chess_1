package chess;

import java.util.ArrayList;

import chess.ReturnPlay.Message;

// import ReturnPiece;
// import ReturnPlay;
// import ReturnPiece.PieceFile;
// import ReturnPiece.PieceType;
// import ReturnPlay.Message;

// import chess.Chess.Player;

public class Chess {

    enum Player { white, black }
    private static Board board;
    private static Player currentPlayer;

    // public Chess() {
    //     board = new Board();
    //     currentPlayer = Player.white;
    // }
    
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
        
        // System.out.println("Move received: " + move);
        // problem 1: currentPlayer outputs null for some reason
        // problem 2: we are moving type Piece on the board, but when we want to get that piece we need ReturnPiece type
        
        move = move.trim();
        ReturnPlay result = new ReturnPlay();
        // empty input
        if (move.isEmpty()) {
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
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
        if (move.endsWith(" draw?")) {
            move = move.substring(0, move.length() - 6).trim();  // Strip "draw?" from move
            String[] parts = move.split(" ");
            int fromRow = Character.getNumericValue(parts[0].charAt(1)) - 1;
            int fromCol = parts[0].charAt(0) - 'a';
            int toRow = Character.getNumericValue(parts[1].charAt(1)) - 1;
            int toCol = parts[1].charAt(0) - 'a';
            board.movePiece(fromRow, fromCol, toRow, toCol, currentPlayer);
            result.message = ReturnPlay.Message.DRAW;
            return result;
        }


        // Simple move parsing (assumes input is valid)
        String[] parts = move.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid move format! Use 'e2 e4'.");
            ReturnPlay invalidMove = new ReturnPlay();
            invalidMove.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return invalidMove;
        }

        int fromRow = Character.getNumericValue(parts[0].charAt(1)) - 1;
        int fromCol = parts[0].charAt(0) - 'a';
        int toRow = Character.getNumericValue(parts[1].charAt(1)) - 1;
        int toCol = parts[1].charAt(0) - 'a';

        if (board.getPiece(fromCol, fromCol) == null) {
            System.out.println("No piece at that position!");
            ReturnPlay invalidMove = new ReturnPlay();
            invalidMove.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return invalidMove;
        }

        // Move the piece
        board.setPiece(fromRow, fromCol, toRow, toCol);
        board.setPieceToNull(toRow, toCol);

        // Swap turns
        currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
        System.out.println("It is now " + currentPlayer + "'s turn.");
        
        // printBoard(); // Show updated board

        ReturnPlay result = new ReturnPlay();
        result.piecesOnBoard = new ArrayList<>();

        // for (int row = 0; row < 8; row++) {
        //     for (int col = 0; col < 8; col++) {
        //         if (board.getPiece(row, col) != null) {
        //             ReturnPiece piece = board.getPiece(row, col);
        //             result.piecesOnBoard.add(piece);
        //         }
        //     }
        // }
        
        return result;
    }
    
    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        /* FILL IN THIS METHOD */
        Board board = new Board();

        // Initialize pieces (as you did before)
        board.initializeBoard();

        Player currentPlayer = Player.white; // Reset the turn to white
        // need to resent any en passant square, castling rights, pawn promotions
        System.out.println("New chess game started! White goes first.");
        // board.printBoard();
    }

    // public static void printBoard(Board board) {
    //     for (int row = 7; row >= 0; row--) {
    //         for (int col = 0; col < 8; col++) {
    //             if (board[row][col] != null) {
    //                 // Print piece using its toString() (e.g., "bR", "wQ")
    //                 System.out.print(board[row][col].toString() + " ");
    //             } else {
    //                 // Print "##" for dark squares or "  " for light squares
    //                 if ((row + col) % 2 == 0) {
    //                     // Light square
    //                     System.out.print("   "); // 3 spaces
    //                 } else {
    //                     // Dark square
    //                     System.out.print("## ");
    //                 }
    //             }
    //         }
    //         // Print the rank label at the end of each row
    //         System.out.println(" " + (row + 1));
    //     }
    //     // Finally, print file labels
    //     System.out.println(" a  b  c  d  e  f  g  h");
    // }
}