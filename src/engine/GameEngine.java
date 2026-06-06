package engine;

import domain.*;
import exceptions.*;
import constants.GameConstants;
import java.util.*;

/**
 * Core game engine for TechCorp Duel Game.
 * Manages game flow, rounds, and player interactions with proper validation.
 */
public class GameEngine {
    private Company playerCompany;
    private Company aiCompany;
    private int totalRounds;
    private int currentRound;

    /**
     * Creates a new game engine with specified parameters.
     * @param playerBudget the player's starting budget
     * @param aiBudget the AI's starting budget
     * @param rounds the number of rounds to play
     */
    public GameEngine(double playerBudget, double aiBudget, int rounds) {
        this.playerCompany = new Company("Player Corp", playerBudget);
        this.aiCompany = new Company("AI Corp", aiBudget);
        this.totalRounds = rounds;
        this.currentRound = 0;
    }

    /**
     * Runs the complete game.
     * @throws Exception if game encounters an error
     */
    public void runGame() throws Exception {
        displayWelcome();

        try (Scanner scanner = new Scanner(System.in)) {
            for (currentRound = 1; currentRound <= totalRounds; currentRound++) {
                displayRoundHeader();
                playRound(scanner);
            }
        }

        displayGameResults();
    }

    /**
     * Displays welcome message.
     */
    private void displayWelcome() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     WELCOME TO TECHCORP DUEL GAME      ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Displays round header.
     */
    private void displayRoundHeader() {
        System.out.println("\n" + "=".repeat(40));
        System.out.printf("ROUND %d / %d%n", currentRound, totalRounds);
        System.out.println("=".repeat(40));
    }

    /**
     * Plays a single round with player input validation.
     * @param scanner the input scanner
     */
    private void playRound(Scanner scanner) {
        try {
            System.out.println("Your turn:");
            System.out.println("1. Assign to Strategic Project");
            System.out.println("2. Pause");
            System.out.println("3. Skip");
            System.out.print("Enter your choice (1-3): ");

            String input = scanner.nextLine().trim();
            int choice = validateInput(input);
            processPlayerChoice(choice);

            AIPlayer.takeTurn(aiCompany);
        } catch (InsufficientFundsException e) {
            System.out.println("⚠️  " + e.getMessage());
        } catch (InvalidProjectException e) {
            System.out.println("⚠️  " + e.getMessage());
        }
    }

    /**
     * Validates player input.
     * @param input the input string
     * @return the validated choice (1-3)
     */
    private int validateInput(String input) {
        try {
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= 3) {
                return choice;
            }
        } catch (NumberFormatException e) {
            // Fall through to default
        }
        System.out.println("Invalid input. Using default: Skip");
        return 3;
    }

    /**
     * Processes the player's choice.
     * @param choice the player's choice (1-3)
     * @throws InsufficientFundsException if operation fails due to funds
     * @throws InvalidProjectException if operation fails due to invalid project
     */
    private void processPlayerChoice(int choice)
            throws InsufficientFundsException, InvalidProjectException {
        switch (choice) {
            case 1:
                handleStrategicProject();
                break;
            case 2:
                System.out.println("Game paused...");
                break;
            case 3:
                System.out.println("Skipping turn...");
                break;
        }
    }

    /**
     * Handles strategic project assignment.
     */
    private void handleStrategicProject()
            throws InsufficientFundsException, InvalidProjectException {
        System.out.println("Assigning to strategic project...");
        // Implementation here
    }

    /**
     * Displays game results.
     */
    private void displayGameResults() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("GAME RESULTS");
        System.out.println("=".repeat(40));
        System.out.println(playerCompany);
        System.out.println(aiCompany);

        if (playerCompany.getScore() > aiCompany.getScore()) {
            System.out.println("\n🎉 YOU WIN! 🎉");
        } else if (aiCompany.getScore() > playerCompany.getScore()) {
            System.out.println("\n😢 AI WINS!");
        } else {
            System.out.println("\n🤝 IT'S A TIE!");
        }
    }
}