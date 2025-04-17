package com.chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    @Test
    void testValidLShapeMove1() {
        Board board = new Board(true);
        Knight knight = new Knight(true);
        board.placePiece(knight, 4, 4);
        assertTrue(knight.isValidMove(4, 4, 6, 5, board.getBoard())); // g3 to h5
    }

    @Test
    void testValidLShapeMove2() {
        Board board = new Board(true);
        Knight knight = new Knight(true);
        board.placePiece(knight, 4, 4);
        assertTrue(knight.isValidMove(4, 4, 3, 6, board.getBoard())); // g3 to f7
    }

    @Test
    void testCaptureEnemy() {
        Board board = new Board(true);
        Knight knight = new Knight(false);
        board.placePiece(knight, 3, 3);
        board.placePiece(new Bishop(true), 5, 4);
        assertTrue(knight.isValidMove(3, 3, 5, 4, board.getBoard()));
    }

    @Test
    void testCaptureOwnPieceInvalid() {
        Board board = new Board(true);
        Knight knight = new Knight(false);
        board.placePiece(knight, 3, 3);
        board.placePiece(new Rook(false), 5, 4);
        assertFalse(knight.isValidMove(3, 3, 5, 4, board.getBoard()));
    }

    @Test
    void testInvalidStraightMove() {
        Board board = new Board(true);
        Knight knight = new Knight(true);
        board.placePiece(knight, 2, 2);
        assertFalse(knight.isValidMove(2, 2, 2, 5, board.getBoard()));
    }

    @Test
    void testInvalidDiagonalMove() {
        Board board = new Board(true);
        Knight knight = new Knight(true);
        board.placePiece(knight, 2, 2);
        assertFalse(knight.isValidMove(2, 2, 4, 4, board.getBoard()));
    }

    @Test
    void testJumpOverPiece() {
        Board board = new Board(true);
        Knight knight = new Knight(true);
        board.placePiece(knight, 4, 4);
        board.placePiece(new Pawn(true), 5, 4); // blocking path directly
        assertTrue(knight.isValidMove(4, 4, 6, 5, board.getBoard()));
    }
}
