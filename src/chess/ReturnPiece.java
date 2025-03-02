package chess;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;

class ReturnPiece {
    static enum PieceType {WP, WR, WN, WB, WQ, WK,
        BP, BR, BN, BB, BK, BQ};
    static enum PieceFile {a, b, c, d, e, f, g, h};
    
    PieceType pieceType;
    PieceFile pieceFile;

    ReturnPiece(PieceType type, PieceFile file, int rank) {
        pieceType = type;
        pieceFile = file;
        pieceRank = rank;
    }

    int pieceRank;  // 1..8
    public String toString() {
        String typeStr = pieceType.toString(); // e.g., "BP", "WN"
    // First char: 'B' or 'W' (color)
    // Second char: 'P', 'R', 'N', 'B', 'Q', or 'K' (piece type)
    char colorChar = (typeStr.charAt(0) == 'B') ? 'b' : 'w';
    char pieceChar = typeStr.charAt(1); // 'P', 'R', 'N', 'B', 'Q', 'K'
    return "" + colorChar + pieceChar;   // e.g., "bP" or "wR"
    }
    public boolean equals(Object other) {
        if (other == null || !(other instanceof ReturnPiece)) {
            return false;
        }
        ReturnPiece otherPiece = (ReturnPiece)other;
        return pieceType == otherPiece.pieceType &&
                pieceFile == otherPiece.pieceFile &&
                pieceRank == otherPiece.pieceRank;
    }
}