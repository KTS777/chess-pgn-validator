# Chess PGN Validator

A Java-based PGN (Portable Game Notation) validator that parses and replays chess games, checking for syntax errors and illegal moves according to standard chess rules.

This project supports:

- PGN syntax parsing with error reporting  
- Full piece movement validation (including en passant, castling, promotion)  
- Turn-by-turn board visualization in the console  
- JUnit tests for all model components and controller logic  

---

## Project Structure

```
├── src/
│   └── com.chess/
│       ├── controller/       # GameController: main replay engine
│       ├── model/            # Chess pieces, board representation
│       ├── parser/           # PGNParser and MoveInterpreter
│       └── view/             # Console-based visualization and Main.java
├── test/
│   └── com.chess/            # JUnit tests for model, parser, and controller
├── games/                    # PGN test files
├── lib/                      # JUnit dependencies
├── out/                      # Compiled classes
├── README.md
└── chess-pgn-validator.iml
```

---

## How to Run

### Requirements

- Java 17+ (tested on JDK 21)  
- IntelliJ IDEA (or any Java-compatible IDE)  
- JUnit 5 (already included under `lib/`)  

---

### Running Tests

- Right-click the `test/` folder → Run All Tests  
- Or run individual test classes like `RookTest`, `PawnTest`, `PGNParserTest`, etc.

---

### Running the Program

1. Open the project in IntelliJ  
2. Right-click on `Main.java` and choose **Run 'Main'**  
3. Output will be printed in the console with board state visualizations

To use a different PGN file, modify the file path in `Main.java`:
```java
parser.parseFile("games/sample.pgn");
```

---

## Included PGN Files

| File                         | Description                    |
|------------------------------|--------------------------------|
| `sample.pgn`                 | Valid, complete chess game     |
| `invalid_illegal_move.pgn`   | Contains an illegal move       |
| `invalid_disambiguation.pgn` | Invalid or ambiguous notation  |
| `invalid_missing_bracket.pgn`| Malformed tag line             |

---

## Sample PGN Input

```pgn
[Event "Broken Tags"]
[Site "Internet"]
[Date "2024.04.13"]
[White "Alice"]
[Black "Bob"]
[Result "1-0"]

1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O
```

---

## Sample Console Output

```
========== Game 1 ==========
Tags:
[Event "Broken Tags"]
[Site "Internet"]
[Date "2024.04.13"]
[White "Alice"]
[Black "Bob"]
[Result "1-0"]

After move: e4
  a b c d e f g h
8 r n b q k b n r 8
7 p p p p p p p p 7
6 . . . . . . . . 6
5 . . . . . . . . 5
4 . . . . P . . . 4
3 . . . . . . . . 3
2 P P P P . P P P 2
1 R N B Q K B N R 1
  a b c d e f g h

After move: e5
...
Result: Game is VALID
---------------------------
```

---

## Features Implemented

- PGN parsing with syntax validation  
- Pawn, Rook, Knight, Bishop, Queen, and King logic  
- Castling (`O-O` and `O-O-O`)  
- Promotion (e.g., `e8=Q`)  
- En passant  
- Move disambiguation (e.g., `R1d2`, `Nge2`)  
- Console-based board visualization  
- Early termination on invalid move  
- Descriptive error messages  
- Full unit test suite

---

## Test Coverage

| Class                      | Coverage |
|----------------------------|----------|
| `Pawn`, `Knight`, etc.     | ✓        |
| `Board`                    | ✓        |
| `PGNParser`                | ✓        |
| `MoveInterpreter`          | ✓        |
| `GameController`           | ✓        |
| Special rules (promotion, castling, en passant) | ✓ |

---

## Bonus Features

- Console-based board visualization with every move  
- Support for disambiguation and special rules  
- Realistic error handling and syntax parsing
