package com.chess.model;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Move must be diagonal (same row and column difference)
        if (rowDiff != colDiff) {
            return false;
        }

        int rowStep = (toRow > fromRow) ? 1 : -1;
        int colStep = (toCol > fromCol) ? 1 : -1;

        int r = fromRow + rowStep;
        int c = fromCol + colStep;

        while (r != toRow && c != toCol) {
            if (board[r][c] != null) {
                return false; // Path blocked
            }
            r += rowStep;
            c += colStep;
        }

        // Final square must be empty or enemy
        Piece target = board[toRow][toCol];
        return target == null || target.isWhite() != this.isWhite;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "B" : "b";
    }
}
