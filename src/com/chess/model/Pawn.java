package com.chess.model;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return isValidMove(fromRow, fromCol, toRow, toCol, board, null);
    }


    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board, int[] lastDoubleStepPawn) {
        int direction = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;

        Piece target = board[toRow][toCol];

        // Diagonal capture (normal or en passant)
        if (Math.abs(fromCol - toCol) == 1 && toRow == fromRow + direction) {
            if (target != null && target.isWhite() != this.isWhite) {
                return true;
            }

            // En passant logic
            if (target == null && lastDoubleStepPawn != null &&
                    lastDoubleStepPawn[0] == fromRow && lastDoubleStepPawn[1] == toCol) {
                Piece adjacent = board[fromRow][toCol];
                if (adjacent instanceof Pawn && adjacent.isWhite() != this.isWhite) {
                    return true;
                }
            }
        }

        // Forward single
        if (fromCol == toCol && toRow == fromRow + direction) {
            if (target == null) {
                return true;
            }
        }

        // Forward double from starting position
        if (fromCol == toCol && fromRow == startRow && toRow == fromRow + 2 * direction) {
            if (board[fromRow + direction][toCol] == null && target == null) {
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
