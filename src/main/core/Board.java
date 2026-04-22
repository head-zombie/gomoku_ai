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
        List<Move> moves = new ArrayList<>();
        boolean[][] candidates = new boolean[SIZE][SIZE];

        // Find all occupied cells and mark nearby cells as candidates
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getCellVal(row, col) != Cell.EMPTY) {
                    // Mark surrounding cells
                    for (int dr = -2; dr <= 2; dr++) {
                        for (int dc = -2; dc <= 2; dc++) {
                            int nr = row + dr;
                            int nc = col + dc;
                            if (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE) {
                                candidates[nr][nc] = true;
                            }
                        }
                    }
                }
            }
        }

        // Collect empty candidate cells
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (candidates[row][col] && board.getCellVal(row, col) == Cell.EMPTY) {
                    moves.add(new Move(row, col));
                }
            }
        }

        // If no candidates (early game), return center area
        if (moves.isEmpty()) {
            for (int row = SIZE/2 - 2; row <= SIZE/2 + 2; row++) {
                for (int col = SIZE/2 - 2; col <= SIZE/2 + 2; col++) {
                    if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board.getCellVal(row, col) == Cell.EMPTY) {
                        moves.add(new Move(row, col));
                    }
                }
            }
        }

        return moves;
    }
}
