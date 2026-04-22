package main.ai;

import main.core.Board;
import main.core.Cell;
import main.core.Move;
import java.util.List;

public class Minimax {
    private Evaluator evaluator;

    public Minimax() {
        this.evaluator = new Evaluator();
    }

    public Minimax(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Move findBestMove(Board board) {
        List<Move> possibleMoves = board.getPossibleMove(board);
        // Sort moves by heuristic score for better alpha-beta pruning
        possibleMoves.sort((m1, m2) -> Integer.compare(evaluateMove(board, m2), evaluateMove(board, m1)));

        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move m : possibleMoves) {
            board.makeMove(m.getRow(), m.getCol(), Cell.AI);
            int score = minimax(board, 15, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.undoMove(m.getRow(), m.getCol());

            if (score > bestScore) {
                bestScore = score;
                bestMove = m;
            }
        }
        return bestMove;
    }

    private int evaluateMove(Board board, Move move) {
        // Simple heuristic: evaluate the board after making the move
        board.makeMove(move.getRow(), move.getCol(), Cell.AI);
        int score = evaluator.evaluate(board);
        board.undoMove(move.getRow(), move.getCol());
        return score;
    }

    private int minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0 || board.isGameOver()) {
            return evaluator.evaluate(board);
        }
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move m : board.getPossibleMove(board)) {
                board.makeMove(m.getRow(), m.getCol(), Cell.AI);
                int eval = minimax(board, depth - 1, false, alpha, beta);
                board.undoMove(m.getRow(), m.getCol());
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Alpha-beta pruning
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move m : board.getPossibleMove(board)) {
                board.makeMove(m.getRow(), m.getCol(), Cell.HUMAN);
                int eval = minimax(board, depth - 1, true, alpha, beta);
                board.undoMove(m.getRow(), m.getCol());
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Alpha-beta pruning
                }
            }
            return minEval;
        }
    }
}
