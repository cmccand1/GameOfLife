package Projects.GameOfLife;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

/**
 * The Controller class handles the interaction between the Model and the View
 * in the Game of Life application. It manages user input and updates the view
 * based on the model's state.
 */
public final class Controller {

    Model model;
    View view;
    GameOptions gameOptions;

    /**
     * Constructs a Controller with the specified model and view.
     *
     * @param model the model of the game
     * @param view  the view of the game
     */
    private Controller(Model model, View view, GameOptions gameOptions) {
        this.model = model;
        this.view = view;
        this.gameOptions = gameOptions;

        // Add listeners to the views
        gameOptions.addStartStopButtonActionListener(this::startStopButtonActionPerformed);
        gameOptions.addNextButtonActionListener(this::nextButtonActionPerformed);
        gameOptions.addResetButtonActionListener(this::resetButtonActionPerformed);
        gameOptions.addSpeedSliderChangeListener(this::delaySliderChangeActionPerformed);
        view.addCellListeners(this::cellClickedActionPerformed);
        model.addObserver(gameOptions);
        model.addObserver(view);
    }


    /**
     * Creates a Controller with the specified model and view.
     *
     * @param model the model of the game
     * @param view  the view of the game
     *
     * @return a new Controller instance
     */
    public static Controller newBlankGameController(Model model, View view, GameOptions gameOptions) {
        return new Controller(model, view, gameOptions);
    }

    /**
     * Creates a Controller with the specified model and view, and initializes
     * the model with a random state.
     *
     * @param model the model of the game
     * @param view  the view of the game
     *
     * @return a new Controller instance with a randomly initialized model
     */
    public static Controller newRandomGameController(Model model, View view, GameOptions gameOptions) {
        Controller controller = new Controller(model, view, gameOptions);
        final double FRACTION_OF_CELLS_FILLED = 0.2;
        for (int i = 0; i < Model.ROWS; i++) {
            for (int j = 0; j < Model.COLS; j++) {
                if (Math.random() < FRACTION_OF_CELLS_FILLED) {
                    model.setCell(i, j, true);
                    view.setCell(i, j, true);
                }
            }
        }
        return controller;
    }

    /**
     * Toggles the state of the cell at the specified row and column in both
     * the model and the view.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     */
    private void toggleCell(int row, int col) {
        if (model.isAlive(row, col)) {
            model.setCell(row, col, false);
            view.setCell(row, col, false);
        } else {
            model.setCell(row, col, true);
            view.setCell(row, col, true);
        }
    }

    /**
     * Handles the action performed when a cell is clicked. Toggles the state
     * of the cell in both the model and the view.
     *
     * @param e the action event triggered by clicking a cell
     */
    private void cellClickedActionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = (int) button.getClientProperty("row");
        int col = (int) button.getClientProperty("col");
        toggleCell(row, col);
        // model and view agree
        assert ((!model.isAlive(row, col) && !view.isColoredAlive(row, col)) || (model.isAlive(row, col) && view.isColoredAlive(row, col)));
    }

    private void delaySliderChangeActionPerformed(ChangeEvent changeEvent) {
        int sliderValue = gameOptions.getDelaySliderValue();
        // use linear interpolation to map slider value to delay in ms
        int delayInMs = Model.MIN_DELAY_IN_MS +
                (Model.MAX_DELAY_IN_MS - Model.MIN_DELAY_IN_MS) *
                        (GameOptions.MAX_SLIDER_POS - sliderValue) /
                        (GameOptions.MAX_SLIDER_POS - GameOptions.MIN_SLIDER_POS);
        model.setSimulationSpeed(delayInMs);
    }

    private void resetButtonActionPerformed(ActionEvent actionEvent) {
        model.stopSimulation();
        gameOptions.resetStartStopButton();
        model.newBlankGame();
    }

    private void nextButtonActionPerformed(ActionEvent actionEvent) {
        if (model.isSimulationRunning()) {
            model.stopSimulation();
            gameOptions.toggleStartStopButton();
        }
        model.nextGeneration();
    }

    private void startStopButtonActionPerformed(ActionEvent actionEvent) {
        if (model.isSimulationRunning()) {
            model.stopSimulation();
        } else {
            model.startSimulation();
        }
        gameOptions.toggleStartStopButton();
    }
}
