package engine;

import domain.*;
import exceptions.*;
import constants.GameConstants;
import java.util.*;

public class GameEngine {
    private Company playerCompany;
    private Company aiCompany;
    private int totalRounds;
    private int currentRound;

    private static final String[] PROJECT_NAMES = {
        "Alpha Platform", "Beta API", "Cloud Migration", "Data Pipeline",
        "E-Commerce Engine", "Fintech Dashboard", "Grid Analytics", "Horizon App"
    };

    public GameEngine(double playerBudget, double aiBudget, int rounds) {
        if (playerBudget <= 0) throw new IllegalArgumentException("Player budget must be positive");
        if (aiBudget <= 0) throw new IllegalArgumentException("AI budget must be positive");
        if (rounds <= 0) throw new IllegalArgumentException("Rounds must be positive");
        this.playerCompany = new Company("Player Corp", playerBudget);
        this.aiCompany = new Company("AI Corp", aiBudget);
        this.totalRounds = rounds;
        this.currentRound = 0;
    }

    public void runGame() {
        Scanner scanner = new Scanner(System.in);
        displayWelcome();
        try {
            for (currentRound = 1; currentRound <= totalRounds; currentRound++) {
                displayRoundHeader();
                playRound(scanner);
            }
            displayGameResults();
        } catch (Exception e) {
            System.err.println("GAME OVER: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private void displayWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("         WELCOME TO TECHCORP DUEL GAME");
        System.out.println("=".repeat(60) + "\n");
    }

    private void displayRoundHeader() {
        System.out.println("\n" + "=".repeat(40));
        System.out.printf("ROUND %d / %d%n", currentRound, totalRounds);
        System.out.printf("Your cash: $%.0f | AI cash: $%.0f%n",
                playerCompany.getCash(), aiCompany.getCash());
        System.out.println("=".repeat(40) + "\n");
    }

    private void playRound(Scanner scanner) throws InsufficientFundsException, InvalidProjectException {
        System.out.println("Your turn:");
        System.out.println("1. Hire Employee");
        System.out.println("2. Start Strategic Project");
        System.out.println("3. Skip");
        System.out.print("Enter your choice (1-3): ");

        String input = scanner.nextLine().trim();
        int choice;
        try { choice = Integer.parseInt(input); if (choice < 1 || choice > 3) choice = 3; }
        catch (NumberFormatException e) { choice = 3; }

        processPlayerChoice(choice, scanner);
        advanceProjects(playerCompany);
        advanceProjects(aiCompany);
        AIPlayer.takeTurn(aiCompany, currentRound);
    }

    private void processPlayerChoice(int choice, Scanner scanner) throws InsufficientFundsException, InvalidProjectException {
        switch (choice) {
            case 1: handleHireEmployee(scanner); break;
            case 2: handleStrategicProject(scanner); break;
            default: System.out.println("Skipping turn..."); break;
        }
    }

    private void handleHireEmployee(Scanner scanner) throws InsufficientFundsException {
        System.out.println("\n1. Developer  (skill*5000)");
        System.out.println("2. Manager    (skill*7000)");
        System.out.println("3. Tester     (skill*3000)");
        System.out.print("Choose type (1-3): ");
        int type;
        try { type = Integer.parseInt(scanner.nextLine().trim()); if (type < 1 || type > 3) type = 1; }
        catch (NumberFormatException e) { type = 1; }

        System.out.print("Enter skill level (1-100): ");
        int skill;
        try { skill = Integer.parseInt(scanner.nextLine().trim()); skill = Math.max(1, Math.min(100, skill)); }
        catch (NumberFormatException e) { skill = GameConstants.STARTING_SKILL_LEVEL; }

        String[] names = {"Alex", "Blake", "Casey", "Dana", "Ellis", "Fran", "Gray", "Harper"};
        String name = names[(int)(Math.random() * names.length)] + " #" + (playerCompany.getEmployeeCount() + 1);

        Employee employee;
        switch (type) {
            case 2: employee = new Manager(name, skill); break;
            case 3: employee = new Tester(name, skill); break;
            default: employee = new Developer(name, skill); break;
        }

        if (playerCompany.getCash() < employee.getSalary())
            throw new InsufficientFundsException("Hire " + employee.getRole(), employee.getSalary(), playerCompany.getCash());

        playerCompany.deductCash(employee.getSalary());
        playerCompany.hireEmployee(employee);
        System.out.printf("Hired %s as %s (skill %d, salary $%.0f)%n", name, employee.getRole(), skill, employee.getSalary());
    }

    private void handleStrategicProject(Scanner scanner) throws InsufficientFundsException, InvalidProjectException {
        System.out.println("\n1. Easy     ($5k budget,  $15k revenue)");
        System.out.println("2. Medium   ($10k budget, $30k revenue)");
        System.out.println("3. Hard     ($20k budget, $60k revenue)");
        System.out.println("4. Critical ($40k budget, $120k revenue)");
        System.out.print("Choose difficulty (1-4): ");

        int diff;
        try { diff = Integer.parseInt(scanner.nextLine().trim()); if (diff < 1 || diff > 4) diff = 1; }
        catch (NumberFormatException e) { diff = 1; }

        Project.ProjectDifficulty difficulty;
        double budget, revenue;
        int deadline;
        switch (diff) {
            case 2: difficulty = Project.ProjectDifficulty.MEDIUM;   budget = 10_000; revenue = 30_000;  deadline = 4; break;
            case 3: difficulty = Project.ProjectDifficulty.HARD;     budget = 20_000; revenue = 60_000;  deadline = 5; break;
            case 4: difficulty = Project.ProjectDifficulty.CRITICAL; budget = 40_000; revenue = 120_000; deadline = 6; break;
            default: difficulty = Project.ProjectDifficulty.EASY;    budget = 5_000;  revenue = 15_000;  deadline = 3; break;
        }

        if (playerCompany.getCash() < budget)
            throw new InsufficientFundsException("Strategic Project", budget, playerCompany.getCash());

        String projectName = PROJECT_NAMES[(int)(Math.random() * PROJECT_NAMES.length)] + " v" + currentRound;
        Project project = new Project(projectName, budget, revenue, difficulty, true, deadline);
        for (Employee emp : playerCompany.getEmployees()) {
            if (emp.isActive()) project.assignEmployee(emp);
        }

        playerCompany.deductCash(budget);
        playerCompany.addProject(project);
        project.startProject(currentRound);
        System.out.printf("Started '%s' [%s, budget $%.0f, deadline %d rounds]%n", projectName, difficulty, budget, deadline);
    }

    private void advanceProjects(Company company) {
        for (Project p : company.getProjects()) {
            if (p.getStatus() == Project.ProjectStatus.IN_PROGRESS) {
                p.advanceTurn();
                if (p.isCompleted()) {
                    company.addCash(p.getRevenue());
                    company.addReputation(p.getReputation());
                    System.out.printf("[%s] Project '%s' COMPLETED! +$%.0f%n", company.getName(), p.getName(), p.getRevenue());
                } else if (p.isFailed()) {
                    System.out.printf("[%s] Project '%s' FAILED.%n", company.getName(), p.getName());
                }
            }
        }
    }

    private void displayGameResults() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("GAME RESULTS");
        System.out.println("=".repeat(40));
        System.out.println(playerCompany);
        System.out.println(aiCompany);
        double playerScore = playerCompany.getScore();
        double aiScore = aiCompany.getScore();
        if (playerScore > aiScore) System.out.println("\nYOU WIN!");
        else if (aiScore > playerScore) System.out.println("\nAI WINS!");
        else System.out.println("\nIT'S A TIE!");
        System.out.println("=".repeat(40));
    }

    public int getCurrentRound() { return currentRound; }
    public int getTotalRounds() { return totalRounds; }
    public Company getPlayerCompany() { return playerCompany; }
    public Company getAiCompany() { return aiCompany; }
}
