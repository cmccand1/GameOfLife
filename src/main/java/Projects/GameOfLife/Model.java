package Projects.GameOfLife;

public class Model {

    public static final int ROWS = 50;
    public static final int COLS = 50;

    boolean[][] board1;
    boolean[][] board2;
    boolean[][] currentBoard;
    boolean[][] nextBoard;
    private int currentGeneration;

    public Model() {
        currentGeneration = 0;
        board1 = new boolean[ROWS][COLS];
        board2 = new boolean[ROWS][COLS];
        currentBoard = board1;
        nextBoard = board2;
    }

    public boolean isAlive(int i, int j) {
        return currentBoard[i][j];
    }

    public void setAlive(int i, int j) {
        currentBoard[i][j] = true;
    }

    public void setDead(int i, int j) {
        currentBoard[i][j] = false;
    }

    private void survive(int i, int j) {
        nextBoard[i][j] = true;
    }

    private void die(int i, int j) {
        nextBoard[i][j] = false;
    }

    public void swapBoards() {
        boolean[][] temp = currentBoard;
        currentBoard = nextBoard;
        nextBoard = temp;
        clearNextBoard();
    }

    public boolean[][] getNextBoard() {
        return nextBoard;
    }

    private void clearNextBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                nextBoard[i][j] = false;
            }
        }
    }

    public void nextGeneration() {
        currentGeneration++;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                decideFate(i, j);
            }
        }
    }

    private void decideFate(int i, int j) {
        int neighborCount = numberOfNeighbors(i, j);
        if (isAlive(i, j)) {
            if (neighborCount < 2 || neighborCount > 3) {
                die(i, j); // "overcrowding" or "loneliness"
            } else {
                survive(i, j); // neither overcrowding nor loneliness
            }
        } else {
            if (neighborCount == 3) { // "reproduction"
                survive(i, j);
            }
        }
    }

    private int numberOfNeighbors(int i, int j) {
        // Use a sliding window to count the number of alive neighbors
        final int OFFSET_LOW = -1;
        final int OFFSET_HIGH = 1;

        int count = 0;
        for (int a = OFFSET_LOW; a <= OFFSET_HIGH; a++) {
            for (int b = OFFSET_LOW; b <= OFFSET_HIGH; b++) {
                if (a == 0 && b == 0) {
                    continue;
                }
                if (isValidBounds(i + a, j + b) && isAlive(i + a, j + b)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValidBounds(int i, int j) {
        return i >= 0 && i < currentBoard.length && j >= 0 && j < currentBoard[i].length;
    }
}
