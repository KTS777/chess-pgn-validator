package com.chess.model;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            Piece target = board[toRow][toCol];
            return target == null || target.isWhite() != this.isWhite;
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "N" : "n";
    }
}
