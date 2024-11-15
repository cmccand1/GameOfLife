package Projects.GameOfLife;

/**
 * The Main class is the entry point for the Game of Life application. It initializes the model,
 * view, and controller, and sets up the main application window.
 */
public class Main {

  public static void main(String[] args) {
    Model gameModel = Model.newRandomGame(.2);
//    Model gameModel = Model.newBlankGame();
    GameView gameView = new GameView(gameModel);
    ControlsPanel controlsPanel = new ControlsPanel(gameModel);
    Controller gameController = new Controller(gameModel, gameView, controlsPanel);
    GameFrame gameFrame = new GameFrame(gameView, controlsPanel);
  }
}
