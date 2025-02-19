// package chess;

public class Square {
    
    private char file;
    private int rank;

    public Square(char file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public static Square convertSquare(String position){
        char file = position.charAt(0);
        int rank = Integer.parseInt(position.substring(1));
        return new Square(file, rank);
    }

    public boolean isValid(){
        return (file >= 'a' && file <= 'h') && (rank >= 1 && rank <= 8);
    }

}
