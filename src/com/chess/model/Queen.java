package com.chess.model;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        // Diagonal move
        if (rowDiff == colDiff) {
            int r = fromRow + rowStep;
            int c = fromCol + colStep;
            while (r != toRow && c != toCol) {
                if (board[r][c] != null) {
                    return false;
                }
                r += rowStep;
                c += colStep;
            }
        }

        // Straight move
        else if (fromRow == toRow || fromCol == toCol) {
            int r = fromRow + rowStep;
            int c = fromCol + colStep;
            while (r != toRow || c != toCol) {
                if (board[r][c] != null) {
                    return false;
                }
                r += (fromRow == toRow) ? 0 : rowStep;
                c += (fromCol == toCol) ? 0 : colStep;
            }
        } else {
            return false; // Not a valid queen move
        }

        // Final square must be empty or enemy
        Piece target = board[toRow][toCol];
        return target == null || target.isWhite() != this.isWhite;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "Q" : "q";
    }
}
