# Game of Life

This project is an implementation of Conway's Game of Life using Java and Swing
for the graphical user interface. The Game of Life is a cellular automaton
devised by the British mathematician John Horton Conway in 1970.

### Features

- **Interactive Grid:** Click on cells to toggle their state
  between alive and dead.
- **Simulation Control:** Start, stop, and step through generations of the
  simulation.
- **Speed Control:** Adjust the speed of the simulation using a slider.
- **Generation Counter:** Display the current generation count.

### Getting Started

#### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Gradle

### Installation

1. Clone the repository:

```
git clone https://github.com/cmccand1/GOL.git
cd GOL
```

2. Build the project using Gradle:

```
./gradlew build
```

3. Run the application:

```
./gradlew run
```

### Usage

- **Start/Stop:** Click the "Start" button to begin the simulation. Click
  "Stop" to pause it.
- **Next:** Click the "Next" button to advance the simulation by one generation.
- **Reset:** Click the "Reset" button to clear the grid and start a new game.
- **Speed Slider:** Adjust the slider to change the speed of the simulation.
  Moving the slider to the right increases the speed, while moving it to the
  left decreases the speed.

### Screenshots
#### Game init
<img width="754" alt="Screenshot 2024-11-14 at 12 49 24 PM" src="https://github.com/user-attachments/assets/c9113f65-f152-46df-a4d4-656d4ec03caa">
#### Game running
<img width="755" alt="Screenshot 2024-11-14 at 12 50 04 PM" src="https://github.com/user-attachments/assets/395306c9-28ac-4929-9af6-d1f93b931384">
