package main;

import javax.swing.JFrame;

import main.controller.GameController;
import main.core.Board;
import main.gui.BoardPanel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gomuku AI");
        Board board = new Board();
        GameController gameController = new GameController(board, null);
        BoardPanel boardPanel = new BoardPanel(board, gameController);
        gameController.setBoardPanel(boardPanel);

        boardPanel.addMouseListener(gameController);

        frame.add(boardPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
