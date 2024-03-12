package Projects.GameOfLife;

public final class Model {

    public static final int ROWS = 50;
    public static final int COLS = 50;

    private boolean[][] currentBoard;
    private boolean[][] nextBoard;
    private int currentGeneration;

    private Model() {
        currentGeneration = 0;
        currentBoard = new boolean[ROWS][COLS];
        nextBoard = new boolean[ROWS][COLS];
    }

    public static Model createModel() {
        return new Model();
    }

    public boolean isAlive(int i, int j) {
        return currentBoard[i][j];
    }

    public void setCell(int i, int j, boolean alive) {
        currentBoard[i][j] = alive;
    }

    public int getGenerationCount() {
        return currentGeneration;
    }

    public void nextGeneration() {
        // Iterate through the current board and populate the next board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                decideFate(i, j);
            }
        }
        currentGeneration++;
        swapBoards();
        clearNextBoard();
    }

    private void survive(int i, int j) {
        nextBoard[i][j] = true;
    }

    private void die(int i, int j) {
        nextBoard[i][j] = false;
    }

    private void swapBoards() {
        boolean[][] temp = currentBoard;
        currentBoard = nextBoard;
        nextBoard = temp;
    }

    private void clearNextBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                nextBoard[i][j] = false;
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
