package com.chess;

import com.chess.model.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        // Manually place a white pawn at row 6, col 4 (e2)
        Pawn whitePawn = new Pawn(true);
        board.placePiece(whitePawn, 6, 4);

        // Test valid 1-square move
        boolean move1 = whitePawn.isValidMove(6, 4, 5, 4, board.getBoard());
        System.out.println("White pawn e2 to e3: " + move1); // should be true

        // Test valid 2-square move from starting position
        boolean move2 = whitePawn.isValidMove(6, 4, 4, 4, board.getBoard());
        System.out.println("White pawn e2 to e4: " + move2); // should be true

        // Test invalid sideways move
        boolean move3 = whitePawn.isValidMove(6, 4, 6, 5, board.getBoard());
        System.out.println("White pawn e2 to f2: " + move3); // should be false

        // Add black pawn at row 1, col 3 (d7)
        Pawn blackPawn = new Pawn(false);
        board.placePiece(blackPawn, 1, 3);

        // Test valid 1-square move
        boolean move4 = blackPawn.isValidMove(1, 3, 2, 3, board.getBoard());
        System.out.println("Black pawn d7 to d6: " + move4); // true

        // Test valid 2-square move from start
        boolean move5 = blackPawn.isValidMove(1, 3, 3, 3, board.getBoard());
        System.out.println("Black pawn d7 to d5: " + move5); // true

        // Print board (optional visual)
        board.printBoard();
    }
}
