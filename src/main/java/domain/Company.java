cat > src/main/java/domain/Company.java << 'EOF'
package domain;

import exceptions.InsufficientFundsException;
import exceptions.InvalidProjectException;
import java.util.*;

public class Company {
    private String name;
    private double cash;
    private double totalReputation;
    private List<Employee> employees;
    private List<Project> projects;

    public Company(String name, double cash) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or blank");
        if (cash < 0) throw new IllegalArgumentException("Cash cannot be negative");
        this.name = name;
        this.cash = cash;
        this.totalReputation = 0;
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public void hireEmployee(Employee e) {
        if (e == null) throw new IllegalArgumentException("Employee cannot be null");
        employees.add(e);
    }

    public void addProject(Project p) throws InvalidProjectException {
        if (p == null) throw new InvalidProjectException("Project cannot be null");
        projects.add(p);
    }

    public void deductCash(double amount) throws InsufficientFundsException {
        if (amount > cash) throw new InsufficientFundsException("Deduct", amount, cash);
        cash -= amount;
    }

    public void addCash(double amount) { cash += amount; }
    public void addReputation(double rep) { totalReputation += rep; }
    public double getScore() { return cash + totalReputation * 100; }

    public String getName() { return name; }
    public double getCash() { return cash; }
    public double getTotalReputation() { return totalReputation; }
    public List<Employee> getEmployees() { return employees; }
    public List<Project> getProjects() { return projects; }
    public int getEmployeeCount() { return employees.size(); }
    public int getProjectCount() { return projects.size(); }

    @Override
    public String toString() {
        return String.format("Company: %s | Cash: $%.0f | Reputation: %.0f | Employees: %d | Projects: %d",
                name, cash, totalReputation, employees.size(), projects.size());
    }
}
EOF