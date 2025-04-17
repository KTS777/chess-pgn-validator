package com.chess.model;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int direction = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;

        // Promotion handling
        if (isWhite && toRow == 0 && fromCol == toCol && board[toRow][toCol] == null) {
            return fromRow == 6; // single step from 2nd to 1st rank (e7 → e8)
        }
        if (!isWhite && toRow == 7 && fromCol == toCol && board[toRow][toCol] == null) {
            return fromRow == 1; // single step from 7th to 8th rank (e2 → e1)
        }


        // Promotion with capture
        if (isWhite && toRow == 0 && Math.abs(fromCol - toCol) == 1 && board[toRow][toCol] != null) {
            return fromRow == 6; // diagonal promotion capture
        }
        if (!isWhite && toRow == 7 && Math.abs(fromCol - toCol) == 1 && board[toRow][toCol] != null) {
            return fromRow == 1;
        }


        // Basic forward move
        if (fromCol == toCol) {
            // Move one forward
            if (toRow == fromRow + direction && board[toRow][toCol] == null) {
                return true;
            }
            // Move two forward from starting position
            if (fromRow == startRow && toRow == fromRow + 2 * direction &&
                    board[fromRow + direction][toCol] == null &&
                    board[toRow][toCol] == null) {
                return true;
            }
        }

        // Capture diagonally
        if (Math.abs(fromCol - toCol) == 1 && toRow == fromRow + direction) {
            Piece target = board[toRow][toCol];
            if (target != null && target.isWhite() != this.isWhite) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "P" : "p";
    }
}
