package com.chess.logic;

import com.chess.model.*;
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
        move = move.replace("+", "").replace("#", "").replace("x", "");

        String promotion = null;
        if (move.contains("=")) {
            int idx = move.indexOf('=');
            promotion = move.substring(idx + 1); // e.g. Q
            move = move.substring(0, idx);       // trim to "e8" or "dxe8"
        }

        // ✅ Handle castling first
        if (move.equals("O-O")) {
            if (isWhiteTurn) {
                // White kingside
                board.movePiece(7, 4, 7, 6); // King e1 → g1
                board.movePiece(7, 7, 7, 5); // Rook h1 → f1
            } else {
                // Black kingside
                board.movePiece(0, 4, 0, 6); // King e8 → g8
                board.movePiece(0, 7, 0, 5); // Rook h8 → f8
            }
            System.out.println("Castling: " + move);
            board.printBoard(); // Optional
            return true;
        }

        if (move.equals("O-O-O")) {
            if (isWhiteTurn) {
                // White queenside
                board.movePiece(7, 4, 7, 2); // King e1 → c1
                board.movePiece(7, 0, 7, 3); // Rook a1 → d1
            } else {
                // Black queenside
                board.movePiece(0, 4, 0, 2); // King e8 → c8
                board.movePiece(0, 0, 0, 3); // Rook a8 → d8
            }
            System.out.println("Castling: " + move);
            board.printBoard(); // Optional
            return true;
        }

        // ✅ Continue with normal move parsing
        String pieceCode;
        String targetSquare;

        String disambiguation = null;

        if (move.matches("^[a-h][1-8]$")) {
            pieceCode = "P";
            targetSquare = move;

        } else if (move.matches("^[a-h][a-h][1-8]$")) {
            pieceCode = "P";
            disambiguation = move.substring(0, 1);
            targetSquare = move.substring(1);

        } else if (move.length() == 3 && Character.isUpperCase(move.charAt(0))) {
            pieceCode = move.substring(0, 1);
            targetSquare = move.substring(1);

        } else if (move.matches("^[NBRQK][a-h][a-h][1-8]$")) {
            pieceCode = move.substring(0, 1);
            disambiguation = move.substring(1, 2); // e.g. g
            targetSquare = move.substring(2);

        } else if (move.matches("^[NBRQK][1-8][a-h][1-8]$")) {
            pieceCode = move.substring(0, 1);
            disambiguation = move.substring(1, 2); // e.g. 1
            targetSquare = move.substring(2);

        } else if (move.matches("^[NBRQK][a-h][1-8]$")) {
            pieceCode = move.substring(0, 1);
            targetSquare = move.substring(1);
        } else {
            System.out.println("Unsupported move format: " + move);
            return false;
        }


        int[] target = squareToCoords(targetSquare);
        if (target == null) return false;

        int toRow = target[0];
        int toCol = target[1];

        Piece[][] boardState = board.getBoard();


        // Find matching piece that can legally make this move
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

                if (disambiguation != null && !disambiguation.isEmpty()) {
                    if (Character.isLetter(disambiguation.charAt(0))) {
                        int disambigCol = disambiguation.charAt(0) - 'a';
                        if (col != disambigCol) continue;
                    } else if (Character.isDigit(disambiguation.charAt(0))) {
                        int disambigRow = 8 - Character.getNumericValue(disambiguation.charAt(0));
                        if (row != disambigRow) continue;
                    }
                }

                if (p.isValidMove(row, col, toRow, toCol, boardState)) {
                    // Check for promotion case (only applies to pawns)
                    if (p.getSymbol().equalsIgnoreCase("P") && promotion != null) {
                        Piece promoted;
                        boolean isWhite = p.isWhite();

                        switch (promotion.toUpperCase()) {
                            case "Q" -> promoted = new Queen(isWhite);
                            case "R" -> promoted = new Rook(isWhite);
                            case "B" -> promoted = new Bishop(isWhite);
                            case "N" -> promoted = new Knight(isWhite);
                            default -> {
                                System.out.println("Invalid promotion piece: " + promotion);
                                return false;
                            }
                        }

                        // Replace pawn with promoted piece
                        board.getBoard()[toRow][toCol] = promoted;
                        board.getBoard()[row][col] = null;

                        System.out.println("After move: " + move + "=" + promotion.toUpperCase());
                        board.printBoard(); // Optional
                        return true;

                    } else {
                        // Regular move
                        board.movePiece(row, col, toRow, toCol);
                        System.out.println("After move: " + move);
                        board.printBoard(); // Optional
                        return true;
                    }
                }

            }
        }

        System.out.println("No valid " + pieceCode + " found to perform move: " + move);
        return false;
    }



}
