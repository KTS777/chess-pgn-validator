package com.chess.logic;

import com.chess.model.Board;
import com.chess.model.Piece;
import com.chess.parser.PGNParser;

public class GameReplay {

    public static boolean replayGame(PGNParser.ParsedGame game) {
        Board board = new Board();
        boolean isWhiteTurn = true;

        for (String move : game.moves) {
            if (!applyMove(board, move, isWhiteTurn)) {
                System.out.println("Illegal move detected: " + move);
                return false;
            }
            isWhiteTurn = !isWhiteTurn;
        }

        System.out.println("Game is valid.");
        return true;
    }

    public static int[] squareToCoords(String square) {
        if (square.length() != 2) return null;

        char file = square.charAt(0); // a–h
        char rank = square.charAt(1); // 1–8

        int col = file - 'a';             // 'a' = 0, ..., 'h' = 7
        int row = 8 - Character.getNumericValue(rank); // '1' = row 7, ..., '8' = row 0

        if (col < 0 || col > 7 || row < 0 || row > 7) return null;

        return new int[]{row, col};
    }

    private static boolean applyMove(Board board, String move, boolean isWhiteTurn) {
        move = move.replace("+", "").replace("#", "");

        String pieceCode;
        String targetSquare;

        // Detect type of move
        if (move.length() == 2) {
            pieceCode = "P"; // pawn
            targetSquare = move;
        } else if (move.length() >= 3 && Character.isUpperCase(move.charAt(0))) {
            pieceCode = move.substring(0, 1);
            targetSquare = move.substring(move.length() - 2);
        } else {
            System.out.println("Unsupported move format: " + move);
            return false;
        }

        int[] target = squareToCoords(targetSquare);
        if (target == null) return false;

        int toRow = target[0];
        int toCol = target[1];

        Piece[][] boardState = board.getBoard();

        // Find the matching piece that can move there
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = boardState[row][col];
                if (p == null) continue;
                if (p.isWhite() != isWhiteTurn) continue;

                boolean matchType = switch (pieceCode) {
                    case "P" -> p.getSymbol().equalsIgnoreCase("P");
                    case "N" -> p.getSymbol().equalsIgnoreCase("N");
                    case "B" -> p.getSymbol().equalsIgnoreCase("B");
                    case "R" -> p.getSymbol().equalsIgnoreCase("R");
                    case "Q" -> p.getSymbol().equalsIgnoreCase("Q");
                    case "K" -> p.getSymbol().equalsIgnoreCase("K");
                    default -> false;
                };

                if (!matchType) continue;

                if (p.isValidMove(row, col, toRow, toCol, boardState)) {
                    board.movePiece(row, col, toRow, toCol);
                    return true;
                }
            }
        }

        System.out.println("No valid " + pieceCode + " found to perform move: " + move);
        return false;
    }



}
