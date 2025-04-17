package com.chess.parser;

import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PGNParserTest {

    private Path createTempPGNFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("test", ".pgn");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write(content);
        }
        return tempFile;
    }

    @Test
    void testValidPGNParsing() throws IOException {
        String pgn = """
            [Event "Example Game"]
            [White "Alice"]
            [Black "Bob"]
            [Result "1-0"]

            1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6
            """;

        Path file = createTempPGNFile(pgn);
        PGNParser parser = new PGNParser();
        List<PGNParser.ParsedGame> games = parser.parseFile(file.toString());

        assertEquals(1, games.size());

        PGNParser.ParsedGame game = games.get(0);
        assertEquals("Alice", game.tags.get("White"));
        assertEquals("Bob", game.tags.get("Black"));
        assertEquals(8, game.moves.size());
        assertTrue(game.syntaxErrors.isEmpty());
    }

    @Test
    void testMalformedTagLine() throws IOException {
        String pgn = """
            [Event "BadTag
            1. e4 e5
            """;
        Path file = createTempPGNFile(pgn);
        PGNParser parser = new PGNParser();
        List<PGNParser.ParsedGame> games = parser.parseFile(file.toString());

        assertEquals(1, games.size());
        assertFalse(games.get(0).syntaxErrors.isEmpty());
        assertTrue(games.get(0).syntaxErrors.get(0).contains("Malformed"));
    }

    @Test
    void testInvalidMoveToken() throws IOException {
        String pgn = """
            [Event "InvalidMove"]
            1. e4 xx 2. Nf3
            """;
        Path file = createTempPGNFile(pgn);
        PGNParser parser = new PGNParser();
        List<PGNParser.ParsedGame> games = parser.parseFile(file.toString());

        assertEquals(1, games.size());
        assertTrue(games.get(0).syntaxErrors.stream().anyMatch(e -> e.contains("Invalid move token")));
    }

    @Test
    void testMultipleGamesInOneFile() throws IOException {
        String pgn = """
            [Event "Game1"]
            [White "A"]
            1. e4 e5

            [Event "Game2"]
            [White "B"]
            1. d4 d5
            """;
        Path file = createTempPGNFile(pgn);
        PGNParser parser = new PGNParser();
        List<PGNParser.ParsedGame> games = parser.parseFile(file.toString());

        assertEquals(2, games.size());
        assertEquals("A", games.get(0).tags.get("White"));
        assertEquals("B", games.get(1).tags.get("White"));
    }
}
