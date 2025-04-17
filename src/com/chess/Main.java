package com.chess;

import com.chess.controller.GameController;
import com.chess.parser.PGNParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PGNParser parser = new PGNParser();
        List<PGNParser.ParsedGame> games = parser.parseFile("games/sample.pgn");

        for (int i = 0; i < games.size(); i++) {
            PGNParser.ParsedGame game = games.get(i);

            System.out.println("========== Game " + (i + 1) + " ==========");

            // Print tags
            if (!game.tags.isEmpty()) {
                System.out.println("Tags:");
                game.tags.forEach((k, v) -> System.out.println("[" + k + " \"" + v + "\"]"));
            }

            // Syntax errors
            if (!game.syntaxErrors.isEmpty()) {
                System.out.println("\n Syntax Errors:");
                for (String error : game.syntaxErrors) {
                    System.out.println(" - " + error);
                }
                System.out.println("Result: Game is INVALID (syntax)");
                System.out.println("---------------------------");
                continue; // Skip to next game
            }

            // Attempt replay
            boolean valid = GameController.replayGame(game);
            System.out.println(valid ? "Result: Game is VALID" : "Result: Game is INVALID (logic)");
            System.out.println("---------------------------\n");
        }
    }
}
