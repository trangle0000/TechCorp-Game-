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
        System.out.println("=".repeat(60));
        System.out.println("  Build the most successful tech company in 12 rounds!");
        System.out.println("  Hire employees, start projects, beat the AI opponent.");
        System.out.println("  Score = Final Cash + (Reputation x 100)");
        System.out.println("=".repeat(60) + "\n");
    }

    private void displayRoundHeader() {
        int repBar = (int) Math.min(20, playerCompany.getTotalReputation() / 5);
        String bar = "#".repeat(repBar) + "-".repeat(20 - repBar);
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("  ROUND %d / %d%n", currentRound, totalRounds);
        System.out.println("  ┌─────────────────────────┬─────────────────────────┐");
        System.out.printf("  │ YOUR COMPANY            │ AI COMPANY              │%n");
        System.out.printf("  │ Cash: $%-17.0f│ Cash: $%-17.0f│%n",
                playerCompany.getCash(), aiCompany.getCash());
        System.out.printf("  │ Reputation: %-12.0f│ Reputation: %-12.0f│%n",
                playerCompany.getTotalReputation(), aiCompany.getTotalReputation());
        System.out.printf("  │ Employees: %-13d│ Employees: %-13d│%n",
                playerCompany.getEmployeeCount(), aiCompany.getEmployeeCount());
        System.out.printf("  │ Projects: %-14d│ Projects: %-14d│%n",
                playerCompany.getProjectCount(), aiCompany.getProjectCount());
        System.out.println("  └─────────────────────────┴─────────────────────────┘");
        System.out.printf("  Rep Progress: [%s] %.0f pts%n", bar, playerCompany.getTotalReputation());
        System.out.println("=".repeat(60) + "\n");
    }

    private void playRound(Scanner scanner) throws InsufficientFundsException, InvalidProjectException {
        System.out.println("  Choose your action:");
        System.out.println("  [1] Hire Employee");
        System.out.println("  [2] View Company Status");
        System.out.println("  [3] Start Strategic Project");
        System.out.println("  [4] View All Employees");
        System.out.println("  [5] View All Projects");
        System.out.println("  [6] Skip Turn");
        System.out.print("  Enter choice (1-6): ");

        String input = scanner.nextLine().trim();
        int choice;
        try { choice = Integer.parseInt(input); if (choice < 1 || choice > 6) choice = 6; }
        catch (NumberFormatException e) { choice = 6; }

        processPlayerChoice(choice, scanner);
        advanceProjects(playerCompany);
        advanceProjects(aiCompany);
        AIPlayer.takeTurn(aiCompany, currentRound);
    }

    private void processPlayerChoice(int choice, Scanner scanner) throws InsufficientFundsException, InvalidProjectException {
        switch (choice) {
            case 1: handleHireEmployee(scanner); break;
            case 2: displayCompanyStatus(); break;
            case 3: handleStrategicProject(scanner); break;
            case 4: displayEmployees(); break;
            case 5: displayProjects(); break;
            default: System.out.println("  Skipping turn..."); break;
        }
    }

    private void displayCompanyStatus() {
        System.out.println("\n  ── COMPANY STATUS ──────────────────────────────────");
        System.out.printf("  Company : %s%n", playerCompany.getName());
        System.out.printf("  Cash    : $%.0f%n", playerCompany.getCash());
        System.out.printf("  Rep     : %.0f pts%n", playerCompany.getTotalReputation());
        System.out.printf("  Score   : %.0f%n", playerCompany.getScore());
        double totalSalary = playerCompany.getEmployees().stream()
                .mapToDouble(Employee::getSalary).sum();
        System.out.printf("  Total Salary Cost: $%.0f%n", totalSalary);
        if (!playerCompany.getEmployees().isEmpty()) {
            System.out.println("  ┌────────────────────┬────────────┬──────────┐");
            System.out.println("  │ Name               │ Role       │ Salary   │");
            System.out.println("  ├────────────────────┼────────────┼──────────┤");
            for (Employee e : playerCompany.getEmployees()) {
                System.out.printf("  │ %-18s │ %-10s │ $%-7.0f │%n",
                        e.getName(), e.getRole(), e.getSalary());
            }
            System.out.println("  └────────────────────┴────────────┴──────────┘");
        }
        System.out.println();
    }

    private void displayEmployees() {
        if (playerCompany.getEmployees().isEmpty()) {
            System.out.println("\n  No employees hired yet.\n");
            return;
        }
        System.out.println("\n  ── EMPLOYEES ───────────────────────────────────────");
        System.out.println("  ┌────────────────────┬───────────┬───────┬──────────────┐");
        System.out.println("  │ Name               │ Role      │ Skill │ Productivity │");
        System.out.println("  ├────────────────────┼───────────┼───────┼──────────────┤");
        for (Employee e : playerCompany.getEmployees()) {
            System.out.printf("  │ %-18s │ %-9s │ %-5d │ %-12.1fx │%n",
                    e.getName(), e.getRole(), e.getSkill(), e.getProductivity());
        }
        System.out.println("  └────────────────────┴───────────┴───────┴──────────────┘");
        System.out.println();
    }

    private void displayProjects() {
        if (playerCompany.getProjects().isEmpty()) {
            System.out.println("\n  No projects started yet.\n");
            return;
        }
        System.out.println("\n  ── PROJECTS ────────────────────────────────────────");
        System.out.println("  ┌──────────────────────┬───────────┬───────────┬────────────┐");
        System.out.println("  │ Name                 │ Difficulty│ Revenue   │ Status     │");
        System.out.println("  ├──────────────────────┼───────────┼───────────┼────────────┤");
        for (Project p : playerCompany.getProjects()) {
            System.out.printf("  │ %-20s │ %-9s │ $%-8.0f │ %-10s │%n",
                    p.getName(), p.getDifficulty(), p.getRevenue(), p.getStatus());
        }
        System.out.println("  └──────────────────────┴───────────┴───────────┴────────────┘");
        System.out.println();
    }

    private void handleHireEmployee(Scanner scanner) throws InsufficientFundsException {
        System.out.println("\n  ── HIRE EMPLOYEE ───────────────────────────────────");
        System.out.println("  [1] Developer  — skill × $5,000 salary, 1.2x productivity");
        System.out.println("  [2] Manager    — skill × $7,000 salary, 0.8x productivity");
        System.out.println("  [3] Tester     — skill × $3,000 salary, 0.9x productivity");
        System.out.print("  Choose type (1-3): ");
        int type;
        try { type = Integer.parseInt(scanner.nextLine().trim()); if (type < 1 || type > 3) type = 1; }
        catch (NumberFormatException e) { type = 1; }

        System.out.printf("  Your cash: $%.0f%n", playerCompany.getCash());
        System.out.print("  Enter skill level (1-100): ");
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
        System.out.printf("  Hired %s as %s | skill=%d | salary=$%.0f%n",
                name, employee.getRole(), skill, employee.getSalary());
    }

    private void handleStrategicProject(Scanner scanner) throws InsufficientFundsException, InvalidProjectException {
        System.out.println("\n  ── START PROJECT ───────────────────────────────────");
        System.out.println("  [1] Easy     — cost $5k,  revenue $15k,  deadline 3 rounds");
        System.out.println("  [2] Medium   — cost $10k, revenue $30k,  deadline 4 rounds");
        System.out.println("  [3] Hard     — cost $20k, revenue $60k,  deadline 5 rounds");
        System.out.println("  [4] Critical — cost $40k, revenue $120k, deadline 6 rounds");
        System.out.printf("  Your cash: $%.0f%n", playerCompany.getCash());
        System.out.print("  Choose difficulty (1-4): ");

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
        System.out.printf("  Started '%s' [%s] | cost $%.0f | deadline %d rounds%n",
                projectName, difficulty, budget, deadline);
    }

    private void advanceProjects(Company company) {
        for (Project p : company.getProjects()) {
            if (p.getStatus() == Project.ProjectStatus.IN_PROGRESS) {
                p.advanceTurn();
                if (p.isCompleted()) {
                    company.addCash(p.getRevenue());
                    company.addReputation(p.getReputation());
                    System.out.printf("  [%s] Project '%s' COMPLETED! +$%.0f +%.0f rep%n",
                            company.getName(), p.getName(), p.getRevenue(), p.getReputation());
                } else if (p.isFailed()) {
                    System.out.printf("  [%s] Project '%s' FAILED.%n", company.getName(), p.getName());
                }
            }
        }
    }

    private void displayGameResults() {
        double playerScore = playerCompany.getScore();
        double aiScore = aiCompany.getScore();
        String result = playerScore > aiScore ? "YOU WIN!" : (aiScore > playerScore ? "AI WINS!" : "IT'S A TIE!");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    FINAL RESULTS");
        System.out.println("=".repeat(60));
        System.out.println("  ┌─────────────────────────┬─────────────────────────┐");
        System.out.printf("  │ YOUR COMPANY            │ AI COMPANY              │%n");
        System.out.printf("  │ Cash: $%-17.0f│ Cash: $%-17.0f│%n",
                playerCompany.getCash(), aiCompany.getCash());
        System.out.printf("  │ Reputation: %-12.0f│ Reputation: %-12.0f│%n",
                playerCompany.getTotalReputation(), aiCompany.getTotalReputation());
        System.out.printf("  │ SCORE: %-17.0f│ SCORE: %-17.0f│%n", playerScore, aiScore);
        System.out.println("  └─────────────────────────┴─────────────────────────┘");
        System.out.println();
        System.out.println("  " + result);
        System.out.println("=".repeat(60));
    }

    public int getCurrentRound() { return currentRound; }
    public int getTotalRounds() { return totalRounds; }
    public Company getPlayerCompany() { return playerCompany; }
    public Company getAiCompany() { return aiCompany; }
}