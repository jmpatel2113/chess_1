package chess;

public class Rook extends Piece {

    public Rook(String color, String position) {
        super(color, position);
    }

    // error with moving horizontally after the initial move

    @Override
    public boolean isValidMove(Square src, Square dest, Board board, String move) {
        // Rook moves must be in a straight line: either horizontal or vertical.
        boolean isHorizontal = (src.getRank() == dest.getRank());
        boolean isVertical = (src.getFile() == dest.getFile());
        
        if (!isHorizontal && !isVertical) {
            return false;  // niether horizontal nor vertical
        }
        // System.out.println("Passed Rook 1");
        
        // Check that the path between src and dest is clear.
        if (!isPathClear(src, dest, board, move)) {
            return false;
        }
        // System.out.println("Passed Rook 2");
        // String[] parts = move.split(" ");
        // int toRow = Character.getNumericValue(parts[1].charAt(1)) - 1;       // toRank
        // int toCol = parts[1].charAt(0) - 'a';                              // toFile   
        
        // // Check if the destination square is either empty or occupied by an opponent's piece.
        // Piece destinationPiece = board.getPiece(dest);
        // if (destinationPiece != null && destinationPiece.getColor().equals(this.color)) {
        //     return false;
        // }
        // System.out.println("Passed Rook 3");
        
        return true;
    }

    @Override
    public String getSymbol() {
        return color.equals(Constants.WHITE) ? "wR" : "bR";
    }

    public boolean isPathClear(Square src, Square dest, Board board, String move) {
        // Rook moves must be in a straight line: either horizontal or vertical.
        boolean isHorizontal = (src.getRank() == dest.getRank());
        boolean isVertical = (src.getFile() == dest.getFile());
        
        if (!isHorizontal && !isVertical) {
            return false;
        }
        // System.out.println("clear 1");
        // Determine the direction of the move.
        int rowIncrement = 0;
        int colIncrement = 0;

        // Set the direction of movement: horizontal or vertical
        if (isHorizontal) {
            colIncrement = (dest.getFile() - src.getFile()) > 0 ? 1 : -1;  // Move horizontally
        } else {
            rowIncrement = (dest.getRank() - src.getRank()) > 0 ? 1 : -1;  // Move vertically
        }
        // System.out.println("clear 2");
        // Move from src to dest, checking each square along the path
        int currentFile = src.getFile() - 'a';
        int currentRank = src.getRank();
        
        String[] parts = move.split(" ");
        char currentFileChar = parts[0].charAt(0);

        // System.out.println(dest.getFile() + " " + dest.getRank());
        // System.out.println(currentFileChar + " " + currentRank);
        boolean white = false;
        if(board.getPiece(8-currentRank, currentFileChar-'a').getColor().equals("w")){
            white = true;
        }
        while (currentFileChar != dest.getFile() || currentRank != dest.getRank()) {
            // Move one square along the direction of the move
            currentFileChar += colIncrement;
            currentFile += colIncrement;
            currentRank += rowIncrement;
            if(white){
                if (board.getPiece(8-currentRank, currentFileChar - 'a') != null) {
                    if(board.getPiece(8-currentRank, currentFileChar - 'a').getColor().equals("b") && 
                    (currentFileChar == dest.getFile() && currentRank == dest.getRank())){
                        return true;
                    }
                    return false; // Path is blocked
                }
            }
            else{
                if (board.getPiece(8-currentRank, currentFileChar - 'a') != null) {
                    if(board.getPiece(8-currentRank, currentFileChar - 'a').getColor().equals("w") && 
                    (currentFileChar == dest.getFile() && currentRank == dest.getRank())){
                        return true;
                    }
                    return false; // Path is blocked
                }
            }
            // System.out.println(dest.getFile() + " " + dest.getRank());
            // System.out.println(currentFileChar + " " + currentRank);
        }
        // System.out.println("clear 3");

        return true;  // Path is clear
    }
}
