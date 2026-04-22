package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import main.controller.GameController;
import main.core.Board;
import main.core.Cell;

public class BoardPanel extends JPanel {
    private Board board;
    private GameController gameController;
    public static final int CELL_SIZE = 60;
    public static final int OFFSET = 20;

    public BoardPanel(Board board, GameController gameController) {
        this.board = board;
        this.gameController = gameController;
        int side = board.SIZE * CELL_SIZE + 2 * OFFSET;
        this.setPreferredSize(new Dimension(side, side));
        this.setBackground(new Color(235, 235, 208));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.GRAY);
        for (int i = 0; i < board.SIZE + 1; i++) {
            int pos = OFFSET + i * CELL_SIZE;
            int maxPos = OFFSET + board.SIZE * CELL_SIZE;
            g2d.drawLine(OFFSET, pos, maxPos, pos);
            g2d.drawLine(pos, OFFSET, pos, maxPos);
        }

        for (int r = 0; r < board.SIZE; r++) {
            for (int c = 0; c < board.SIZE; c++) {
                int player = board.getCellVal(r, c);
                if (player != Cell.EMPTY) {
                    drawPiece(g2d, r, c, player);
                }
            }
        }

        if (gameController.getIsGameOver()) {
            drawOverlay(g2d);
        }
    }

    private void drawPiece(Graphics2D g2d, int row, int col, int player) {
        int x = OFFSET + col * CELL_SIZE + 4;
        int y = OFFSET + row * CELL_SIZE + 4;
        int diameter = CELL_SIZE - 8;

        if (player == Cell.HUMAN) {
            g2d.setColor(Color.BLACK);
            g2d.fillOval(x, y, diameter, diameter);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x, y, diameter, diameter);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, y, diameter, diameter);
        }
    }

    private void drawOverlay(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int boxW = getWidth() / 2;
        int boxH = 60;
        int boxX = (getWidth() - boxW) / 2;
        int boxY = (getHeight() - boxH) / 2;

        g2d.setColor(new Color(235, 235, 208));
        g2d.fillRect(boxX, boxY, boxW, boxH);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(boxX, boxY, boxW, boxH);

        String text = gameController.getWinnerMessage();
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.BLACK);

        java.awt.FontMetrics metrics = g2d.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = boxY + ((boxH - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(text, x, y);
    }
}
