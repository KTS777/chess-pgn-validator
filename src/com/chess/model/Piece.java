package com.chess.model;

public abstract class Piece {
    protected boolean isWhite;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    // Abstract method: each subclass (Pawn, Rook...) must implement this
    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board);

    // For printing to console
    public abstract String getSymbol();
}
