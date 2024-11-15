package Projects.GameOfLife;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;

/**
 * The Controller class handles the interaction between the Model and the View in the Game of Life
 * application. It manages user input and updates the view based on the model's state.
 */
public final class Controller {

  Model model;
  GameView gameView;
  ControlsPanel controlsPanel;

  /**
   * Constructs a Controller with the specified model and view.
   *
   * @param model    the model of the game
   * @param gameView the view of the game
   */
  public Controller(Model model, GameView gameView, ControlsPanel controlsPanel) {
    this.model = model;
    this.gameView = gameView;
    this.controlsPanel = controlsPanel;

    // Add listeners to the views
    controlsPanel.addStartStopButtonActionListener(this::startStopButtonActionPerformed);
    controlsPanel.addNextButtonActionListener(this::nextButtonActionPerformed);
    controlsPanel.addResetButtonActionListener(this::resetButtonActionPerformed);
    controlsPanel.addSpeedSliderChangeListener(this::delaySliderChangeActionPerformed);
    gameView.addCellListeners(this::cellClickedActionPerformed);
    model.addObserver(controlsPanel);
    model.addObserver(gameView);
  }

  /**
   * Handles the action performed when a cell is clicked. Toggles the state of the cell in both the
   * model and the view.
   *
   * @param e the action event triggered by clicking a cell
   */
  private void cellClickedActionPerformed(ActionEvent e) {
    // get the button that was clicked
    JButton button = (JButton) e.getSource();
    int row = (int) button.getClientProperty("row");
    int col = (int) button.getClientProperty("col");

    // toggle the cell in the model and view
    boolean alive = model.isAlive(row, col);
    model.setCell(row, col, !alive);
    // view is observer of model, so it will update automatically

    assert ((!model.isAlive(row, col) && !gameView.isColoredAlive(row, col)) || (
        model.isAlive(row, col) && gameView.isColoredAlive(row, col)));
  }

  private void delaySliderChangeActionPerformed(ChangeEvent changeEvent) {
    int sliderValue = controlsPanel.getDelaySliderValue();
    int delayInMs = calculateSimulationDelay(sliderValue);
    model.setSimulationSpeed(delayInMs);
  }

  /**
   * Calculate the simulation delay in (milliseconds) using linear interpolation
   *
   * @param sliderValue the numerical value of the slider
   * @return the simulation delay (in milliseconds)
   */
  private int calculateSimulationDelay(int sliderValue) {
    return Model.MIN_DELAY_IN_MS +
        (Model.MAX_DELAY_IN_MS - Model.MIN_DELAY_IN_MS) *
            (ControlsPanel.MAX_SLIDER_POS - sliderValue) /
            (ControlsPanel.MAX_SLIDER_POS - ControlsPanel.MIN_SLIDER_POS);
  }

  private void resetButtonActionPerformed(ActionEvent actionEvent) {
    model.stopSimulation();
    controlsPanel.resetStartStopButton();
    model.reset();
  }

  private void nextButtonActionPerformed(ActionEvent actionEvent) {
    if (model.isSimulationRunning()) {
      model.stopSimulation();
      controlsPanel.toggleStartStopButton();
    }
    model.nextGeneration();
  }

  private void startStopButtonActionPerformed(ActionEvent actionEvent) {
    if (model.isSimulationRunning()) {
      model.stopSimulation();
    } else {
      model.startSimulation();
    }
    controlsPanel.toggleStartStopButton();
  }
}
