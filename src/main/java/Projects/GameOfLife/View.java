package Projects.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static Projects.GameOfLife.Model.COLS;
import static Projects.GameOfLife.Model.ROWS;


public class View extends JPanel {

    private static final int BTN_HEIGHT = 15;
    private static final int BTN_WIDTH = 15;
    private static final Color BORDER_COLOR = new Color(152, 152, 152);
    private static final Color DEAD_COLOR = new Color(126, 126, 126);
    private static final Color ALIVE_COLOR = new Color(255, 255, 51);

    private final JButton[][] board;

    public View() {
        setLayout(new GridLayout(ROWS, COLS));
        board = new JButton[ROWS][COLS];
        initBoard();
    }

    public void initBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new JButton();
                board[i][j].putClientProperty("row", i);
                board[i][j].putClientProperty("col", j);
                board[i][j].setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
                board[i][j].setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
                board[i][j].setOpaque(true);
                setCell(i, j, false);
                add(board[i][j]);
            }
        }
    }

    public void setCell(int i, int j, boolean alive) {
        if (alive) {
            board[i][j].setBackground(ALIVE_COLOR);
        } else {
            board[i][j].setBackground(DEAD_COLOR);
        }
    }

    public void updateView(Model model) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                setCell(i, j, model.isAlive(i, j));
            }
        }
    }

    public void addCellListeners(ActionListener listener) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].addActionListener(listener);
            }
        }
    }

    public boolean isColoredAlive(int i, int j) {
        return board[i][j].getBackground().equals(ALIVE_COLOR);
    }
}
