package com.chess.view;

import com.chess.model.Board;
import com.chess.model.Piece;

public class BoardPrinter {

    public static void print(Board board) {
        Piece[][] grid = board.getBoard();
        System.out.println("  a b c d e f g h");
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null) {
                    System.out.print(piece.getSymbol() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println((8 - row));
        }
        System.out.println("  a b c d e f g h\n");
    }
}
