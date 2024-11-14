package Projects.GameOfLife;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The GameOptions class represents the options panel of the Game of Life application.
 * It provides controls to start and stop the simulation, to reset the board, to view the current generation count,
 * and to change the simulation delay.
 */
public final class GameOptions extends JPanel implements Observer {

    private final JButton startStopButton;
    private final JButton nextButton;
    private final JButton resetButton;
    private final JSlider delaySlider;
    private final JLabel generationLabel;

    private final Model model;
    static final int MIN_SLIDER_POS = 1;
    static final int MAX_SLIDER_POS = 100;
    static final int INITIAL_SLIDER_POS = (MAX_SLIDER_POS - MIN_SLIDER_POS) / 2;

    public GameOptions(Model model) {
        this.model = model;

        startStopButton = new JButton("Start");
        nextButton = new JButton("Next");
        resetButton = new JButton("Reset");
        delaySlider = new JSlider(SwingConstants.HORIZONTAL, MIN_SLIDER_POS, MAX_SLIDER_POS, INITIAL_SLIDER_POS);
        generationLabel = new JLabel("Generation: 0");

        // Set layout manager
        setLayout(new BorderLayout());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns
        buttonPanel.add(startStopButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(resetButton);

        // Add the button panel to the top of GameOptions
        add(buttonPanel, BorderLayout.NORTH);

        // Create a panel for the slider and label
        JPanel sliderPanel = new JPanel();
        JLabel speedLabel = new JLabel("Speed:");
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

        // set preferred size for the slider to take up half the frame width
        delaySlider.setPreferredSize(new Dimension(200, delaySlider.getPreferredSize().height));

        // center align the slider and label within sliderPanel
        JPanel sliderContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sliderContainer.add(speedLabel);
        sliderContainer.add(delaySlider);
        sliderPanel.add(sliderContainer);

        // center the generation label and add it below the slider
        JPanel labelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelContainer.add(generationLabel);
        sliderPanel.add(labelContainer);

        // Add the slider panel to the center of GameOptions
        add(sliderPanel, BorderLayout.CENTER);
    }

    public void addStartStopButtonActionListener(ActionListener listener) {
        startStopButton.addActionListener(listener);
    }

    public void addNextButtonActionListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public void addResetButtonActionListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    public void addSpeedSliderChangeListener(ChangeListener listener) {
        delaySlider.addChangeListener(listener);
    }

    public void setGenerationLabel(int generation) {
        generationLabel.setText("Generation: " + generation);
    }

    /**
     * Defines the method that will be called when the observed object is updated.
     */
    @Override
    public void update() {
        setGenerationLabel(model.getGenerationCount());
    }

    public void resetStartStopButton() {
        startStopButton.setText("Start");
    }

    public void toggleStartStopButton() {
        if (startStopButton.getText().equals("Start")) {
            startStopButton.setText("Stop");
        } else {
            startStopButton.setText("Start");
        }
    }

    public int getDelaySliderValue() {
        return delaySlider.getValue();
    }
}
