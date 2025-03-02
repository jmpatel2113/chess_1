package chess;

public class Board {
   
    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    public void initializeBoard() {
        board[0][0] = new Rook(Constants.WHITE, "a1");
        // board[0][1] = new Knight(Constants.WHITE, "b1");
        // board[0][2] = new Bishop(Constants.WHITE, "c1");
        board[0][3] = new Queen(Constants.WHITE, "d1");
        board[0][4] = new King(Constants.WHITE, "e1");
        // board[0][5] = new Bishop(Constants.WHITE, "f1");
        // board[0][6] = new Knight(Constants.WHITE, "g1");
        board[0][7] = new Rook(Constants.WHITE, "h1");
        // board[1][0] = new Pawn(Constants.WHITE, "a2");
        // board[1][1] = new Pawn(Constants.WHITE, "b2");
        // board[1][2] = new Pawn(Constants.WHITE, "c2");
        // board[1][3] = new Pawn(Constants.WHITE, "d2");
        // board[1][4] = new Pawn(Constants.WHITE, "e2");
        // board[1][5] = new Pawn(Constants.WHITE, "f2");
        // board[1][6] = new Pawn(Constants.WHITE, "g2");
        // board[1][7] = new Pawn(Constants.WHITE, "h2");

        // board[6][0] = new Pawn(Constants.BLACK, "a7");
        // board[6][1] = new Pawn(Constants.BLACK, "b7");
        // board[6][2] = new Pawn(Constants.BLACK, "c7");
        // board[6][3] = new Pawn(Constants.BLACK, "d7");
        // board[6][4] = new Pawn(Constants.BLACK, "e7");
        // board[6][5] = new Pawn(Constants.BLACK, "f7");
        // board[6][6] = new Pawn(Constants.BLACK, "g7");
        // board[6][7] = new Pawn(Constants.BLACK, "h7");
        board[7][0] = new Rook(Constants.BLACK, "a8");
        // board[7][1] = new Knight(Constants.BLACK, "b8");
        // board[7][2] = new Bishop(Constants.BLACK, "c8");
        board[7][3] = new Queen(Constants.BLACK, "d8");
        board[7][4] = new King(Constants.BLACK, "e8");
        // board[7][5] = new Bishop(Constants.BLACK, "f8");
        // board[7][6] = new Knight(Constants.BLACK, "g8");
        board[7][7] = new Rook(Constants.BLACK, "h8");

    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(board[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public Piece getPiece(Square square) {
        return board[square.getRank() - 1][square.getFile() - 'a'];
    }

    public void setPiece(Square square, Piece piece) {
        board[square.getRank() - 1][square.getFile() - 'a'] = piece;
    }

    public boolean movePiece(Square src, Square dest) {
        Piece piece = getPiece(src);
        if (piece == null) {
            return false;
        }
        if (piece.isValidMove(src, dest, this)) {
            setPiece(dest, piece);
            // setPiece(src, null);
            return true;
        }
        return false;
    }

    /**
     * Checks if the path between the source and destination squares is clear.
     * This is used for sliding pieces (like Queen, Rook, and Bishop).
     * The destination square is not checked.
     *
     * @param src  The starting square.
     * @param dest The destination square.
     * @return true if the path is clear, false otherwise.
     */
    public boolean isPathClear(Square src, Square dest) {
        int srcRank = src.getRank();
        int destRank = dest.getRank();
        int srcFile = src.getFile() - 'a';
        int destFile = dest.getFile() - 'a';
        
        int rankDiff = destRank - srcRank;
        int fileDiff = destFile - srcFile;
        
        // Determine the step direction: 0, 1, or -1 for each dimension.
        int rankStep = (rankDiff == 0) ? 0 : (rankDiff / Math.abs(rankDiff));
        int fileStep = (fileDiff == 0) ? 0 : (fileDiff / Math.abs(fileDiff));
        
        // Start checking from the square next to src.
        int currentRank = srcRank + rankStep;
        int currentFile = srcFile + fileStep;
        
        // Traverse until reaching the destination (but not including dest).
        while (currentRank != destRank || currentFile != destFile) {
            Square currentSquare = new Square((char)(currentFile + 'a'), currentRank);
            if (getPiece(currentSquare) != null) {
                return false;  // A piece is blocking the path.
            }
            currentRank += rankStep;
            currentFile += fileStep;
        }
        
        return true;
    }

    public boolean isKingInCheck(String color) {
        Square kingSquare = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece instanceof King && piece.getColor().equals(color)) {
                    kingSquare = new Square((char) (j + 'a'), 8 - i);
                    break;
                }
            }
        }
        if (kingSquare == null) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(color) && piece.isValidMove(new Square((char) (j + 'a'), 8 - i), kingSquare, this)) {
                    return true;
                }
            }
        }
        return false;
    }

//    need to setup the pieces and the isKingInCheck method
    public boolean isCheckmate(String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getColor().equals(color)) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) { //((char) (j + 'a'), 8 - i)
                            if (movePiece(new Square((char) (j + 'a'), 8 - i), new Square((char) (l + 'a'), 8 - k))) {
                                if (!isKingInCheck(color)) {
                                    return false;
                                }
                                movePiece(new Square((char) (l + 'a'), 8 - k), new Square((char) (j + 'a'), 8 - i));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
