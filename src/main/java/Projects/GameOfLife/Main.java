package Projects.GameOfLife;

import javax.swing.*;
import java.awt.*;

/**
 * The Main class is the entry point for the Game of Life application.
 * It initializes the model, view, and controller, and sets up the main application window.
 */
public class Main {
    public static void main(String[] args) {
        Model gameModel = new Model();
        View gameView = new View(gameModel);
        GameOptions gameOptions = new GameOptions(gameModel);
        Controller gameController = Controller.newRandomGameController(gameModel, gameView, gameOptions);
//        Controller gameController = Controller.newBlankGameController(gameModel, gameView, gameOptions);

        JFrame frame = new JFrame("Game of Life");
        frame.setLayout(new BorderLayout());
        frame.add(gameOptions, BorderLayout.SOUTH);
        frame.add(gameView, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
