package constants;

/**
 * Game configuration constants.
 * Use these instead of hardcoded values throughout the game.
 */
public class GameConstants {

    // === Game Configuration ===
    public static final double PLAYER_STARTING_BUDGET = 100000.0;
    public static final double AI_STARTING_BUDGET = 100000.0;
    public static final int GAME_ROUNDS = 12;

    // === Financial Calculations ===
    public static final double REPUTATION_MULTIPLIER = 1000.0;
    public static final double COMPLETED_PROJECT_VALUE = 5000.0;
    public static final double STRATEGIC_PROJECT_REVENUE = 5000.0;
    public static final double NORMAL_PROJECT_REVENUE = 3000.0;
    public static final double REDUCED_SALARY_RATE = 0.8;

    private GameConstants() {
        // Prevent instantiation
    }
}