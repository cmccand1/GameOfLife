package Projects.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static Projects.GameOfLife.Model.COLS;
import static Projects.GameOfLife.Model.ROWS;

/**
 * The View class represents the graphical user interface of the Game of Life.
 * It initializes the board and provides methods to update the view based on the model's state.
 */
public final class View extends JPanel implements Observer {

    private static final int BTN_HEIGHT = 15;
    private static final int BTN_WIDTH = 15;
    private static final Color BORDER_COLOR = new Color(152, 152, 152);
    private static final Color DEAD_COLOR = new Color(126, 126, 126);
    private static final Color ALIVE_COLOR = new Color(255, 255, 51);

    private final JButton[][] board;
    private final Model model;

    /**
     * Constructs a new View and initializes the board.
     */
    public View(Model model) {
        this.model = model;
        setLayout(new GridLayout(ROWS, COLS));
        board = new JButton[ROWS][COLS];
        initBoard();
    }

    /**
     * Initializes the board with buttons representing cells.
     * Each button is configured with properties and added to the panel.
     */
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

    /**
     * Sets the state of the cell at the specified position.
     *
     * @param i     the row index of the cell
     * @param j     the column index of the cell
     * @param alive the new state of the cell (true for alive, false for dead)
     */
    public void setCell(int i, int j, boolean alive) {
        if (alive) {
            board[i][j].setBackground(ALIVE_COLOR);
        } else {
            board[i][j].setBackground(DEAD_COLOR);
        }
    }

    /**
     * Adds action listeners to all cells in the board.
     *
     * @param listener the action listener to be added to each cell
     */
    public void addCellListeners(ActionListener listener) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].addActionListener(listener);
            }
        }
    }

    /**
     * Checks if the cell at the specified position is colored as alive.
     *
     * @param i the row index of the cell
     * @param j the column index of the cell
     *
     * @return true if the cell is colored as alive, false otherwise
     */
    public boolean isColoredAlive(int i, int j) {
        return board[i][j].getBackground().equals(ALIVE_COLOR);
    }

    /**
     * Defines the method that will be called when the observed object is updated.
     */
    @Override
    public void update() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                setCell(i, j, model.isAlive(i, j));
            }
        }
    }
}
