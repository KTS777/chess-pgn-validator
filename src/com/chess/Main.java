package com.chess;

import com.chess.controller.GameController;
import com.chess.controller.MultiThreadedGameRunner; // 🆕 import
import com.chess.parser.PGNParser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PGNParser parser = new PGNParser();
        List<PGNParser.ParsedGame> allGames = parser.parseFile("games/sample.pgn");

        List<PGNParser.ParsedGame> validSyntaxGames = new ArrayList<>();

        // Print tags and check syntax errors first
        for (int i = 0; i < allGames.size(); i++) {
            PGNParser.ParsedGame game = allGames.get(i);

            System.out.println("========== Game " + (i + 1) + " ==========");

            // Print tags
            if (!game.tags.isEmpty()) {
                System.out.println("Tags:");
                game.tags.forEach((k, v) -> System.out.println("[" + k + " \"" + v + "\"]"));
            }

            // Syntax errors
            if (!game.syntaxErrors.isEmpty()) {
                System.out.println("\nSyntax Errors:");
                for (String error : game.syntaxErrors) {
                    System.out.println(" - " + error);
                }
                System.out.println("Result: Game is INVALID (syntax)");
                System.out.println("---------------------------");
            } else {
                validSyntaxGames.add(game);
            }
        }

        System.out.println("\n========== Starting Game Replays ==========\n");

        // Replay valid syntax games in parallel
        MultiThreadedGameRunner.replayGamesInParallel(validSyntaxGames);
    }
}
