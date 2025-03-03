package chess;

public class MoveParser {
    
    // public static String parseMove(String move) {
    //     move = move.trim();

    //     // resign
    //     if (move.equals("resign")) {
    //         return "resign";
    //     }

    //     if(move.endsWith("draw?")) {
    //         return "draw?";
    //     }

    //     if(move.matches("[a-h][1-8] [a-h][1-8]")) {
    //         return move;
    //     }

    //     String[] moveParts = move.split(" ");
    // }
    
    public static boolean isValidMove(String move) {
        if (move.length() != 4) {
            return false;
        }
        Square src = Square.convertSquare(move.substring(0, 2));
        Square dest = Square.convertSquare(move.substring(2));
        return src.isValid() && dest.isValid();
    }
    
    public static boolean isValidMove(String move, Board board) {
        if (!isValidMove(move)) {
            return false;
        }
        Square src = Square.convertSquare(move.substring(0, 2));
        Square dest = Square.convertSquare(move.substring(2));
        return board.getPiece(src).isValidMove(src, dest, board);
    }
    
    public static boolean isCaptureMove(String move, Board board) {
        Square src = Square.convertSquare(move.substring(0, 2));
        Square dest = Square.convertSquare(move.substring(2));
        return board.getPiece(dest) != null && board.getPiece(src).getColor() != board.getPiece(dest).getColor();
    }
    
    public static boolean isCastlingMove(String move, Board board) {
        if (move.equals("e1g1") && board.getPiece("e1") instanceof King && board.getPiece("h1") instanceof Rook) {
            return true;
        }
        if (move.equals("e1c1") && board.getPiece("e1") instanceof King && board.getPiece("a1") instanceof Rook) {
            return true;
        }
        if (move.equals("e8g8") && board.getPiece("e8") instanceof King && board.getPiece("h8") instanceof Rook) {
            return true;
        }
        if (move.equals("e8c8") && board.getPiece("e8") instanceof King && board.getPiece("a8") instanceof Rook) {
            return true;
        }
        return false;
    }
    
    public static boolean isEnPassantMove(String move, Board board) {
        if (board.getEnPassantSquare() == null) {
            return false;
        }
        Square src = Square.convertSquare(move.substring(0, 2));
        Square dest = Square.convertSquare(move.substring(2));
        return board.getPiece(src) instanceof Pawn && dest.equals(board.getEnPassantSquare());
    }

}
