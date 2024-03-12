package Projects.GameOfLife;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class Controller {

    Model model;
    View view;

    private Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.addCellListeners(this::cellClickedActionPerformed);
    }

    public static Controller createController(Model model, View view) {
        return new Controller(model, view);
    }

    public static Controller createControllerWithRandomInit(Model model, View view) {
        Controller controller = new Controller(model, view);
        controller.randomInit();
        return controller;
    }

    public void simulate() {
        Timer timer = new Timer(100, e -> {
            System.out.println("Generation: " + model.getGenerationCount());
            view.updateView(model);
            model.nextGeneration();
        });
        timer.start();
    }

    private void cellClickedActionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = (int) button.getClientProperty("row");
        int col = (int) button.getClientProperty("col");
        toggleCell(row, col);
        // model and view agree
        assert ((!model.isAlive(row, col) && !view.isColoredAlive(row, col)) || (model.isAlive(row, col) && view.isColoredAlive(row, col)));
    }

    private void toggleCell(int row, int col) {
        if (model.isAlive(row, col)) {
            model.setCell(row, col, false);
            view.setCell(row, col, false);
        } else {
            model.setCell(row, col, true);
            view.setCell(row, col, true);
        }
    }

    private void randomInit() {
        final double FRACTION_OF_CELLS_FILLED = 0.2;
        for (int i = 0; i < Model.ROWS; i++) {
            for (int j = 0; j < Model.COLS; j++) {
                if (Math.random() < FRACTION_OF_CELLS_FILLED) {
                    model.setCell(i, j, true);
                    view.setCell(i, j, true);
                }
            }
        }
    }
}
