package com.chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    @Test
    void testValidSingleStepForward() {
        Board board = new Board(true);
        King king = new King(true);
        board.placePiece(king, 4, 4);
        assertTrue(king.isValidMove(4, 4, 3, 4, board.getBoard())); // e4 → e5
    }

    @Test
    void testValidSingleStepDiagonal() {
        Board board = new Board(true);
        King king = new King(false);
        board.placePiece(king, 5, 5);
        assertTrue(king.isValidMove(5, 5, 4, 4, board.getBoard())); // f3 → e4
    }

    @Test
    void testInvalidTwoStepMove() {
        Board board = new Board(true);
        King king = new King(true);
        board.placePiece(king, 4, 4);
        assertFalse(king.isValidMove(4, 4, 2, 4, board.getBoard()));
    }

    @Test
    void testCaptureEnemy() {
        Board board = new Board(true);
        King king = new King(true);
        board.placePiece(king, 3, 3);
        board.placePiece(new Rook(false), 4, 4);
        assertTrue(king.isValidMove(3, 3, 4, 4, board.getBoard()));
    }

    @Test
    void testCaptureOwnPieceInvalid() {
        Board board = new Board(true);
        King king = new King(false);
        board.placePiece(king, 3, 3);
        board.placePiece(new Bishop(false), 4, 4);
        assertFalse(king.isValidMove(3, 3, 4, 4, board.getBoard()));
    }

    @Test
    void testNoMovement() {
        Board board = new Board(true);
        King king = new King(true);
        board.placePiece(king, 4, 4);
        assertFalse(king.isValidMove(4, 4, 4, 4, board.getBoard()));
    }
}
