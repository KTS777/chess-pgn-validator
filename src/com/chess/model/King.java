package com.chess.model;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // King moves only 1 square in any direction
        if (rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0)) {
            Piece target = board[toRow][toCol];
            return target == null || target.isWhite() != this.isWhite;
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "K" : "k";
    }
}
