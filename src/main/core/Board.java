package main.core;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int SIZE = 15;
    private Cell[][] board;

    public Board() {
        this.board = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public boolean makeMove(int row, int col, int player) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col].getVal() == Cell.EMPTY) {
            board[row][col].setVal(player);
            return true;
        }
        return false;
    }

    public void undoMove(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            board[row][col].setVal(Cell.EMPTY);
        }
    }

    public boolean checkWin(int row, int col) {
        int player = board[row][col].getVal();
        int[][] dir = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };
        for (int[] d : dir) {
            int cnt = 1;
            cnt += count(row, col, d[0], d[1], player);
            cnt += count(row, col, -d[0], -d[1], player);
            if (cnt >= 5)
                return true;
        }
        return false;
    }

    public boolean isGameOver() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (checkWin(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int count(int r, int c, int dr, int dc, int player) {
        int cnt = 0;
        int nr = r + dr;
        int nc = c + dc;
        while (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE && board[nr][nc].getVal() == player) {
            cnt++;
            nr += dr;
            nc += dc;
        }
        return cnt;
    }

    public int getCellVal(int row, int col) {
        return board[row][col].getVal();
    }

    public List<Move> getPossibleMove(Board board) {
        List<Move> move = new ArrayList<>();
        for (int row = 0; row < board.SIZE; row++) {
            for (int col = 0; col < board.SIZE; col++) {
                if (board.getCellVal(row, col) == Cell.EMPTY) {
                    Move newMove = new Move(row, col);
                    move.add(newMove);
                }
            }
        }
        return move;
    }
}
