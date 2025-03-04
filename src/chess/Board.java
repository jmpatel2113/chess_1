package chess;

import java.util.ArrayList;

public class Board {
   
    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    // initializes the board with the pieces
    public void initializeBoard() {
        board[0][0] = new Rook(Constants.BLACK, "a1");
        board[0][1] = new Knight(Constants.BLACK, "b1");
        board[0][2] = new Bishop(Constants.BLACK, "c1");
        board[0][3] = new Queen(Constants.BLACK, "d1");
        board[0][4] = new King(Constants.BLACK, "e1");
        board[0][5] = new Bishop(Constants.BLACK, "f1");
        board[0][6] = new Knight(Constants.BLACK, "g1");
        board[0][7] = new Rook(Constants.BLACK, "h1");
        board[1][0] = new Pawn(Constants.BLACK, "a2");
        board[1][1] = new Pawn(Constants.BLACK, "b2");
        board[1][2] = new Pawn(Constants.BLACK, "c2");
        board[1][3] = new Pawn(Constants.BLACK, "d2");
        board[1][4] = new Pawn(Constants.BLACK, "e2");
        board[1][5] = new Pawn(Constants.BLACK, "f2");
        board[1][6] = new Pawn(Constants.BLACK, "g2");
        board[1][7] = new Pawn(Constants.BLACK, "h2");

        board[6][0] = new Pawn(Constants.WHITE, "a7");
        board[6][1] = new Pawn(Constants.WHITE, "b7");
        board[6][2] = new Pawn(Constants.WHITE, "c7");
        board[6][3] = new Pawn(Constants.WHITE, "d7");
        board[6][4] = new Pawn(Constants.WHITE, "e7");
        board[6][5] = new Pawn(Constants.WHITE, "f7");
        board[6][6] = new Pawn(Constants.WHITE, "g7");
        board[6][7] = new Pawn(Constants.WHITE, "h7");
        board[7][0] = new Rook(Constants.WHITE, "a8");
        board[7][1] = new Knight(Constants.WHITE, "b8");
        board[7][2] = new Bishop(Constants.WHITE, "c8");
        board[7][3] = new Queen(Constants.WHITE, "d8");
        board[7][4] = new King(Constants.WHITE, "e8");
        board[7][5] = new Bishop(Constants.WHITE, "f8");
        board[7][6] = new Knight(Constants.WHITE, "g8");
        board[7][7] = new Rook(Constants.WHITE, "h8");

    }

    // prints the board
    public void printBoard() {
        for (int i = 7; i >= 0; i--) {  // Start from the bottom row (index 7)
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    if ((i + j) % 2 == 0) {
                        System.out.print("   ");  // White square
                    } else {
                        System.out.print("## ");  // Black square
                    }
                } else {
                    System.out.print(board[i][j].getSymbol() + " ");
                }
            }
            System.out.print((i+1) + " ");  // Correct row numbering (1 to 8)
            System.out.println();
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }
    
    public Piece getPiece(int rank, int file) {
        return board[rank][file];
    }

    public Piece getPiece(Square square) {
        return board[8 - square.getRank()][square.getFile() - 'a'];
    }

    // moves the piece after validation
    public Piece setPiece(int fromRank, int fromFile, int toRank, int toFile, String toPosition) {
        Piece piece = board[fromRank][fromFile];
        board[toRank][toFile] = piece;
        board[fromRank][fromFile] = null;
        board[toRank][toFile].position = toPosition;
        return piece;
    }

    public Piece setPieceToNull(int rank, int file) {
        Piece piece = board[rank][file];
        board[rank][file] = null;
        return piece;
    }
    
    public void setPiece(Square square, Piece piece) {
        board[square.getRank() - 1][square.getFile() - 'a'] = piece;
    }

    // populates the board after each move
    public ArrayList<ReturnPiece> populatePiecesOnBoard() {
        ArrayList<ReturnPiece> resultPieces = new ArrayList<>();
        
        for (int row= 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece boardPiece = getPiece(row, col);
                if (boardPiece != null) {
                    // Convert Piece to ReturnPiece using the helper method
                    resultPieces.add(convertPieceToReturnPiece(boardPiece));
                }
            }
        }

        return resultPieces;
    }
    
    // converts type Piece to type ReturnPiece
    public ReturnPiece convertPieceToReturnPiece (Piece piece) {
        
        ReturnPiece rp = new ReturnPiece();
        String pos = piece.getPosition();
        
        // get the file and rank
        char fileChar = pos.charAt(0); 
        int rank = Integer.parseInt(pos.substring(1));
        rp.pieceRank = rank;
        rp.pieceFile = ReturnPiece.PieceFile.valueOf(String.valueOf(fileChar));
            
        // Get the piece symbol
        String sym = piece.getSymbol().toUpperCase(); 
        rp.pieceType = ReturnPiece.PieceType.valueOf(sym);
        return rp;
        
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

    public Square getEnPassantSquare() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece instanceof Pawn && ((Pawn) piece).isEnPassant()) {
                    return new Square(j + 'a', 8 - i);
                }
            }
        }
        return null;
    }

    public void setEnPassantSquare(Square square) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece instanceof Pawn) {
                    ((Pawn) piece).setEnPassant(false);
                }
            }
        }
        Piece piece = getPiece(square);
        if (piece != null && piece instanceof Pawn) {
            ((Pawn) piece).setEnPassant(true);
        }
    }

    public boolean isPromotionMove(String move) {
        return move.length() == 5;
    }

    public void promotePawn(String move, String color) {
        Square src = Square.convertSquare(move.substring(0, 2));
        Square dest = Square.convertSquare(move.substring(2, 4));
        Piece piece = null;
        switch (move.charAt(4)) {
            case 'q':
                piece = new Queen(color, dest.toString());
                break;
            case 'r':
                piece = new Rook(color, dest.toString());
                break;
            case 'b':
                piece = new Bishop(color, dest.toString());
                break;
            case 'n':
                piece = new Knight(color, dest.toString());
                break;
        }
        setPiece(src, null);
        setPiece(dest, piece);
    }

    public boolean isCastlingMove(String move) {
        return move.equals("e1g1") || move.equals("e1c1") || move.equals("e8g8") || move.equals("e8c8");
    }

    public void castle(String move) {
        if (move.equals("e1g1")) {
            setPiece(new Square('h', 1), null);
            setPiece(new Square('f', 1), new King(Constants.WHITE, "f1"));
            setPiece(new Square('g', 1), new Rook(Constants.WHITE, "g1"));
        } else if (move.equals("e1c1")) {
            setPiece(new Square('a', 1), null);
            setPiece(new Square('d', 1), new King(Constants.WHITE, "d1"));
            setPiece(new Square('c', 1), new Rook(Constants.WHITE, "c1"));
        } else if (move.equals("e8g8")) {
            setPiece(new Square('h', 8), null);
            setPiece(new Square('f', 8), new King(Constants.BLACK, "f8"));
            setPiece(new Square('g', 8), new Rook(Constants.BLACK, "g8"));
        } else if (move.equals("e8c8")) {
            setPiece(new Square('a', 8), null);
            setPiece(new Square('d', 8), new King(Constants.BLACK, "d8"));
            setPiece(new Square('c', 8), new Rook(Constants.BLACK, "c8"));
        }
    } 



}