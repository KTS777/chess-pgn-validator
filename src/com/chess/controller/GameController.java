package com.chess.controller;

import com.chess.model.*;
import com.chess.parser.MoveInterpreter;
import com.chess.parser.PGNParser;
import com.chess.view.BoardPrinter;

public class GameController {

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

    private static int[] lastDoubleStepPawn = null;

    private static boolean applyMove(Board board, String move, boolean isWhiteTurn) {
        MoveInterpreter.ParsedMove parsed = MoveInterpreter.parse(move);
        if (parsed == null) return false;

        if (parsed.isCastlingKingside) {
            if (isWhiteTurn) {
                board.movePiece(7, 4, 7, 6); // e1 → g1
                board.movePiece(7, 7, 7, 5); // h1 → f1
            } else {
                board.movePiece(0, 4, 0, 6); // e8 → g8
                board.movePiece(0, 7, 0, 5); // h8 → f8
            }
            System.out.println("Castling: O-O");
            BoardPrinter.print(board);
            lastDoubleStepPawn = null;
            return true;
        }

        if (parsed.isCastlingQueenside) {
            if (isWhiteTurn) {
                board.movePiece(7, 4, 7, 2); // e1 → c1
                board.movePiece(7, 0, 7, 3); // a1 → d1
            } else {
                board.movePiece(0, 4, 0, 2); // e8 → c8
                board.movePiece(0, 0, 0, 3); // a8 → d8
            }
            System.out.println("Castling: O-O-O");
            BoardPrinter.print(board);
            lastDoubleStepPawn = null;
            return true;
        }


        int[] target = squareToCoords(parsed.targetSquare);
        if (target == null) return false;

        int toRow = target[0];
        int toCol = target[1];
        Piece[][] boardState = board.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = boardState[row][col];
                if (p == null || p.isWhite() != isWhiteTurn) continue;


                boolean matchType = parsed.pieceCode.equalsIgnoreCase(p.getSymbol());

                if (!matchType) continue;

                // Disambiguation
                if (parsed.disambiguation != null && !parsed.disambiguation.isEmpty()) {
                    char c = parsed.disambiguation.charAt(0);
                    if (Character.isLetter(c) && col != (c - 'a')) continue;
                    if (Character.isDigit(c) && row != (8 - Character.getNumericValue(c))) continue;
                }

                // En passant-aware move validation
                boolean isValid = (p instanceof Pawn pawn)
                        ? pawn.isValidMove(row, col, toRow, toCol, boardState, lastDoubleStepPawn)
                        : p.isValidMove(row, col, toRow, toCol, boardState);

                if (!isValid) continue;

                // Handle promotion
                if (p instanceof Pawn && parsed.promotion != null) {
                    Piece promoted;
                    switch (parsed.promotion.toUpperCase()) {
                        case "Q" -> promoted = new Queen(isWhiteTurn);
                        case "R" -> promoted = new Rook(isWhiteTurn);
                        case "B" -> promoted = new Bishop(isWhiteTurn);
                        case "N" -> promoted = new Knight(isWhiteTurn);
                        default -> {
                            System.out.println("Invalid promotion: " + parsed.promotion);
                            return false;
                        }
                    }
                    board.placePiece(promoted, toRow, toCol);
                    board.placePiece(null, row, col);
                }

                // Handle en passant capture
                else if (p instanceof Pawn &&
                        Math.abs(col - toCol) == 1 &&
                        board.getPiece(toRow, toCol) == null) {
                    board.movePiece(row, col, toRow, toCol);
                    int capturedRow = isWhiteTurn ? toRow + 1 : toRow - 1;
                    board.placePiece(null, capturedRow, toCol);
                }
                // Regular move
                else {
                    board.movePiece(row, col, toRow, toCol);
                }

                // Update last double pawn move
                if (p instanceof Pawn && Math.abs(toRow - row) == 2) {
                    lastDoubleStepPawn = new int[]{toRow, toCol};
                } else {
                    lastDoubleStepPawn = null;
                }

                System.out.println("After move: " + move);
                BoardPrinter.print(board);
                return true;
            }
        }

        System.out.println("No valid " + parsed.pieceCode + " found to perform move: " + move);
        return false;
    }




}
