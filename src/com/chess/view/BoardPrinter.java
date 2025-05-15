package com.chess.view;

import com.chess.model.Board;
import com.chess.model.Piece;

public class BoardPrinter {

    public static String getBoardAsString(Board board) {
        Piece[][] grid = board.getBoard();
        StringBuilder sb = new StringBuilder();

        sb.append("  a b c d e f g h\n");
        for (int row = 0; row < 8; row++) {
            sb.append(8 - row).append(" ");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null) {
                    sb.append(piece.getSymbol()).append(" ");
                } else {
                    sb.append(". ");
                }
            }
            sb.append(8 - row).append("\n");
        }
        sb.append("  a b c d e f g h\n\n");

        return sb.toString();
    }
}
