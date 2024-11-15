package Projects.GameOfLife;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame {

  private final ControlsPanel controlsPanel;
  private final GameView gameView;

  public GameFrame(GameView gameView, ControlsPanel controlsPanel) {
    this.gameView = gameView;
    this.controlsPanel = controlsPanel;

    setTitle("Game of Life");
    setLayout(new BorderLayout());
    add(gameView, BorderLayout.CENTER);
    add(controlsPanel, BorderLayout.SOUTH);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);
    pack();
    setVisible(true);
  }
}
