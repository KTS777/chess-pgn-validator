package com.chess.model;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        // Only allow straight lines
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        int rowStep = Integer.compare(toRow, fromRow); // 1, 0, or -1
        int colStep = Integer.compare(toCol, fromCol); // 1, 0, or -1

        int r = fromRow + rowStep;
        int c = fromCol + colStep;

        // Check if path is clear
        while (r != toRow || c != toCol) {
            if (board[r][c] != null) {
                return false; // Blocked
            }
            r += rowStep;
            c += colStep;
        }

        // Allow capture only if target is empty or opposite color
        Piece target = board[toRow][toCol];
        return target == null || target.isWhite() != this.isWhite;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "R" : "r";
    }
}
