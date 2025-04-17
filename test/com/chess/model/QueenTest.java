package com.chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    @Test
    void testValidDiagonalMove() {
        Board board = new Board(true);
        Queen queen = new Queen(true);
        board.placePiece(queen, 2, 2);
        assertTrue(queen.isValidMove(2, 2, 5, 5, board.getBoard())); // c3 → f6
    }

    @Test
    void testValidVerticalMove() {
        Board board = new Board(true);
        Queen queen = new Queen(false);
        board.placePiece(queen, 0, 4);
        assertTrue(queen.isValidMove(0, 4, 5, 4, board.getBoard())); // e8 → e3
    }

    @Test
    void testValidHorizontalMove() {
        Board board = new Board(true);
        Queen queen = new Queen(true);
        board.placePiece(queen, 4, 4);
        assertTrue(queen.isValidMove(4, 4, 4, 0, board.getBoard())); // e4 → a4
    }

    @Test
    void testBlockedPathDiagonal() {
        Board board = new Board(true);
        Queen queen = new Queen(true);
        board.placePiece(queen, 3, 3);
        board.placePiece(new Pawn(true), 4, 4); // block on d5 → e6
        assertFalse(queen.isValidMove(3, 3, 6, 6, board.getBoard()));
    }

    @Test
    void testBlockedPathStraight() {
        Board board = new Board(true);
        Queen queen = new Queen(true);
        board.placePiece(queen, 0, 0);
        board.placePiece(new Pawn(true), 3, 0); // block on a5
        assertFalse(queen.isValidMove(0, 0, 5, 0, board.getBoard()));
    }

    @Test
    void testCaptureEnemy() {
        Board board = new Board(true);
        Queen queen = new Queen(false);
        board.placePiece(queen, 3, 3);
        board.placePiece(new Bishop(true), 6, 6);
        assertTrue(queen.isValidMove(3, 3, 6, 6, board.getBoard()));
    }

    @Test
    void testCaptureOwnPieceInvalid() {
        Board board = new Board(true);
        Queen queen = new Queen(true);
        board.placePiece(queen, 3, 3);
        board.placePiece(new Knight(true), 6, 6);
        assertFalse(queen.isValidMove(3, 3, 6, 6, board.getBoard()));
    }

    @Test
    void testInvalidLShapedMove() {
        Board board = new Board(true);
        Queen queen = new Queen(true);
        board.placePiece(queen, 4, 4);
        assertFalse(queen.isValidMove(4, 4, 6, 5, board.getBoard()));
    }
}
