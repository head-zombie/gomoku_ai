package main.ai;

import main.core.Board;
import main.core.Cell;
import main.core.Move;

public class Minimax {
    private Evaluator evaluator;

    public Minimax() {
        this.evaluator = new Evaluator();
    }

    public Minimax(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Move findBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move m : board.getPossibleMove(board)) {
            board.makeMove(m.getRow(), m.getCol(), Cell.AI);
            int score = minimax(board, 2, false);
            board.undoMove(m.getRow(), m.getCol());

            if (score > bestScore) {
                bestScore = score;
                bestMove = m;
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean flag) {
        if (depth == 0 || board.isGameOver()) {
            return evaluator.evaluate(board);
        }
        if (flag) {
            int maxEval = Integer.MIN_VALUE;
            for (Move m : board.getPossibleMove(board)) {
                board.makeMove(m.getRow(), m.getCol(), Cell.AI);
                int eval = minimax(board, depth - 1, false);
                board.undoMove(m.getRow(), m.getCol());
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move m : board.getPossibleMove(board)) {
                board.makeMove(m.getRow(), m.getCol(), Cell.HUMAN);
                int eval = minimax(board, depth - 1, true);
                board.undoMove(m.getRow(), m.getCol());
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
}
