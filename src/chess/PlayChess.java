package chess;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayChess {
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        Chess.start();
        
        String line = sc.nextLine();
        while (!line.equals("quit")) {
            if (line.equals("reset")) {
                Chess.start();
                System.out.println();
                line = sc.nextLine();
                continue;
            }
            // move
            ReturnPlay res = Chess.play(line);
            
            // print result message
            if (res.message != null) {
                System.out.println("\n"+res.message);
            }
            System.out.println();
            
            // print result board
            printBoard(res.piecesOnBoard);
            System.out.println();
            
            // next line
            line = sc.nextLine();
        }
        
        sc.close();
    }
    
    static void printBoard(ArrayList<ReturnPiece> pieces) {
        String[][] board = makeBlankBoard();
        if (pieces != null) {
            printPiecesOnBoard(pieces, board);
        }
        for (int r = 7; r >= 0; r--) {
            for (int c = 0; c < 8; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println((r + 1));
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }
    
    static String[][] makeBlankBoard() {
        String[][] board = new String[8][8];
        for (int r=0; r < 8; r++) {
            for (int c=0; c < 8; c++) {
                if (r % 2 == 0) {
                    board[r][c] = c % 2 == 0 ? "  " : "##";
                } else {
                    board[r][c] = c % 2 == 0 ? "##" : "  ";
                }
            }
        }
        return board;
    }
    
    static void printPiecesOnBoard(ArrayList<ReturnPiece> pieces, String[][] board) {
        // for (ReturnPiece rp: pieces) {
        //     int file = (""+rp.pieceFile).charAt(0) - 'a';
        //     String pieceStr = "" + rp.pieceType;
        //     String ppstr = "";
        //     ppstr += Character.toLowerCase(pieceStr.charAt(0));
        //     ppstr += pieceStr.charAt(1) == 'P' ? 'p' : pieceStr.charAt(1);
        //     board[rp.pieceRank - 1][file] = ppstr;
        //         }
        for (ReturnPiece rp: pieces) {
            int file = rp.pieceFile.toString().charAt(0) - 'a';  // 'a' -> 0, 'b' -> 1, etc.
    
            // Instead of 8 - rp.pieceRank, use rp.pieceRank - 1
            int row = rp.pieceRank - 1;  
    
            String pieceStr = rp.pieceType.toString(); // e.g., "WP", "BP"
            String ppstr = "";
            ppstr += Character.toLowerCase(pieceStr.charAt(0)); // 'w' or 'b'
            // If second char is 'P', use 'p'; else 'R', 'N', etc.
            ppstr += (pieceStr.charAt(1) == 'P') ? 'p' : pieceStr.charAt(1);
    
            // Place the piece in the display board
            board[row][file] = ppstr;
        }
    }
    
}