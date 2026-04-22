# Gomoku Game (Five-in-a-Row)

A Java implementation of the Gomoku (Five-in-a-Row) game with an AI opponent using the Minimax algorithm enhanced with alpha-beta pruning.

## Features
- Human vs AI gameplay
- Intelligent AI with optimized search algorithms
- Graphical user interface (GUI) for easy interaction
- Core game logic with board management and win detection

## Technologies Used
- Java
- Swing for GUI
- OOP principles for clean code structure

## How to Run
1. Clone the repository: `git clone <your-repo-url>`
2. Compile: `find src -name "*.java" -exec javac -d bin {} +`
3. Run: `java -cp bin main.Main`

## Project Structure
- `src/main/`: Source code
  - `ai/`: AI algorithms (Minimax, Evaluator)
  - `core/`: Game logic (Board, Cell, Move)
  - `gui/`: User interface
  - `controller/`: Game controller
- `test/`: Test files (if any)

## Future Enhancements
- Add difficulty levels
- Implement network multiplayer
- Optimize for larger boards