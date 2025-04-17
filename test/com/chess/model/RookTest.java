package com.chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    @Test
    void testValidHorizontalMove() {
        Board board = new Board(true);
        Rook rook = new Rook(true);
        board.placePiece(rook, 4, 0); // a4
        assertTrue(rook.isValidMove(4, 0, 4, 7, board.getBoard()));
    }

    @Test
    void testValidVerticalMove() {
        Board board = new Board(true);
        Rook rook = new Rook(false);
        board.placePiece(rook, 0, 3);
        assertTrue(rook.isValidMove(0, 3, 5, 3, board.getBoard()));
    }


    @Test
    void testBlockedMove() {
        Board board = new Board(true);
        Rook rook = new Rook(true);
        board.placePiece(rook, 4, 0);
        board.placePiece(new Pawn(true), 4, 3);
        assertFalse(rook.isValidMove(4, 0, 4, 7, board.getBoard()));
    }

    @Test
    void testCaptureEnemy() {
        Board board = new Board(true);
        Rook rook = new Rook(true);
        board.placePiece(rook, 2, 2);
        board.placePiece(new Knight(false), 2, 5);
        assertTrue(rook.isValidMove(2, 2, 2, 5, board.getBoard()));
    }

    @Test
    void testCaptureOwnPieceInvalid() {
        Board board = new Board(true);
        Rook rook = new Rook(false);
        board.placePiece(rook, 6, 6);
        board.placePiece(new Bishop(false), 6, 2);
        assertFalse(rook.isValidMove(6, 6, 6, 2, board.getBoard()));
    }

    @Test
    void testInvalidDiagonalMove() {
        Board board = new Board(true);
        Rook rook = new Rook(true);
        board.placePiece(rook, 3, 3);
        assertFalse(rook.isValidMove(3, 3, 6, 6, board.getBoard()));
    }

    @Test
    void testBlockedOnTargetSquare() {
        Board board = new Board(true);
        Rook rook = new Rook(true);
        board.placePiece(rook, 2, 2);
        board.placePiece(new Queen(true), 2, 6);
        assertFalse(rook.isValidMove(2, 2, 2, 6, board.getBoard()));
    }
}
