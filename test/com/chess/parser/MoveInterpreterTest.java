package com.chess.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoveInterpreterTest {

    @Test
    void testSimplePawnMove() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("e4");
        assertNotNull(move);
        assertEquals("P", move.pieceCode);
        assertEquals("e4", move.targetSquare);
        assertNull(move.disambiguation);
        assertFalse(move.isCastling());
    }

    @Test
    void testKnightMove() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("Nf3");
        assertNotNull(move);
        assertEquals("N", move.pieceCode);
        assertEquals("f3", move.targetSquare);
        assertNull(move.disambiguation);
    }

    @Test
    void testDisambiguationByFile() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("Nge2");
        assertNotNull(move);
        assertEquals("N", move.pieceCode);
        assertEquals("e2", move.targetSquare);
        assertEquals("g", move.disambiguation);
    }

    @Test
    void testDisambiguationByRank() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("R1d2");
        assertNotNull(move);
        assertEquals("R", move.pieceCode);
        assertEquals("d2", move.targetSquare);
        assertEquals("1", move.disambiguation);
    }

    @Test
    void testPawnCaptureWithDisambiguation() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("exd5");
        assertNotNull(move);
        assertEquals("P", move.pieceCode);
        assertEquals("d5", move.targetSquare);
        assertEquals("e", move.disambiguation);
    }

    @Test
    void testPromotion() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("e8=Q");
        assertNotNull(move);
        assertEquals("P", move.pieceCode);
        assertEquals("e8", move.targetSquare);
        assertEquals("Q", move.promotion);
    }

    @Test
    void testCapturePromotion() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("dxe8=R+");
        assertNotNull(move);
        assertEquals("P", move.pieceCode);
        assertEquals("e8", move.targetSquare);
        assertEquals("d", move.disambiguation);
        assertEquals("R", move.promotion);
    }

    @Test
    void testCastlingKingside() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("O-O");
        assertNotNull(move);
        assertTrue(move.isCastlingKingside);
        assertTrue(move.isCastling());
    }

    @Test
    void testCastlingQueenside() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("O-O-O");
        assertNotNull(move);
        assertTrue(move.isCastlingQueenside);
        assertTrue(move.isCastling());
    }

    @Test
    void testInvalidMoveReturnsNull() {
        MoveInterpreter.ParsedMove move = MoveInterpreter.parse("xx");
        assertNull(move);
    }
}
