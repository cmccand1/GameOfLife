package Projects.GameOfLife;

import javax.swing.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("FeatureEnvy")
public class Controller {

    Model model;
    View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.addCellListeners(this::cellClickedActionPerformed);

//        randomInit();
//        simulate();
    }

    public void simulate() {
        Timer timer = new Timer(100, e -> {
            model.nextGeneration();
            view.updateView(model);
            model.swapBoards();
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
            model.setDead(row, col);
            view.colorDead(row, col);
        } else {
            model.setAlive(row, col);
            view.colorAlive(row, col);
        }
    }

    private void randomInit() {
        for (int i = 0; i < Model.ROWS; i++) {
            for (int j = 0; j < Model.COLS; j++) {
                if (Math.random() < 0.05) {
                    model.setAlive(i, j);
                    view.colorAlive(i, j);
                }
            }
        }
    }


}
