package chess;
// package chess;

public class Board {
   
    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    public void initializeBoard() {
        board[0][0] = new Rook(Constants.WHITE, "a1");
        board[0][1] = new Knight(Constants.WHITE, "b1");
        board[0][2] = new Bishop(Constants.WHITE, "c1");
        board[0][3] = new Queen(Constants.WHITE, "d1");
        board[0][4] = new King(Constants.WHITE, "e1");
        board[0][5] = new Bishop(Constants.WHITE, "f1");
        board[0][6] = new Knight(Constants.WHITE, "g1");
        board[0][7] = new Rook(Constants.WHITE, "h1");
        board[1][0] = new Pawn(Constants.WHITE, "a2");
        board[1][1] = new Pawn(Constants.WHITE, "b2");
        board[1][2] = new Pawn(Constants.WHITE, "c2");
        board[1][3] = new Pawn(Constants.WHITE, "d2");
        board[1][4] = new Pawn(Constants.WHITE, "e2");
        board[1][5] = new Pawn(Constants.WHITE, "f2");
        board[1][6] = new Pawn(Constants.WHITE, "g2");
        board[1][7] = new Pawn(Constants.WHITE, "h2");

        board[6][0] = new Pawn(Constants.BLACK, "a7");
        board[6][1] = new Pawn(Constants.BLACK, "b7");
        board[6][2] = new Pawn(Constants.BLACK, "c7");
        board[6][3] = new Pawn(Constants.BLACK, "d7");
        board[6][4] = new Pawn(Constants.BLACK, "e7");
        board[6][5] = new Pawn(Constants.BLACK, "f7");
        board[6][6] = new Pawn(Constants.BLACK, "g7");
        board[6][7] = new Pawn(Constants.BLACK, "h7");
        board[7][0] = new Rook(Constants.BLACK, "a8");
        board[7][1] = new Knight(Constants.BLACK, "b8");
        board[7][2] = new Bishop(Constants.BLACK, "c8");
        board[7][3] = new Queen(Constants.BLACK, "d8");
        board[7][4] = new King(Constants.BLACK, "e8");
        board[7][5] = new Bishop(Constants.BLACK, "f8");
        board[7][6] = new Knight(Constants.BLACK, "g8");
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
            setPiece(src, null);
            return true;
        }
        return false;
    }

    public boolean isPathClear(Square src, Square dest) {
        int rankDiff = dest.getRank() - src.getRank();
        int fileDiff = dest.getFile() - src.getFile();
        int rankDir = (int) Math.signum(rankDiff);
        int fileDir = (int) Math.signum(fileDiff);
        int rank = src.getRank() + rankDir;
        int file = src.getFile() + fileDir;
        while (rank != dest.getRank() || file != dest.getFile()) {
            if (board[rank - 1][file - 'a'] != null) {
                return false;
            }
            rank += rankDir;
            file += fileDir;
        }
        return true;
    }

    public boolean isKingInCheck(String color) {
        Square kingSquare = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece instanceof King && piece.getColor().equals(color)) {
                    kingSquare = new Square(j + 'a', 8 - i);
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
                if (piece != null && !piece.getColor().equals(color) && piece.isValidMove(new Square(j + 'a', 8 - i), kingSquare, this)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getColor().equals(color)) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (movePiece(new Square(j + 'a', 8 - i), new Square(l + 'a', 8 - k))) {
                                if (!isKingInCheck(color)) {
                                    return false;
                                }
                                movePiece(new Square(l + 'a', 8 - k), new Square(j + 'a', 8 - i));
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

    public void playMove(String move) {
        Square src = Square.convertSquare(move.substring(0, 2));
        Square dest = Square.convertSquare(move.substring(2));
        if (isPromotionMove(move)) {
            promotePawn(move, getPiece(src).getColor());
        } else if (isCastlingMove(move)) {
            castle(move);
        } else {
            movePiece(src, dest);
        }
        setEnPassantSquare(null);
    }

}
