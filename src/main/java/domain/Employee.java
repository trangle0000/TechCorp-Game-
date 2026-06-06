cat > src/main/java/domain/Employee.java << 'EOF'
package domain;

public abstract class Employee implements Comparable<Employee> {
    protected String name;
    protected int skill;
    protected double salary;
    protected int experienceLevel;
    protected boolean active;

    public Employee(String name, int skill) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        if (skill < 1 || skill > 100) throw new IllegalArgumentException("Skill must be 1-100");
        this.name = name;
        this.skill = skill;
        this.salary = calculateSalary();
        this.experienceLevel = 1;
        this.active = true;
    }

    public Employee(String name, int skill, double salary) {
        this(name, skill);
        this.salary = salary;
    }

    protected abstract double calculateSalary();
    public abstract String getRole();
    public abstract double getProductivity();

    public double getContribution() { return skill * getProductivity(); }
    public void increaseSkill(int amount) { this.skill = Math.min(100, this.skill + amount); }
    public void addExperience(int years) { this.experienceLevel += years; }

    public String getName() { return name; }
    public int getSkill() { return skill; }
    public double getSalary() { return salary; }
    public int getExperienceLevel() { return experienceLevel; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public int compareTo(Employee other) { return Integer.compare(other.skill, this.skill); }

    @Override
    public String toString() {
        return String.format("%s [%s] skill=%d salary=%.0f", name, getRole(), skill, salary);
    }
}
EOF