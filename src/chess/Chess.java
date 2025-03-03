package chess;
// package chess;

import java.util.ArrayList;

// import ReturnPiece;
// import ReturnPlay;
// import ReturnPiece.PieceFile;
// import ReturnPiece.PieceType;
// import ReturnPlay.Message;

// import chess.Chess.Player;

public class Chess {

    enum Player { white, black }
    
    
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
        
        System.out.println("Move received: " + move);

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

    if (board[fromRow][fromCol] == null) {
        System.out.println("No piece at that position!");
        ReturnPlay invalidMove = new ReturnPlay();
        invalidMove.message = ReturnPlay.Message.ILLEGAL_MOVE;
        return invalidMove;
    }

    // Move the piece
    board[toRow][toCol] = board[fromRow][fromCol];
    board[fromRow][fromCol] = null;

    // Swap turns
    currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
    System.out.println("It is now " + currentPlayer + "'s turn.");
    
    // printBoard(); // Show updated board

    ReturnPlay result = new ReturnPlay();
    result.piecesOnBoard = new ArrayList<>();

    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            if (board[row][col] != null) {
                ReturnPiece piece = board[row][col];
                result.piecesOnBoard.add(piece);
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
        board = new ReturnPiece[8][8];

        // Initialize pieces (as you did before)
        board[0][0] = new ReturnPiece(ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.a, 1);
        board[0][1] = new ReturnPiece(ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.b, 1);
        board[0][2] = new ReturnPiece(ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.c, 1);
        board[0][3] = new ReturnPiece(ReturnPiece.PieceType.WQ, ReturnPiece.PieceFile.d, 1);
        board[0][4] = new ReturnPiece(ReturnPiece.PieceType.WK, ReturnPiece.PieceFile.e, 1);
        board[0][5] = new ReturnPiece(ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.f, 1);
        board[0][6] = new ReturnPiece(ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.g, 1);
        board[0][7] = new ReturnPiece(ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.h, 1);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new ReturnPiece(ReturnPiece.PieceType.WP, ReturnPiece.PieceFile.values()[i], 2);
        }

        board[7][0] = new ReturnPiece(ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.a, 8);
        board[7][1] = new ReturnPiece(ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.b, 8);
        board[7][2] = new ReturnPiece(ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.c, 8);
        board[7][3] = new ReturnPiece(ReturnPiece.PieceType.BQ, ReturnPiece.PieceFile.d, 8);
        board[7][4] = new ReturnPiece(ReturnPiece.PieceType.BK, ReturnPiece.PieceFile.e, 8);
        board[7][5] = new ReturnPiece(ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.f, 8);
        board[7][6] = new ReturnPiece(ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.g, 8);
        board[7][7] = new ReturnPiece(ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.h, 8);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new ReturnPiece(ReturnPiece.PieceType.BP, ReturnPiece.PieceFile.values()[i], 7);
        }
        currentPlayer = Player.white; // Reset the turn to white
        System.out.println("New chess game started! White goes first.");
        printBoard();
    }

    public static void printBoard() {
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    // Print piece using its toString() (e.g., "bR", "wQ")
                    System.out.print(board[row][col].toString() + " ");
                } else {
                    // Print "##" for dark squares or "  " for light squares
                    if ((row + col) % 2 == 0) {
                        // Light square
                        System.out.print("   "); // 3 spaces
                    } else {
                        // Dark square
                        System.out.print("## ");
                    }
                }
            }
            // Print the rank label at the end of each row
            System.out.println(" " + (row + 1));
        }
        // Finally, print file labels
        System.out.println(" a  b  c  d  e  f  g  h");
    }
}