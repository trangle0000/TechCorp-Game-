package domain;

import java.util.*;

public class Project {
    public enum ProjectStatus { NOT_STARTED, IN_PROGRESS, COMPLETED, FAILED }
    public enum ProjectDifficulty { EASY, MEDIUM, HARD, CRITICAL }

    private String name;
    private double budget;
    private double revenue;
    private ProjectDifficulty difficulty;
    private boolean strategic;
    private int deadline;
    private int turnsElapsed;
    private int startRound;
    private ProjectStatus status;
    private List<Employee> assignedEmployees;

    public Project(String name, double budget, double revenue, ProjectDifficulty difficulty, boolean strategic, int deadline) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        if (budget <= 0) throw new IllegalArgumentException("Budget must be positive");
        this.name = name;
        this.budget = budget;
        this.revenue = revenue;
        this.difficulty = difficulty;
        this.strategic = strategic;
        this.deadline = deadline;
        this.turnsElapsed = 0;
        this.status = ProjectStatus.NOT_STARTED;
        this.assignedEmployees = new ArrayList<>();
    }

    public void startProject(int round) {
        if (status != ProjectStatus.NOT_STARTED) throw new IllegalStateException("Project already started");
        this.status = ProjectStatus.IN_PROGRESS;
        this.startRound = round;
    }

    public void assignEmployee(Employee e) {
        if (e == null) throw new IllegalArgumentException("Employee cannot be null");
        assignedEmployees.add(e);
    }

    public void advanceTurn() {
        if (status != ProjectStatus.IN_PROGRESS) return;
        turnsElapsed++;
        if (turnsElapsed >= deadline) {
            double successChance = getSuccessChance();
            if (Math.random() < successChance) completeProject();
            else failProject("Deadline reached");
        }
    }

    private double getSuccessChance() {
        double base;
        switch (difficulty) {
            case EASY: base = 0.9; break;
            case MEDIUM: base = 0.75; break;
            case HARD: base = 0.6; break;
            default: base = 0.4; break;
        }
        double teamBonus = assignedEmployees.isEmpty() ? -0.3 :
                assignedEmployees.stream().mapToDouble(e -> e.getSkill() / 200.0).sum();
        return Math.min(0.95, base + teamBonus);
    }

    public void completeProject() { this.status = ProjectStatus.COMPLETED; }
    public void failProject(String reason) { this.status = ProjectStatus.FAILED; }
    public double getReputation() { return strategic ? revenue * 0.1 : revenue * 0.05; }

    public String getName() { return name; }
    public double getBudget() { return budget; }
    public double getRevenue() { return revenue; }
    public ProjectStatus getStatus() { return status; }
    public ProjectDifficulty getDifficulty() { return difficulty; }
    public boolean isStrategic() { return strategic; }
    public int getDeadline() { return deadline; }
    public boolean isCompleted() { return status == ProjectStatus.COMPLETED; }
    public boolean isFailed() { return status == ProjectStatus.FAILED; }
    public int getAssignedEmployeeCount() { return assignedEmployees.size(); }
    public List<Employee> getAssignedEmployees() { return assignedEmployees; }
}
