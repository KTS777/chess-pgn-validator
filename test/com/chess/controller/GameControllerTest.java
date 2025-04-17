package com.chess.controller;

import com.chess.parser.PGNParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    @Test
    void testValidSimpleGame() {
        PGNParser.ParsedGame game = new PGNParser.ParsedGame();
        game.moves = List.of(
                "e4", "e5",
                "Nf3", "Nc6",
                "Bb5", "a6",
                "Ba4", "Nf6",
                "O-O", "Be7",
                "Re1", "b5",
                "Bb3", "d6",
                "c3", "O-O"
        );

        assertTrue(GameController.replayGame(game));
    }

    @Test
    void testGameWithPromotion() {
        PGNParser.ParsedGame game = new PGNParser.ParsedGame();
        game.moves = List.of(
                "e4", "d5",
                "exd5", "Qxd5",
                "d4", "Nc6",
                "Nc3", "Qa5",
                "d5", "Nb4",
                "a3", "Nxc2+",
                "Qxc2", "Bf5",
                "Qxf5", "Rd8",
                "Bb5+", "Rd7",
                "Qxd7#", "a1=Q" // promotion as last move (invalid in this order)
        );

        assertFalse(GameController.replayGame(game)); // a1=Q can't happen if game ended in checkmate
    }

    @Test
    void testInvalidMoveFailsReplay() {
        PGNParser.ParsedGame game = new PGNParser.ParsedGame();
        game.moves = List.of("e4", "e5", "Nf3", "Nc6", "Qh5", "Qh5"); // illegal double move

        assertFalse(GameController.replayGame(game));
    }

    @Test
    void testEnPassantValid() {
        PGNParser.ParsedGame game = new PGNParser.ParsedGame();
        game.moves = List.of(
                "e4", "d5",
                "exd5", "c5",
                "dxc6" // en passant-like
        );

        assertTrue(GameController.replayGame(game));
    }

    @Test
    void testCastlingBothSides() {
        PGNParser.ParsedGame game = new PGNParser.ParsedGame();
        game.moves = List.of(
                "e4", "e5",
                "Nf3", "Nc6",
                "Bc4", "Bc5",
                "O-O", "O-O"
        );

        assertTrue(GameController.replayGame(game));
    }
}
