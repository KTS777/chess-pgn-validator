package com.chess.model;

public class Board {
    private Piece[][] board = new Piece[8][8];

    public Board() {
        setupInitialPosition();
    }

    public Board(boolean empty) {
        if (!empty) {
            setupInitialPosition();
        } else {
            board = new Piece[8][8]; // initialize empty board
        }
    }


    public void setupInitialPosition() {

        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn(true);
            board[1][col] = new Pawn(false);
        }


        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);


        board[7][1] = new Knight(true);
        board[7][6] = new Knight(true);
        board[0][1] = new Knight(false);
        board[0][6] = new Knight(false);


        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);
        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);


        board[7][3] = new Queen(true);
        board[0][3] = new Queen(false);


        board[7][4] = new King(true);
        board[0][4] = new King(false);
    }


    public void placePiece(Piece piece, int row, int col) {
        board[row][col] = piece;
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board[fromRow][fromCol];
        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;
    }

    public Piece[][] getBoard() {
        return board;
    }

}
