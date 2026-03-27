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
                if (player == Cell.EMPTY)
                    continue;

                for (int i = 0; i < 4; i++) {
                    if (visited[row][col][i])
                        continue;

                    int[] d = dir[i];

                    int cnt = 1;
                    cnt += count(board, row, col, d[0], d[1], player, i);
                    cnt += count(board, row, col, -d[0], -d[1], player, i);

                    int point = getPoint(cnt);
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

    private int getPoint(int cnt) {
        if (cnt == 1)
            return 5;
        if (cnt == 2)
            return 15;
        if (cnt == 3)
            return 50;
        if (cnt == 4)
            return 100;
        if (cnt == 5)
            return 1000;
        return 0;
    }
}
