package Projects.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static Projects.GameOfLife.Model.COLS;
import static Projects.GameOfLife.Model.ROWS;


public class View extends JPanel {

    Model model;

    private static final int BTN_HEIGHT = 15;
    private static final int BTN_WIDTH = 15;
    private static final Color BORDER_COLOR = new Color(152, 152, 152);
    private static final Color DEAD_COLOR = new Color(126, 126, 126);
    private static final Color ALIVE_COLOR = new Color(255, 255, 51);

    private final JButton[][] board;

    public View(Model model) {
        this.model = model;
        setLayout(new GridLayout(ROWS, COLS));
        board = new JButton[ROWS][COLS];
        initBoard(board);
    }

    private void initBoard(JButton[][] gameBoard) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameBoard[i][j] = new JButton();
                gameBoard[i][j].putClientProperty("row", i);
                gameBoard[i][j].putClientProperty("col", j);
                gameBoard[i][j].setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
                gameBoard[i][j].setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
                gameBoard[i][j].setOpaque(true);
                colorDead(i, j);
                add(gameBoard[i][j]);
            }
        }
    }

    public boolean isColoredAlive(int i, int j) {
        return board[i][j].getBackground().equals(ALIVE_COLOR);
    }

    public void colorAlive(int i, int j) {
        board[i][j].setBackground(ALIVE_COLOR);
    }

    public void colorDead(int i, int j) {
        board[i][j].setBackground(DEAD_COLOR);
    }

    public void addCellListeners(ActionListener listener) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].addActionListener(listener);
            }
        }
    }

    public void updateView(Model model) {
        boolean[][] nextBoard = model.getNextBoard();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (nextBoard[i][j]) {
                    colorAlive(i, j);
                } else {
                    colorDead(i, j);
                }
            }
        }
    }
}
