public class Chess {
    
    enum Player { white, black }
    
    private static Player currentPlayer = Player.white;
    
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
        
        /* FILL IN THIS METHOD */
        
        /* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
        /* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
        if (move == null || move.trim().isEmpty()) {
            ReturnPlay invalidMove = new ReturnPlay();
//            invalidMove.message = "Invalid move!";
            return invalidMove;
        }
        
        // Swap players after a move
        currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
        
        // Create a ReturnPlay object and populate it with a placeholder message
        ReturnPlay result = new ReturnPlay();
//        result.message = "Move played: " + move + " (Next turn: " + currentPlayer + ")";
        return result;
    }
    
    
    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        /* FILL IN THIS METHOD */
        
        currentPlayer = Player.white; // Reset the turn to white
        System.out.println("New chess game started! White goes first.");
    }
}
