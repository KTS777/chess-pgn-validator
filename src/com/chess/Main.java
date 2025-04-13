package com.chess;

import com.chess.parser.PGNParser;
import com.chess.parser.PGNParser.ParsedGame;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PGNParser parser = new PGNParser();
        List<ParsedGame> games = parser.parseFile("games/sample.pgn");

        for (int i = 0; i < games.size(); i++) {
            ParsedGame game = games.get(i);
            System.out.println("Game " + (i + 1) + ":");

            System.out.println("Tags:");
            for (var entry : game.tags.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("Moves:");
            if (game.moves.isEmpty()) {
                System.out.println("  (No moves found)");
            } else {
                for (String move : game.moves) {
                    System.out.println("  " + move);
                }
            }

            if (!game.syntaxErrors.isEmpty()) {
                System.out.println("Syntax Errors:");
                for (String error : game.syntaxErrors) {
                    System.out.println("  - " + error);
                }
            } else {
                System.out.println("No syntax errors.");
            }

            System.out.println("--------------");
        }
    }
}
