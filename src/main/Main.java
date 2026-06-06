import engine.GameEngine;
import constants.GameConstants;

/**
 * Main entry point for the TechCorp Duel Game.
 * Initializes and runs the game with proper resource management.
 */
public class Main {
    /**
     * Main method - starts the game.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Create and run the game engine
            GameEngine engine = new GameEngine(
                GameConstants.PLAYER_STARTING_BUDGET,
                GameConstants.AI_STARTING_BUDGET,
                GameConstants.GAME_ROUNDS
            );
            engine.runGame();
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}