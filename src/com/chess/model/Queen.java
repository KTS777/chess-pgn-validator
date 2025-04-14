package com.chess.model;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Diagonal move
        if (rowDiff == colDiff) {
            int rowStep = (toRow > fromRow) ? 1 : -1;
            int colStep = (toCol > fromCol) ? 1 : -1;

            int r = fromRow + rowStep;
            int c = fromCol + colStep;

            while (r != toRow && c != toCol) {
                if (board[r][c] != null) {
                    return false;
                }
                r += rowStep;
                c += colStep;
            }

            // Horizontal or vertical move
        } else if (fromRow == toRow || fromCol == toCol) {
            int rowStep = Integer.compare(toRow, fromRow);
            int colStep = Integer.compare(toCol, fromCol);

            int r = fromRow + rowStep;
            int c = fromCol + colStep;

            while (r != toRow || c != toCol) {
                if (board[r][c] != null) {
                    return false;
                }
                r += rowStep;
                c += colStep;
            }

        } else {
            return false; // Not a straight or diagonal move
        }

        // Final square must be empty or have enemy piece
        Piece target = board[toRow][toCol];
        return target == null || target.isWhite() != this.isWhite;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "Q" : "q";
    }
}
