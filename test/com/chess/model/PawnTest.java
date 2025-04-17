package com.chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

    @Test
    void testSingleStepForward() {
        Board board = new Board(true);
        Pawn whitePawn = new Pawn(true);
        board.placePiece(whitePawn, 6, 4);
        assertTrue(whitePawn.isValidMove(6, 4, 5, 4, board.getBoard()));
    }

    @Test
    void testDoubleStepFromStart() {
        Board board = new Board(true);
        Pawn blackPawn = new Pawn(false);
        board.placePiece(blackPawn, 1, 3);
        assertTrue(blackPawn.isValidMove(1, 3, 3, 3, board.getBoard()));
    }

    @Test
    void testCaptureEnemy() {
        Board board = new Board(true);
        Pawn white = new Pawn(true);
        Pawn black = new Pawn(false);
        board.placePiece(white, 4, 4);
        board.placePiece(black, 3, 5);
        assertTrue(white.isValidMove(4, 4, 3, 5, board.getBoard()));
    }

    @Test
    void testInvalidMoveBlocked() {
        Board board = new Board(true);
        Pawn white = new Pawn(true);
        board.placePiece(white, 6, 0);
        board.placePiece(new Rook(true), 5, 0); // block
        assertFalse(white.isValidMove(6, 0, 5, 0, board.getBoard()));
    }

    @Test
    void testEnPassantCapture() {
        Board board = new Board(true);
        Pawn white = new Pawn(true);
        Pawn black = new Pawn(false);

        board.placePiece(white, 3, 4); // White pawn on e5
        board.placePiece(black, 1, 5); // Black pawn on f7

        board.movePiece(1, 5, 3, 5); // Black moves from f7 to f5

        int[] lastDoubleStepPawn = {3, 5}; // Track black's double step

        assertTrue(white.isValidMove(3, 4, 2, 5, board.getBoard(), lastDoubleStepPawn));
    }

    @Test
    void testInvalidEnPassantAfterOneMove() {
        Board board = new Board(true);
        Pawn white = new Pawn(true);
        Pawn black = new Pawn(false);

        board.placePiece(white, 3, 4); // e5
        board.placePiece(black, 1, 5); // f7
        board.movePiece(1, 5, 3, 5);   // black double step

        int[] lastDoubleStepPawn = null; // forgot about it
        assertFalse(white.isValidMove(3, 4, 2, 5, board.getBoard(), lastDoubleStepPawn));
    }
}
