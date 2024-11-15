package Projects.GameOfLife;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 * The Model class represents the state of the Game of Life application. It contains the current
 * board state and the logic to update the board based on the rules of the game.
 */
public final class Model {

  public static final int ROWS = 50;
  public static final int COLS = 50;

  private boolean[][] currentBoard;
  private boolean[][] nextBoard;
  private int currentGeneration;

  private final List<ModelObserver> modelObservers;

  private final Timer simulation;
  static final int MIN_DELAY_IN_MS = 17; // 60 FPS
  static final int INITIAL_DELAY_IN_MS = 1_000;
  static final int MAX_DELAY_IN_MS = 1_000;

  /**
   * Constructs a new Model with an initial empty board and generation count set to 0.
   */
  private Model() {
    currentGeneration = 0;
    currentBoard = new boolean[ROWS][COLS];
    nextBoard = new boolean[ROWS][COLS];
    modelObservers = new ArrayList<>();
    simulation = new Timer(INITIAL_DELAY_IN_MS, e -> {
      nextGeneration();
    });
  }

  /**
   * Creates a new Model with a randomly initialized board with a fraction of cells alive.
   *
   * @param fraction the fraction of cells to be alive in the initial board
   * @return a new Model instance with a randomly initialized board
   */
  private Model(double fraction) {
    this(); // call the default constructor
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        currentBoard[i][j] = Math.random() < fraction;
      }
    }
  }

  public static Model newBlankGame() {
    return new Model();
  }

  public static Model newRandomGame(double fraction) {
    if (fraction < 0 || fraction > 1) {
      throw new IllegalArgumentException("Fraction must be between 0 and 1");
    }
    return new Model(fraction);
  }

  public void reset() {
    currentGeneration = 0;
    currentBoard = new boolean[ROWS][COLS];
    nextBoard = new boolean[ROWS][COLS];
    notifyObservers();
  }

  // small values are faster, large values are slower
  public void setSimulationSpeed(int delayInMs) {
    if (delayInMs < MIN_DELAY_IN_MS || delayInMs > MAX_DELAY_IN_MS) {
      throw new IllegalArgumentException(
          "Delay must be between %d and %d".formatted(MIN_DELAY_IN_MS,
              MAX_DELAY_IN_MS));
    }
    simulation.setDelay(delayInMs);
  }

  public boolean isSimulationRunning() {
    return simulation.isRunning();
  }

  public void startSimulation() {
    simulation.start();
  }

  public void stopSimulation() {
    simulation.stop();
  }

  /**
   * Checks if the cell at the specified position is alive.
   *
   * @param i the row index of the cell
   * @param j the column index of the cell
   * @return true if the cell is alive, false otherwise
   */
  public boolean isAlive(int i, int j) {
    return currentBoard[i][j];
  }

  /**
   * Sets the state of the cell at the specified position.
   *
   * @param i     the row index of the cell
   * @param j     the column index of the cell
   * @param alive the new state of the cell (true for alive, false for dead)
   */
  public void setCell(int i, int j, boolean alive) {
    currentBoard[i][j] = alive;
    notifyObservers();
  }

  /**
   * Returns the current generation count.
   *
   * @return the current generation count
   */
  public int getGenerationCount() {
    return currentGeneration;
  }

  /**
   * Advances the game to the next generation by applying the rules of the Game of Life.
   */
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
    notifyObservers();
  }

  /**
   * Sets the cell at the specified position to survive in the next generation.
   *
   * @param i the row index of the cell
   * @param j the column index of the cell
   */
  private void survive(int i, int j) {
    nextBoard[i][j] = true;
  }

  /**
   * Sets the cell at the specified position to die in the next generation.
   *
   * @param i the row index of the cell
   * @param j the column index of the cell
   */
  private void die(int i, int j) {
    nextBoard[i][j] = false;
  }

  /**
   * Swaps the current board with the next board.
   */
  private void swapBoards() {
    boolean[][] temp = currentBoard;
    currentBoard = nextBoard;
    nextBoard = temp;
  }

  /**
   * Clears the next board by setting all cells to dead.
   */
  private void clearNextBoard() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        nextBoard[i][j] = false;
      }
    }
  }

  /**
   * Decides the fate of the cell at the specified position based on the number of alive neighbors.
   *
   * @param i the row index of the cell
   * @param j the column index of the cell
   */
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

  /**
   * Counts the number of alive neighbors for the cell at the specified position.
   *
   * @param i the row index of the cell
   * @param j the column index of the cell
   * @return the number of alive neighbors
   */
  private int numberOfNeighbors(int i, int j) {
    // Use a square sliding window to count the number of alive neighbors
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

  /**
   * Checks if the specified position is within the bounds of the board.
   *
   * @param i the row index
   * @param j the column index
   * @return true if the position is within bounds, false otherwise
   */
  private boolean isValidBounds(int i, int j) {
    return i >= 0 && i < currentBoard.length && j >= 0 && j < currentBoard[i].length;
  }

  /**
   * Starts the simulation when the start button is clicked and disables the button.
   */
  public void addObserver(ModelObserver view) {
    this.modelObservers.add(view);
    view.update();
  }

  /**
   * Notifies all observers of a change in the model.
   */
  public void notifyObservers() {
    for (ModelObserver view : modelObservers) {
      view.update();
    }
  }

}
