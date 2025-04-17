package com.chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    @Test
    void testValidDiagonalMove() {
        Board board = new Board(true);
        Bishop bishop = new Bishop(true);
        board.placePiece(bishop, 2, 2);
        assertTrue(bishop.isValidMove(2, 2, 5, 5, board.getBoard())); // c3 to f6
    }

    @Test
    void testBlockedPath() {
        Board board = new Board(true);
        Bishop bishop = new Bishop(true);
        board.placePiece(bishop, 2, 2);
        board.placePiece(new Pawn(true), 3, 3);
        assertFalse(bishop.isValidMove(2, 2, 5, 5, board.getBoard()));
    }

    @Test
    void testCaptureEnemy() {
        Board board = new Board(true);
        Bishop bishop = new Bishop(true);
        board.placePiece(bishop, 4, 4);
        board.placePiece(new Knight(false), 1, 1);
        assertTrue(bishop.isValidMove(4, 4, 1, 1, board.getBoard()));
    }

    @Test
    void testCaptureOwnPieceInvalid() {
        Board board = new Board(true);
        Bishop bishop = new Bishop(false);
        board.placePiece(bishop, 4, 4);
        board.placePiece(new Queen(false), 1, 1);
        assertFalse(bishop.isValidMove(4, 4, 1, 1, board.getBoard()));
    }

    @Test
    void testInvalidHorizontalMove() {
        Board board = new Board(true);
        Bishop bishop = new Bishop(true);
        board.placePiece(bishop, 3, 3);
        assertFalse(bishop.isValidMove(3, 3, 3, 6, board.getBoard()));
    }

    @Test
    void testInvalidVerticalMove() {
        Board board = new Board(true);
        Bishop bishop = new Bishop(false);
        board.placePiece(bishop, 3, 3);
        assertFalse(bishop.isValidMove(3, 3, 6, 3, board.getBoard()));
    }
}
