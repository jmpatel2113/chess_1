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
        
        // System.out.println("Move received: " + move);
        // problem 2: we are moving type Piece on the board, but when we want to get that piece we need ReturnPiece type
        
        move = move.trim();
        ReturnPlay result = new ReturnPlay();
        
        // empty input
        if (move.isEmpty()) {
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            result.piecesOnBoard = new ArrayList<>();
            ReturnPiece empty = new ReturnPiece();
            empty.pieceType = ReturnPiece.PieceType.WP;
            empty.pieceFile = ReturnPiece.PieceFile.a;
            empty.pieceRank = 1;
            result.piecesOnBoard.add(empty);
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
            System.out.println("Invalid move format! Use 'e2 e4' format.");
            ReturnPlay invalidMove = new ReturnPlay();
            invalidMove.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return invalidMove;
        }

        int fromRow = 8-(Character.getNumericValue(parts[0].charAt(1)));   // fromRank
        int fromCol = parts[0].charAt(0) - 'a';                            // fromFile
        int toRow = 8-(Character.getNumericValue(parts[1].charAt(1)));     // toRank
        int toCol = parts[1].charAt(0) - 'a';                              // toFile   

        if (board.getPiece(fromRow, fromCol) == null) {
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
        
        result.piecesOnBoard = new ArrayList<>();

        for (int row= 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                // Convert Piece to ReturnPiece using the helper method
                result.piecesOnBoard.add(board.convertPieceToReturnPiece(piece));
                }
            }
        }
        
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