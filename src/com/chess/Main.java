package com.chess;

import com.chess.controller.GameController;
import com.chess.parser.PGNParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PGNParser parser = new PGNParser();
        List<PGNParser.ParsedGame> games = parser.parseFile("games/sample.pgn");

        for (int i = 0; i < games.size(); i++) {
            System.out.println("Replaying Game " + (i + 1) + "...");
            boolean result = GameController.replayGame(games.get(i));
            System.out.println(result ? "Game is VALID ✅" : "Game is INVALID ❌");
            System.out.println("---------------------------");
        }
    }
}
