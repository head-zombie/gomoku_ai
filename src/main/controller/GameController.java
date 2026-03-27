package main.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.ai.Minimax;
import main.core.Board;
import main.core.Cell;
import main.core.Move;
import main.gui.BoardPanel;

public class GameController extends MouseAdapter {
    private Minimax minimax;
    private Board board;
    private BoardPanel boardPanel;
    private String winnerMessage;
    private boolean isGameOver = false;

    public boolean getIsGameOver() {
        return this.isGameOver;
    }

    public GameController(Board board, BoardPanel boardPanel) {
        this.board = board;
        this.boardPanel = boardPanel;
        this.minimax = new Minimax();
    }

    public String getWinnerMessage() {
        return winnerMessage;
    }

    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (isGameOver)
            return;

        int col = (mouseEvent.getX() - BoardPanel.OFFSET) / BoardPanel.CELL_SIZE;
        int row = (mouseEvent.getY() - BoardPanel.OFFSET) / BoardPanel.CELL_SIZE;

        if (board.makeMove(row, col, Cell.HUMAN)) {
            boardPanel.repaint();
            if (board.checkWin(row, col)) {
                isGameOver = true;
                winnerMessage = "You Win";
                boardPanel.repaint();
                return;
            }
            makeAiMove();
        }
    }

    private void makeAiMove() {
        Move bestMove = minimax.findBestMove(board);
        if (board.makeMove(bestMove.getRow(), bestMove.getCol(), Cell.AI)) {
            boardPanel.repaint();
            if (board.checkWin(bestMove.getRow(), bestMove.getCol())) {
                isGameOver = true;
                winnerMessage = "AI Win";
                boardPanel.repaint();
            }
            return;
        }
    }
}
