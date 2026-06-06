cat > src/main/java/engine/AIPlayer.java << 'EOF'
package engine;

import domain.*;
import exceptions.*;
import constants.GameConstants;

public class AIPlayer {
    public enum Strategy { AGGRESSIVE, BALANCED, DEFENSIVE }

    public static void takeTurn(Company company, int round) {
        Strategy strategy = determineStrategy(company);
        try {
            if (company.getEmployeeCount() < 3) {
                hireEmployee(company, strategy);
            } else {
                startProject(company, strategy, round);
            }
        } catch (InsufficientFundsException | InvalidProjectException e) {
            System.out.println("[AI] Skipping turn: " + e.getMessage());
        }
    }

    private static Strategy determineStrategy(Company company) {
        double cash = company.getCash();
        if (cash > 150_000) return Strategy.AGGRESSIVE;
        if (cash > 70_000) return Strategy.BALANCED;
        return Strategy.DEFENSIVE;
    }

    private static void hireEmployee(Company company, Strategy strategy) throws InsufficientFundsException {
        int skill = strategy == Strategy.AGGRESSIVE ? 70 : strategy == Strategy.BALANCED ? 50 : 30;
        String name = "AI-Dev-" + (company.getEmployeeCount() + 1);
        Employee emp = new Developer(name, skill);
        company.deductCash(emp.getSalary());
        company.hireEmployee(emp);
        System.out.printf("[AI Corp] Hired %s (skill %d)%n", name, skill);
    }

    private static void startProject(Company company, Strategy strategy, int round) throws InsufficientFundsException, InvalidProjectException {
        Project.ProjectDifficulty diff;
        double budget, revenue;
        int deadline;

        switch (strategy) {
            case AGGRESSIVE:
                diff = Project.ProjectDifficulty.HARD; budget = 20_000; revenue = 60_000; deadline = 5; break;
            case BALANCED:
                diff = Project.ProjectDifficulty.MEDIUM; budget = 10_000; revenue = 30_000; deadline = 4; break;
            default:
                diff = Project.ProjectDifficulty.EASY; budget = 5_000; revenue = 15_000; deadline = 3; break;
        }

        if (company.getCash() < budget) throw new InsufficientFundsException("AI Project", budget, company.getCash());

        Project project = new Project("AI-Project-" + round, budget, revenue, diff, true, deadline);
        for (Employee e : company.getEmployees()) {
            if (e.isActive()) project.assignEmployee(e);
        }

        company.deductCash(budget);
        company.addProject(project);
        project.startProject(round);
        System.out.printf("[AI Corp] Started project [%s difficulty]%n", diff);
    }
}
EOF