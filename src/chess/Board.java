package chess;

import java.util.ArrayList;
import java.util.List;

public class Board {
   
    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    // initializes the board with the pieces
    public void initializeBoard() {
        board[0][0] = new Rook(Constants.BLACK, "a8");
        board[0][1] = new Knight(Constants.BLACK, "b8");
        board[0][2] = new Bishop(Constants.BLACK, "c8");
        board[0][3] = new Queen(Constants.BLACK, "d8");
        board[0][4] = new King(Constants.BLACK, "e8");
        board[0][5] = new Bishop(Constants.BLACK, "f8");
        board[0][6] = new Knight(Constants.BLACK, "g8");
        board[0][7] = new Rook(Constants.BLACK, "h8");
        board[1][0] = new Pawn(Constants.BLACK, "a7");
        board[1][1] = new Pawn(Constants.BLACK, "b7");
        board[1][2] = new Pawn(Constants.BLACK, "c7");
        board[1][3] = new Pawn(Constants.BLACK, "d7");
        board[1][4] = new Pawn(Constants.BLACK, "e7");
        board[1][5] = new Pawn(Constants.BLACK, "f7");
        board[1][6] = new Pawn(Constants.BLACK, "g7");
        board[1][7] = new Pawn(Constants.BLACK, "h7");

        board[6][0] = new Pawn(Constants.WHITE, "a2");
        board[6][1] = new Pawn(Constants.WHITE, "b2");
        board[6][2] = new Pawn(Constants.WHITE, "c2");
        board[6][3] = new Pawn(Constants.WHITE, "d2");
        board[6][4] = new Pawn(Constants.WHITE, "e2");
        board[6][5] = new Pawn(Constants.WHITE, "f2");
        board[6][6] = new Pawn(Constants.WHITE, "g2");
        board[6][7] = new Pawn(Constants.WHITE, "h2");
        board[7][0] = new Rook(Constants.WHITE, "a1");
        board[7][1] = new Knight(Constants.WHITE, "b1");
        board[7][2] = new Bishop(Constants.WHITE, "c1");
        board[7][3] = new Queen(Constants.WHITE, "d1");
        board[7][4] = new King(Constants.WHITE, "e1");
        board[7][5] = new Bishop(Constants.WHITE, "f1");
        board[7][6] = new Knight(Constants.WHITE, "g1");
        board[7][7] = new Rook(Constants.WHITE, "h1");

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

    //OLD GET PIECE METHOD, MAY HAVE TO BE REINSTATED THO
    // public Piece getPiece(Square square) {
    //     return board[8 - square.getRank()][square.getFile() - 'a'];
    // }

    public Piece getPiece(Square square) {
        int rowIndex = 8 - square.getRank();    // e.g. rank=1 => rowIndex=7
        int colIndex = square.getFile() - 'a';  // e.g. file='a' => colIndex=0
    
        // *** NEW: Bounds check
        if (rowIndex < 0 || rowIndex > 7 || colIndex < 0 || colIndex > 7) {
            return null; // treat as "no piece" if out of bounds
        }
    
        return board[rowIndex][colIndex];
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

    public List<Piece> getPieces() {
        List<Piece> pieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    pieces.add(board[row][col]);
                }
            }
        }
        return pieces;
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

    // public boolean movePiece(Square src, Square dest) {
    //     Piece piece = getPiece(src);
    //     if (piece == null) {
    //         return false;
    //     }
    //     if (piece.isValidMove(src, dest, this)) {
    //         setPiece(dest, piece);
    //         // setPiece(src, null);
    //         return true;
    //     }
    //     return false;
    // }

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
        //if no king found, 
        if (kingSquare == null) {
            return false;
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(color)) {
                    // If this piece can move to kingSquare, it means the king is threatened
                    Square pieceSquare = new Square((char)(j + 'a'), 8 - i);
    
                    // We re-use the piece's isValidMove method
                    if (piece.isValidMove(pieceSquare, kingSquare, this, /* any string */ "dummy")) {
                        return true; // The king is in check
                    }
                }
            }
        }

        return false;
    }

//    need to setup the pieces and the isKingInCheck method
    public boolean isCheckmate(String color) {
        if(!isKingInCheck(color)){
            return false;
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    Square srcSquare = new Square((char) (col + 'a'), 8 - row);
                    
                    for (int destRow = 0; destRow < 8; destRow++) {
                        for (int destCol = 0; destCol < 8; destCol++) { 
                            Square destSquare = new Square((char) (destCol + 'a'), 8 - destRow);

                            if (piece.isValidMove(srcSquare, destSquare, this, "dummyVal")){
                                Piece captured = board[destRow][destCol];
                                board[destRow][destCol] = piece;
                                board[row][col] = null;
                                piece.position = (char)(destCol + 'a') + "" + (8 - destRow);
    
                                // Check if that hypothetical move removed the check
                                boolean stillInCheck = isKingInCheck(color);
    
                                // Revert the move
                                board[row][col] = piece;
                                board[destRow][destCol] = captured;
                                piece.position = (char)(col + 'a') + "" + (8 - row);
                                
                                if(!stillInCheck){
                                    return false;
                                }
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
        return null;  // No en passant square found
    }

    public void setEnPassantSquare(Square square) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece instanceof Pawn) {
                    ((Pawn) piece).setEnPassant(false);  // Reset all pawns' en passant states
                }
            }
        }
        
        // Set the new en passant square for the correct pawn
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