package main.ai;

import main.core.Board;
import main.core.Cell;

public class Evaluator {
    private boolean visited[][][];

    public int evaluate(Board board) {
        int score = 0;
        visited = new boolean[board.SIZE][board.SIZE][4];
        int[][] dir = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 } };

        for (int row = 0; row < board.SIZE; row++) {
            for (int col = 0; col < board.SIZE; col++) {

                int player = board.getCellVal(row, col);
                if (player == Cell.EMPTY) {
                    // Add center bonus for empty cells? No, for occupied.
                    continue;
                }

                // Add position bonus for center
                int centerDistance = Math.abs(row - board.SIZE/2) + Math.abs(col - board.SIZE/2);
                int positionBonus = Math.max(0, 7 - centerDistance);
                if (player == Cell.AI) {
                    score += positionBonus;
                } else {
                    score -= positionBonus;
                }

                for (int i = 0; i < 4; i++) {
                    if (visited[row][col][i])
                        continue;

                    int[] d = dir[i];

                    int cnt = 1;
                    int leftExtend = count(board, row, col, d[0], d[1], player, i);
                    int rightExtend = count(board, row, col, -d[0], -d[1], player, i);
                    cnt += leftExtend + rightExtend;

                    // Check if open: check positions beyond the ends
                    int leftEndRow = row + (leftExtend + 1) * d[0];
                    int leftEndCol = col + (leftExtend + 1) * d[1];
                    boolean leftOpen = leftEndRow >= 0 && leftEndRow < board.SIZE && leftEndCol >= 0 && leftEndCol < board.SIZE && board.getCellVal(leftEndRow, leftEndCol) == Cell.EMPTY;

                    int rightEndRow = row - (rightExtend + 1) * d[0];
                    int rightEndCol = col - (rightExtend + 1) * d[1];
                    boolean rightOpen = rightEndRow >= 0 && rightEndRow < board.SIZE && rightEndCol >= 0 && rightEndCol < board.SIZE && board.getCellVal(rightEndRow, rightEndCol) == Cell.EMPTY;

                    boolean isOpen = leftOpen || rightOpen;

                    int point = getPoint(cnt, isOpen);
                    if (player == Cell.AI)
                        score += point;
                    else
                        score -= point;

                    visited[row][col][i] = true;
                }
            }
        }

        return score;
    }

    private int count(Board board, int row, int col, int dr, int dc, int player, int i) {
        int cnt = 0;
        int nr = row + dr;
        int nc = col + dc;
        while (nr >= 0 && nr < board.SIZE && nc >= 0 && nc < board.SIZE && board.getCellVal(nr, nc) == player) {
            visited[nr][nc][i] = true;
            cnt++;
            nr += dr;
            nc += dc;
        }
        return cnt;
    }

    private boolean isOpen(Board board, int row, int col, int player) {
        if (row < 0 || row >= board.SIZE || col < 0 || col >= board.SIZE) {
            return false; // Out of bounds, not open
        }
        return board.getCellVal(row, col) == Cell.EMPTY;
    }

    private int getPoint(int cnt, boolean isOpen) {
        if (cnt == 1) return isOpen ? 10 : 5;
        if (cnt == 2) return isOpen ? 30 : 15;
        if (cnt == 3) return isOpen ? 100 : 50;
        if (cnt == 4) return isOpen ? 500 : 100;
        if (cnt == 5) return 10000; // Win
        return 0;
    }
}
