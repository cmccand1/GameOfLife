package Projects.GameOfLife;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        Model model = Model.createModel();
        View view = new View();
        Controller controller = Controller.createControllerWithRandomInit(model, view);
        //Controller controller = Controller.createController(model, view);

        JFrame frame = new JFrame("Game of Life");
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            controller.simulate();
            startButton.setEnabled(false);

        });
        frame.add(view, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
