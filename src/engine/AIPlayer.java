package engine;

import domain.Company;
import exceptions.*;
import constants.GameConstants;

/**
 * AI Player for TechCorp Duel Game.
 * Implements simple AI strategy for game decisions.
 */
public class AIPlayer {
    /**
     * AI takes its turn in the game.
     * @param company the AI's company
     * @throws InsufficientFundsException if AI can't afford its choice
     * @throws InvalidProjectException if AI's choice is invalid
     */
    public static void takeTurn(Company company)
            throws InsufficientFundsException, InvalidProjectException {
        // Simple AI strategy
        int choice = (int) (Math.random() * 3) + 1;

        switch (choice) {
            case 1:
                System.out.println(company.getName() + " assigns to strategic project.");
                break;
            case 2:
                System.out.println(company.getName() + " pauses.");
                break;
            case 3:
                System.out.println(company.getName() + " skips turn.");
                break;
        }
    }

    /**
     * Gets a decision from the AI based on company state.
     * @param company the AI's company
     * @return the AI's choice (1-3)
     */
    public static int getDecision(Company company) {
        double cashRatio = company.getCash() / GameConstants.PLAYER_STARTING_BUDGET;

        if (cashRatio > 0.7) {
            return 1; // Strategic project
        } else if (cashRatio > 0.4) {
            return 2; // Pause
        } else {
            return 3; // Skip
        }
    }
}