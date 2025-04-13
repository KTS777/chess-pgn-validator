package com.chess.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PGNParser {

    public static class ParsedGame{
        public Map<String, String> tags = new HashMap<>();
        public List<String> moves = new ArrayList<>();
        public List<String> syntaxErrors = new ArrayList<>();
    }

    public List<ParsedGame> parseFile(String filePath) {
        List<ParsedGame> games = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            ParsedGame currentGame = new ParsedGame();
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("[") && line.endsWith("]")) {
                    // Handle start of a new game if we hit a new [Event ...] tag
                    if (line.startsWith("[Event ") && (!currentGame.tags.isEmpty() || !currentGame.moves.isEmpty())) {
                        games.add(currentGame);
                        currentGame = new ParsedGame();
                    }

                    String content = line.substring(1, line.length() - 1);
                    int spaceIndex = content.indexOf(" ");
                    if (spaceIndex == -1) {
                        currentGame.syntaxErrors.add("Malformed tag line: " + line);
                    }
                    else {
                        String tag = content.substring(0, spaceIndex).trim();
                        String value = content.substring(spaceIndex + 1).trim();

                        if (value.startsWith("\"") && value.endsWith("\"")) {
                            value = value. substring(1, value.length() -1);
                            currentGame.tags.put(tag, value);
                        } else {
                            currentGame.syntaxErrors.add("Missing quotes in tag value: " + line);
                        }
                    }

                } else {
                    String[] tokens = line.split("\\s+");
                    for (String token : tokens) {
                        if (token.matches("^[0-9]+\\.$")) {
                            continue;
                        } else if (token.matches("^[a-hNBRQKO0-9x+#=]+$")) {
                           currentGame.moves.add(token);
                        } else {
                            currentGame.syntaxErrors.add("Invalid move token: " + token);
                        }

                    }

                }

            }
            if (!currentGame.tags.isEmpty() || !currentGame.moves.isEmpty()) {
                games.add(currentGame);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return games;
    }
}
